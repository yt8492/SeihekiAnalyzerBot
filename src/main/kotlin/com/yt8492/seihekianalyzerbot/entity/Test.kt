package com.yt8492.seihekianalyzerbot.entity

import javax.persistence.*

@Entity
@Table(name = "test")
data class Test(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        val testData: String
)