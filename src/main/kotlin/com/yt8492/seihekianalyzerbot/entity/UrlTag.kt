package com.yt8492.seihekianalyzerbot.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "url_tag")
data class UrlTag(
        @Id
        @GeneratedValue
        val id: Long? = null,
        val urlId: Long,
        val tagId: Long
)