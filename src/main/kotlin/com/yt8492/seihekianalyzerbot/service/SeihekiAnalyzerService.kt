package com.yt8492.seihekianalyzerbot.service

import com.yt8492.seihekianalyzerbot.entity.Work

interface SeihekiAnalyzerService {
    fun findAll(): List<Work>
    fun fetchWorkByUrl(url: String): Work
    fun registerUser(userId: String)
    fun saveTest(test: String)
}