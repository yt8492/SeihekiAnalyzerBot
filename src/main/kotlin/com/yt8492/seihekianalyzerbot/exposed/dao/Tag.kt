package com.yt8492.seihekianalyzerbot.exposed.dao

import com.yt8492.seihekianalyzerbot.exposed.table.Tags
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass

class Tag(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Tag>(Tags)

    var tag by Tags.tag
}