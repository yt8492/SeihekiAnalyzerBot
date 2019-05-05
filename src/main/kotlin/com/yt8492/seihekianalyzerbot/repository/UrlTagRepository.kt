package com.yt8492.seihekianalyzerbot.repository

import com.yt8492.seihekianalyzerbot.entity.UrlTag

interface UrlTagRepository {
    fun findAllByUrlId(urlId: Long): List<UrlTag>
    fun findByUrlIdAndTagId(urlId: Long, tagId: Long): UrlTag?
    fun save(urlId: Long, tagId: Long): UrlTag
}