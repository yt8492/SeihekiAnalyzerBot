package com.yt8492.seihekianalyzerbot.repository

import com.yt8492.seihekianalyzerbot.entity.Url

interface UrlRepository {
    fun findAll(): List<Url>
    fun findById(id: Long): Url?
    fun findByUrl(url: String): Url?
    fun save(url: String): Url
}