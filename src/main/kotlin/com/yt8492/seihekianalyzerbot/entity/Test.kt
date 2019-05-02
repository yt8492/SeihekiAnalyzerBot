package com.yt8492.seihekianalyzerbot.entity

import javax.persistence.*

@Entity
@Table(name = "test")
data class Test(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        @Column(unique = true)
        val testData: String
)