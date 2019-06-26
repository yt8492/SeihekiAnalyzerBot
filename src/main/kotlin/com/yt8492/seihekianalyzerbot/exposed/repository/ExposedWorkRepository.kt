package com.yt8492.seihekianalyzerbot.exposed.repository

import com.yt8492.seihekianalyzerbot.exposed.dao.Tag
import com.yt8492.seihekianalyzerbot.exposed.dao.Work
import com.yt8492.seihekianalyzerbot.exposed.table.Works
import com.yt8492.seihekianalyzerbot.repository.WorkRepository
import org.jetbrains.exposed.sql.SizedCollection
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
        val tags = work.tags
                .map { Tag.new { this.tag = it } }
                .let { SizedCollection(it) }
        return Work.new {
            this.url = work.url
        }.apply {
            this.tags = tags
        }.toModel()
    }
}