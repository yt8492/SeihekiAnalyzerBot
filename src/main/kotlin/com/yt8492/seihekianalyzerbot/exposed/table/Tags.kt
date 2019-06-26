package com.yt8492.seihekianalyzerbot.exposed.table

import org.jetbrains.exposed.dao.LongIdTable

object Tags : LongIdTable("tags") {
    val tag = text("tag").uniqueIndex()
}