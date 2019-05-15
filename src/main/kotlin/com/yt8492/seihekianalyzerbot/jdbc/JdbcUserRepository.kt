package com.yt8492.seihekianalyzerbot.jdbc

import com.yt8492.seihekianalyzerbot.entity.User
import com.yt8492.seihekianalyzerbot.repository.UserRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class JdbcUserRepository(private val jdbcTemplate: JdbcTemplate) : UserRepository {
    private val rowMapper = RowMapper<User> { rs, rowNum ->
        User(rs.getLong("id"), rs.getString("line_id"))
    }

    override fun findAll(): List<User> {
        return jdbcTemplate.query("SELECT id, line_id FROM user", rowMapper)
    }

    override fun save(lineId: String): User {
        val id = jdbcTemplate.queryForObject("INSERT INTO user (line_id) VALUES (?)", Long::class.java, lineId)
        return User(id, lineId)
    }
}