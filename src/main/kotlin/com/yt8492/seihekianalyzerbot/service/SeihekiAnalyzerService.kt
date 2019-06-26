package com.yt8492.seihekianalyzerbot.service

import com.yt8492.seihekianalyzerbot.model.Work

interface SeihekiAnalyzerService {
    fun findAllWorks(): List<Work>
    fun fetchWorkByUrl(url: String): Work
    fun registerUser(userId: String)
    fun findAllUserIds(): List<String>
}