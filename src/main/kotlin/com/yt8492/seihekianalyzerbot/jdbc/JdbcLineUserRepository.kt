package com.yt8492.seihekianalyzerbot.jdbc

import com.yt8492.seihekianalyzerbot.entity.LineUser
import com.yt8492.seihekianalyzerbot.repository.LineUserRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class JdbcLineUserRepository(private val jdbcTemplate: JdbcTemplate) : LineUserRepository {
    private val rowMapper = RowMapper<LineUser> { rs, rowNum ->
        LineUser(rs.getLong("id"), rs.getString("line_id"))
    }

    override fun findAll(): List<LineUser> {
        return jdbcTemplate.query("SELECT id, line_id FROM line_user", rowMapper)
    }

    override fun save(lineId: String): LineUser {
        val id = jdbcTemplate.queryForObject("INSERT INTO line_user (line_id) VALUES (?)", Long::class.java, lineId)
        return LineUser(id, lineId)
    }
}