package com.yt8492.seihekianalyzerbot.repository

import com.yt8492.seihekianalyzerbot.entity.UrlTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlTagRepository : JpaRepository<UrlTag, Long> {
    fun findByUrlId(urlId: Long): List<UrlTag>
}