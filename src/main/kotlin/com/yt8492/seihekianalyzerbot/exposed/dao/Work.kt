package com.yt8492.seihekianalyzerbot.exposed.dao

import com.yt8492.seihekianalyzerbot.exposed.table.WorkTags
import com.yt8492.seihekianalyzerbot.exposed.table.Works
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass

class Work(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Work>(Works)

    var url by Works.url
    var tags by Tag via WorkTags

    fun toModel(): com.yt8492.seihekianalyzerbot.model.Work {
        return com.yt8492.seihekianalyzerbot.model.Work(
                this.url,
                this.tags.map {
                    it.tag
                }
        )
    }
}