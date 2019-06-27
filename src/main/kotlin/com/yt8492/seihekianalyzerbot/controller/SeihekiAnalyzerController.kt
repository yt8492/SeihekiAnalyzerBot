package com.yt8492.seihekianalyzerbot.controller

import com.linecorp.bot.model.event.FollowEvent
import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.message.TextMessageContent
import com.linecorp.bot.spring.boot.annotation.EventMapping
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import com.yt8492.seihekianalyzerbot.presenter.SeihekiAnalyzerPresenter
import com.yt8492.seihekianalyzerbot.service.SeihekiAnalyzerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.scheduling.annotation.Scheduled

@LineMessageHandler
open class SeihekiAnalyzerController(private val seihekiAnalyzerService: SeihekiAnalyzerService,
                                     private val seihekiAnalyzerPresenter: SeihekiAnalyzerPresenter) {

    @EventMapping
    fun handleTextMessageEvent(event: MessageEvent<TextMessageContent>) {
        println("event: $event")
        val messageText = event.message.text
        val userId = event.source.userId
        println("UserId: $userId")
        when (messageText) {
            "analyze" -> {
                CoroutineScope(Dispatchers.IO).launch {
                    analyzeAndPushMessage(userId)
                }
            }
            "recommend" -> {
                CoroutineScope(Dispatchers.IO).launch {
                    recommend(userId)
                }
            }
        }
    }

    @EventMapping
    fun handleFollowEvent(event: FollowEvent) {
        val userId = event.source.userId
        seihekiAnalyzerService.registerUser(userId)
    }

    private fun analyzeAndPushMessage(userId: String) {
        val analyzeResults = seihekiAnalyzerService.getAnalyzeResults()
        seihekiAnalyzerPresenter.showAnalyzeResult(analyzeResults, userId)
    }

    @Scheduled(cron = "0 0 21 * * *", zone = "Asia/Tokyo")
    fun autoRecommend() {
        CoroutineScope(Dispatchers.IO).launch {
            val recommends = seihekiAnalyzerService.getRecommendedWorks()
            val userIds = seihekiAnalyzerService.findAllUserIds()
            seihekiAnalyzerPresenter.autoRecommend(recommends, userIds)
        }
    }

    private fun recommend(userId: String) {
        val recommends = seihekiAnalyzerService.getRecommendedWorks()
        seihekiAnalyzerPresenter.showRecommends(recommends, userId)
    }
}