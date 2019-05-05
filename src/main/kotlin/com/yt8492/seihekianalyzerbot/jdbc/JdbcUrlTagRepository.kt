package com.yt8492.seihekianalyzerbot.jdbc

import com.yt8492.seihekianalyzerbot.entity.UrlTag
import com.yt8492.seihekianalyzerbot.repository.UrlTagRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class JdbcUrlTagRepository(private val jdbcTemplate: JdbcTemplate) : UrlTagRepository {
    private val rowMapper = RowMapper<UrlTag> { rs, rowNum ->
        UrlTag(rs.getLong("id"), rs.getLong("url_id"), rs.getLong("tag_id"))
    }

    override fun findAllByUrlId(urlId: Long): List<UrlTag> {
        return jdbcTemplate.query("SELECT id, url_id, tag_id FROM url_tag WHERE url_id = ?", rowMapper, urlId)
    }

    override fun findByUrlIdAndTagId(urlId: Long, tagId: Long): UrlTag? {
        return jdbcTemplate.query("SELECT id, url_id, tag_id FROM url_tag WHERE url_id = ? AND tag_id = ?",
                rowMapper, urlId, tagId)
                .firstOrNull()
    }

    override fun save(urlId: Long, tagId: Long): UrlTag {
        jdbcTemplate.update("INSERT INTO url_tag (url_id, tag_id) VALUES (?, ?)", urlId, tagId)
        val id = jdbcTemplate.queryForObject("SELECT last_insert_id()", Long::class.java) ?: error("insert failed")
        return UrlTag(id, urlId, tagId)
    }
}