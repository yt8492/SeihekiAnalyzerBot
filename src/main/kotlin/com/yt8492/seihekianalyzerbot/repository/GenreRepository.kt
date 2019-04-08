package com.yt8492.seihekianalyzerbot.repository

import javax.persistence.*

@Entity
@Table(name = "genre")
data class Genre(
        @Id
        @Column(name = "id", nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,
        @Column(name = "url", nullable = false)
        val url: String
)