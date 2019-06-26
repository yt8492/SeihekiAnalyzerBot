package com.yt8492.seihekianalyzerbot.repository

import com.yt8492.seihekianalyzerbot.model.Work

interface WorkRepository {
    fun findAll(): List<Work>
    fun findAllByUrl(urls: List<String>): List<Work>
    fun findByUrl(url: String): Work?
    fun save(work: Work): Work
}