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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
                GlobalScope.launch {
                    analyze(userId)
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

    private fun analyze(userId: String) {
        val works = seihekiAnalyzerService.findAll()
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

    private fun reply(token: String, text: String) {
        lineMessagingClient
                .replyMessage(ReplyMessage(token, TextMessage(text)))
    }

    private fun pushMessage(userId: String, text: String) {
        lineMessagingClient.pushMessage(PushMessage(userId, TextMessage(text)))
    }
}