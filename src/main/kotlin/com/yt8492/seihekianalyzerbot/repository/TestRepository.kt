package com.yt8492.seihekianalyzerbot.repository

import com.yt8492.seihekianalyzerbot.entity.Test

interface TestRepository {
    fun findById(id: Long): Test?
    fun findByTestData(testData: String): Test?
    fun save(test: String): Test
}