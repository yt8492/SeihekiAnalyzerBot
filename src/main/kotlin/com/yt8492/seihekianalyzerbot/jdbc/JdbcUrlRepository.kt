package com.yt8492.seihekianalyzerbot.jdbc

import com.yt8492.seihekianalyzerbot.entity.Url
import com.yt8492.seihekianalyzerbot.repository.UrlRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class JdbcUrlRepository(private val jdbcTemplate: JdbcTemplate) : UrlRepository {
    private val rowMapper = RowMapper<Url> { rs, rowNum ->
        Url(rs.getLong("id"), rs.getString("url"))
    }

    override fun findAll(): List<Url> {
        return jdbcTemplate.query("SELECT id, url FROM url", rowMapper)
    }

    override fun findById(id: Long): Url? {
        return jdbcTemplate.query("SELECT id, url FROM url WHERE id = ?", rowMapper, id).firstOrNull()
    }

    override fun findByUrl(url: String): Url? {
        return jdbcTemplate.query("SELECT id, url FROM url WHERE url = ?", rowMapper, url).firstOrNull()
    }

    override fun save(url: String): Url {
        jdbcTemplate.update("INSERT INTO url (url) VALUES (?)", url)
        val id = jdbcTemplate.queryForObject("SELECT last_insert_id()", Long::class.java) ?: error("insert failed")
        return Url(id, url)
    }
}