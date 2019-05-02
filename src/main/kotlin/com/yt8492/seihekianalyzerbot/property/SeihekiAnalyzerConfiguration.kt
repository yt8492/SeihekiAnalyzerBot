package com.yt8492.seihekianalyzerbot.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "dlsite.api")
class SeihekiAnalyzerConfiguration {
    lateinit var id: String
    lateinit var password:  String
}