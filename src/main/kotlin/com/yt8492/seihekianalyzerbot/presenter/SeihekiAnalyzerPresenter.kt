package com.yt8492.seihekianalyzerbot.presenter

import com.yt8492.seihekianalyzerbot.model.AnalyzeResult
import com.yt8492.seihekianalyzerbot.model.Work

interface SeihekiAnalyzerPresenter {
    fun showAnalyzeResult(results: List<AnalyzeResult>, userId: String)
    fun showRecommends(recommendedWorks: List<Work>, userId: String)
    fun autoRecommend(recommendedWorks: List<Work>, userIds: List<String>)
}