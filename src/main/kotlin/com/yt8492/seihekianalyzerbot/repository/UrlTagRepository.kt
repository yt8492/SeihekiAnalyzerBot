package com.yt8492.seihekianalyzerbot.repository

import com.yt8492.seihekianalyzerbot.entity.UrlTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UrlTagRepository : JpaRepository<UrlTag, Long> {
    fun findByUrlId(urlId: Long): List<UrlTag>

    @Query(value = "SELECT ut FROM url_tag ut where ut.url_id = ?1 and ut.tag_id = ?2", nativeQuery = true)
    fun findByUrlIdAndTagId(urlId: Long, tagId: Long): UrlTag?
}