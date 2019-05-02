package com.yt8492.seihekianalyzerbot.entity

import javax.persistence.*

@Entity
@Table(name = "url")
data class Url(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        @Column(unique = true)
        val url: String
)