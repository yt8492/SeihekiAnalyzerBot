package com.yt8492.seihekianalyzerbot.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "url")
data class Url(
        @Id
        @GeneratedValue
        val id: Long? = null,
        val url: String
)