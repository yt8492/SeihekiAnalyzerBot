package com.yt8492.seihekianalyzerbot.controller

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.PushMessage
import com.linecorp.bot.model.ReplyMessage
import com.linecorp.bot.model.event.FollowEvent
import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.message.TextMessageContent
import com.linecorp.bot.model.message.TextMessage
import com.linecorp.bot.spring.boot.annotation.EventMapping
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import com.yt8492.seihekianalyzerbot.entity.Work
import com.yt8492.seihekianalyzerbot.service.SeihekiAnalyzerService
import com.yt8492.seihekianalyzerbot.tools.SeihekiAnalyzer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.scheduling.annotation.Scheduled

@LineMessageHandler
open class SeihekiAnalyzerController(private val seihekiAnalyzerService: SeihekiAnalyzerService,
                                     private val lineMessagingClient: LineMessagingClient) {

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
            else -> seihekiAnalyzerService.saveTest(messageText)
        }
    }

    @EventMapping
    fun handleFollowEvent(event: FollowEvent) {
        val userId = event.source.userId
        seihekiAnalyzerService.registerUser(userId)
    }

    private fun analyzeAndPushMessage(userId: String) {
        val works = seihekiAnalyzerService.findAllWorks()
        val tagCnt = mutableMapOf<String, Int>()
        works.flatMap(Work::tags).forEach { tag ->
            var cnt = tagCnt[tag] ?: 0
            cnt++
            tagCnt[tag] = cnt
        }
        val result = tagCnt.toList()
                .sortedByDescending { it.second }
                .take(10)
                .mapIndexed { index, pair ->
                    "%2d位 %2.2f%%: ${pair.first}".format(index + 1, (pair.second.toDouble() / works.size) * 100)
                }.joinToString("\n")
        pushMessage(userId, result)
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Tokyo")
    fun recommend() {
        CoroutineScope(Dispatchers.IO).launch {
            val userIds = seihekiAnalyzerService.findAllUserIds()
            val latestWorks = SeihekiAnalyzer.getLatestWorks().map {
                Work(it.key, it.value)
            }
            val myFavoriteTags = analyze().map(Pair<String, Int>::first)
            val recommends = latestWorks.filter { work ->
                val tagCnt = work.tags.intersect(myFavoriteTags).size
                tagCnt >= 2
            }
            if (recommends.isNotEmpty()) {
                val result = "本日のオススメ作品\n${recommends.joinToString("\n") { it.url }}"
                userIds.forEach { userId ->
                    pushMessage(userId, result)
                }
            }
        }
    }

    private fun analyze(): List<Pair<String, Int>> {
        val works = seihekiAnalyzerService.findAllWorks()
        val tagCnt = mutableMapOf<String, Int>()
        works.flatMap(Work::tags).forEach { tag ->
            var cnt = tagCnt[tag] ?: 0
            cnt++
            tagCnt[tag] = cnt
        }
        val result = tagCnt.toList()
                .sortedByDescending { it.second }
                .take(10)
        return result
    }

    private fun recommend(userId: String) {
        val latestWorks = SeihekiAnalyzer.getLatestWorks().map {
            Work(it.key, it.value)
        }
        val myFavoriteTags = analyze().map(Pair<String, Int>::first)
        val recommends = latestWorks.filter { work ->
            val tagCnt = work.tags.intersect(myFavoriteTags).size
            tagCnt >= 2
        }
        val result = if (recommends.isNotEmpty()) {
            "本日のオススメ作品\n${recommends.joinToString("\n") { it.url }}"
        } else {
            "本日のオススメはありません。"
        }
        pushMessage(userId, result)
    }

    private fun reply(token: String, text: String) {
        lineMessagingClient
                .replyMessage(ReplyMessage(token, TextMessage(text)))
    }

    private fun pushMessage(userId: String, text: String) {
        lineMessagingClient.pushMessage(PushMessage(userId, TextMessage(text)))
    }
}