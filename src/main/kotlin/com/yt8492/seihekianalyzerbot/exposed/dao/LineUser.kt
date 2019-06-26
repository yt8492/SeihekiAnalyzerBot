package com.yt8492.seihekianalyzerbot.exposed.dao

import com.yt8492.seihekianalyzerbot.exposed.table.LineUsers
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass

class LineUser(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<LineUser>(LineUsers)

    var lineId by LineUsers.lineId

    fun toModel(): com.yt8492.seihekianalyzerbot.model.LineUser {
        return com.yt8492.seihekianalyzerbot.model.LineUser(lineId)
    }
}