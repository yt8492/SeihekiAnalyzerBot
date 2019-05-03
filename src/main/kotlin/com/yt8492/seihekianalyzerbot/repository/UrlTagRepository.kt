package com.yt8492.seihekianalyzerbot.repository

import com.yt8492.seihekianalyzerbot.entity.UrlTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlTagRepository : CrudRepository<UrlTag, Long> {
    fun findByUrlId(urlId: Long): List<UrlTag>

    fun findByUrlIdAndTagId(urlId: Long, tagId: Long): UrlTag?
}