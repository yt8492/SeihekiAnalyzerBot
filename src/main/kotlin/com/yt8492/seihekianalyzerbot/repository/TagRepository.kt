package com.yt8492.seihekianalyzerbot.repository

import com.yt8492.seihekianalyzerbot.entity.Tag

interface TagRepository {
    fun findById(id: Long): Tag?
    fun findByTag(tag: String): Tag?
    fun save(tag: String): Tag
}