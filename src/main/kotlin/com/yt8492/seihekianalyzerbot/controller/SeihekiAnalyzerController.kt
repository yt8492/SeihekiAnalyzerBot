package com.yt8492.seihekianalyzerbot.controller

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.PushMessage
import com.linecorp.bot.model.ReplyMessage
import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.message.TextMessageContent
import com.linecorp.bot.model.message.TextMessage
import com.linecorp.bot.spring.boot.annotation.EventMapping
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import org.springframework.beans.factory.annotation.Autowired

@LineMessageHandler
open class SeihekiAnalyzerController {

    @Autowired
    private lateinit var lineMessagingClient: LineMessagingClient

    @EventMapping
    fun handleTextMessageEvent(event: MessageEvent<TextMessageContent>) {
        println("event: $event")
        val messageText = event.message.text
        val userId = event.source.userId
        reply(event.replyToken, messageText)
    }

    private fun reply(token: String, text: String) {
        lineMessagingClient
                .replyMessage(ReplyMessage(token, TextMessage(text)))
    }

    private fun pushMessage(userId: String, text: String) {
        lineMessagingClient.pushMessage(PushMessage(userId, TextMessage(text)))
    }
}