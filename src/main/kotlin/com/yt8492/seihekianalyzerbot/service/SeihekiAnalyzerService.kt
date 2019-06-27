package com.yt8492.seihekianalyzerbot.service

import com.yt8492.seihekianalyzerbot.model.AnalyzeResult
import com.yt8492.seihekianalyzerbot.model.Work

interface SeihekiAnalyzerService {
    fun findAllWorks(): List<Work>
    fun findOrCreateWorkByUrl(url: String): Work
    fun registerUser(userId: String)
    fun findAllUserIds(): List<String>
    fun getAnalyzeResults(): List<AnalyzeResult>
    fun getRecommendedWorks(): List<Work>
}