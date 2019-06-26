package com.yt8492.seihekianalyzerbot

import com.yt8492.seihekianalyzerbot.exposed.table.LineUsers
import com.yt8492.seihekianalyzerbot.exposed.table.Tags
import com.yt8492.seihekianalyzerbot.exposed.table.WorkTags
import com.yt8492.seihekianalyzerbot.exposed.table.Works
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
open class SeihekianalyzerbotApplication {
    @Bean
    fun transactionManager(dataSource: DataSource) = SpringTransactionManager(dataSource)

    @Bean
    fun createTables() {
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Tags, Works, WorkTags, LineUsers)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SeihekianalyzerbotApplication>(*args)
}
