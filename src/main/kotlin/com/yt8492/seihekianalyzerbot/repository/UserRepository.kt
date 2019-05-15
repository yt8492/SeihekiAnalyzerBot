package com.yt8492.seihekianalyzerbot.repository

import com.yt8492.seihekianalyzerbot.entity.User

interface UserRepository {
    fun findAll(): List<User>
    fun save(lineId: String): User
}