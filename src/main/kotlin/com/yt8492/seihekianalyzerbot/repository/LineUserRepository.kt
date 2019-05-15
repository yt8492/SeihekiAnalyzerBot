package com.yt8492.seihekianalyzerbot.repository

import com.yt8492.seihekianalyzerbot.entity.LineUser

interface LineUserRepository {
    fun findAll(): List<LineUser>
    fun save(lineId: String): LineUser
}