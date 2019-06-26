package com.yt8492.seihekianalyzerbot.exposed.repository

import com.yt8492.seihekianalyzerbot.exposed.dao.Work
import com.yt8492.seihekianalyzerbot.exposed.table.Tags
import com.yt8492.seihekianalyzerbot.exposed.table.WorkTags
import com.yt8492.seihekianalyzerbot.exposed.table.Works
import com.yt8492.seihekianalyzerbot.repository.WorkRepository
import org.jetbrains.exposed.sql.insertIgnore
import org.springframework.stereotype.Repository

@Repository
class ExposedWorkRepository : WorkRepository {
    override fun findAll(): List<com.yt8492.seihekianalyzerbot.model.Work> {
        return Work.all()
                .map { it.toModel() }
    }

    override fun findAllByUrl(urls: List<String>): List<com.yt8492.seihekianalyzerbot.model.Work> {
        return Work.find { Works.url inList urls }
                .map { it.toModel() }
    }

    override fun findByUrl(url: String): com.yt8492.seihekianalyzerbot.model.Work? {
        return Work.find {
            Works.url eq url
        }.firstOrNull()?.toModel()
    }

    override fun save(work: com.yt8492.seihekianalyzerbot.model.Work): com.yt8492.seihekianalyzerbot.model.Work {
        val insertWork = Works.insertIgnore {
            it[url] = work.url
        }
        work.tags.forEach { tag ->
            val tagId = Tags.insertIgnore {
                it[this.tag] = tag
            } get Tags.id
            WorkTags.insertIgnore {
                it[this.tag] = tagId
                it[this.work] = insertWork get Works.id
            }
        }
        return work
    }
}