package com.yt8492.seihekianalyzerbot.jdbc

import com.yt8492.seihekianalyzerbot.entity.Tag
import com.yt8492.seihekianalyzerbot.repository.TagRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository

@Repository
class JdbcTagRepository(private val jdbcTemplate: JdbcTemplate) : TagRepository {
    private val rowMapper = RowMapper<Tag> { rs, rowNum ->
        Tag(rs.getLong("id"), rs.getString("tag"))
    }

    override fun findById(id: Long): Tag? {
        return jdbcTemplate.query("SELECT id, tag FROM tag WHERE id = ?", rowMapper, id).firstOrNull()
    }

    override fun findByTag(tag: String): Tag? {
        return jdbcTemplate.query("SELECT id, tag FROM tag WHERE tag = ?", rowMapper, tag).firstOrNull()
    }

    override fun save(tag: String): Tag {
        val id = jdbcTemplate.queryForObject("INSERT INTO tag (tag) VALUES (?) RETURNING id",Long::class.java, tag)
        return Tag(id, tag)
    }
}