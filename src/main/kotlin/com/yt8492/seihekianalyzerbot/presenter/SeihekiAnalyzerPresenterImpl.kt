package com.yt8492.seihekianalyzerbot.presenter

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.PushMessage
import com.linecorp.bot.model.message.TextMessage
import com.yt8492.seihekianalyzerbot.model.AnalyzeResult
import com.yt8492.seihekianalyzerbot.model.Work
import org.springframework.stereotype.Component

@Component
class SeihekiAnalyzerPresenterImpl(private val lineMessagingClient: LineMessagingClient) : SeihekiAnalyzerPresenter {
    override fun showAnalyzeResult(results: List<AnalyzeResult>, userId: String) {
        val message = results.mapIndexed { index, result ->
            "%2d位 %2.2f%%: ${result.tag}".format(index + 1, result.percentage)
        }.joinToString("\n")
        pushMessage(userId, message)
    }

    override fun showRecommends(recommendedWorks: List<Work>, userId: String) {
        val result = if (recommendedWorks.isNotEmpty()) {
            "本日のオススメ作品\n${recommendedWorks.joinToString("\n") { it.url }}"
        } else {
            "本日のオススメはありません。"
        }
        pushMessage(userId, result)
    }

    override fun autoRecommend(recommendedWorks: List<Work>, userIds: List<String>) {
        if (recommendedWorks.isNotEmpty()) {
            val result = "本日のオススメ作品\n${recommendedWorks.joinToString("\n") { it.url }}"
            userIds.forEach { userId ->
                pushMessage(userId, result)
            }
        }
    }

    private fun pushMessage(userId: String, text: String) {
        lineMessagingClient.pushMessage(PushMessage(userId, TextMessage(text)))
    }
}