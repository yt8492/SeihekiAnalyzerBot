package com.yt8492.seihekianalyzerbot.exposed.table

import org.jetbrains.exposed.dao.LongIdTable

object Works : LongIdTable() {
    val url = text("url").uniqueIndex()
}