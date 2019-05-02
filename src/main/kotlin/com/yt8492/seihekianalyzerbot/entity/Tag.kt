package com.yt8492.seihekianalyzerbot.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Tag(
        @Id
        @GeneratedValue
        val id: Long? = null,
        val tag: String
)