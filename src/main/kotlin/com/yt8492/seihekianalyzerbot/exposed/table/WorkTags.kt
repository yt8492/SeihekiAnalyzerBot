package com.yt8492.seihekianalyzerbot.exposed.table

import org.jetbrains.exposed.sql.Table

object WorkTags : Table() {
    val work = reference("work", Works).primaryKey(0)
    val tag = reference("tag", Tags).primaryKey(1)
}