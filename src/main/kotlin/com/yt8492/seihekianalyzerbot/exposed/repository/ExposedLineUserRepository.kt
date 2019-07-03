package com.yt8492.seihekianalyzerbot.exposed.repository

import com.yt8492.seihekianalyzerbot.exposed.dao.LineUser
import com.yt8492.seihekianalyzerbot.repository.LineUserRepository
import org.springframework.stereotype.Repository

@Repository
class ExposedLineUserRepository : LineUserRepository {
    override fun findAll(): List<com.yt8492.seihekianalyzerbot.model.LineUser> {
        return LineUser.all()
                .map(this::dto2model)
    }

    override fun save(lineId: String): com.yt8492.seihekianalyzerbot.model.LineUser {
        return LineUser.new { this.lineId = lineId }
                .let(this::dto2model)
    }

    private fun dto2model(lineUser: LineUser): com.yt8492.seihekianalyzerbot.model.LineUser =
            com.yt8492.seihekianalyzerbot.model.LineUser(lineUser.lineId)
}