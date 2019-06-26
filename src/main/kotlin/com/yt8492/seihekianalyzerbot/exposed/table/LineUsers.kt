package com.yt8492.seihekianalyzerbot.exposed.table

import org.jetbrains.exposed.dao.LongIdTable

object LineUsers : LongIdTable("line_users") {
    val lineId = text("lineId").uniqueIndex()
}