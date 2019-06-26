package com.yt8492.seihekianalyzerbot

import org.jetbrains.exposed.spring.SpringTransactionManager
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
}

fun main(args: Array<String>) {
    runApplication<SeihekianalyzerbotApplication>(*args)
}
