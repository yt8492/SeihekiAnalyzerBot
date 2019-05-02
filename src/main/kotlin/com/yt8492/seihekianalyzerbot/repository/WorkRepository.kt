package com.yt8492.seihekianalyzerbot.repository

interface WorkRepository {
    fun fetchUrls(): List<String>
}