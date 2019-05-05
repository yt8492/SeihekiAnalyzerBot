package com.yt8492.seihekianalyzerbot.jdbc

import com.yt8492.seihekianalyzerbot.entity.Test
import com.yt8492.seihekianalyzerbot.repository.TestRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class JdbcTestRepository(private val jdbcTemplate: JdbcTemplate) : TestRepository {
    private val rowMapper = RowMapper<Test> { rs, rowNum ->
        Test(rs.getLong("id"), rs.getString("test_data"))
    }

    override fun findById(id: Long): Test? {
        return jdbcTemplate.query("SELECT id, test_data FROM test WHERE id = ?", rowMapper, id).firstOrNull()
    }

    override fun findByTestData(testData: String): Test? {
        return jdbcTemplate.query("SELECT id, test_data FROM test WHERE test_data = ?", rowMapper, testData).firstOrNull()
    }

    override fun save(test: String): Test {
        jdbcTemplate.update("INSERT INTO test (test_data) VALUES (?)", test)
        val id = jdbcTemplate.queryForObject("SELECT last_insert_id()", Long::class.java) ?: error("insert failed")
        return Test(id, test)
    }
}