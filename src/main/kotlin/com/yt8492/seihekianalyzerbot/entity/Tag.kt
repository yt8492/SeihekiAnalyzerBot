package com.yt8492.seihekianalyzerbot.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tag")
data class Tag(
        @Id
        @GeneratedValue
        val id: Long? = null,
        val tag: String
)