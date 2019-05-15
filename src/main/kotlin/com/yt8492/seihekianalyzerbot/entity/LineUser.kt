package com.yt8492.seihekianalyzerbot.entity

import javax.persistence.*

@Entity
@Table(name = "line_user")
data class LineUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val line_id: String
)