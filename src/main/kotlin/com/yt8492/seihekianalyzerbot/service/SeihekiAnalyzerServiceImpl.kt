package com.yt8492.seihekianalyzerbot.service

import com.yt8492.seihekianalyzerbot.model.*
import com.yt8492.seihekianalyzerbot.property.SeihekiAnalyzerConfiguration
import com.yt8492.seihekianalyzerbot.repository.*
import com.yt8492.seihekianalyzerbot.tools.SeihekiAnalyzer
import org.springframework.stereotype.Service

@Service
class SeihekiAnalyzerServiceImpl(private val lineUserRepository: LineUserRepository,
                                 private val workRepository: WorkRepository,
                                 seihekiAnalyzerConfiguration: SeihekiAnalyzerConfiguration)
    : SeihekiAnalyzerService {
    val seihekiAnalyzer = SeihekiAnalyzer.login(seihekiAnalyzerConfiguration.id, seihekiAnalyzerConfiguration.password)
            ?: error("invalid userId or password")

    override fun findAllWorks(): List<Work> {
        val allUrls = seihekiAnalyzer.getUrls()
        val allWorks = allUrls.map { findOrCreateWorkByUrl(it) }
        return allWorks
    }

    override fun findOrCreateWorkByUrl(url: String): Work {
        return workRepository.findByUrl(url) ?: createWork(url)
    }

    private fun createWork(url: String): Work {
        val tags = SeihekiAnalyzer.getTagsFromUrl(url)
        val work =  Work(url, tags)
        return workRepository.save(work)
    }

    override fun registerUser(userId: String) {
        lineUserRepository.save(userId)
    }

    override fun findAllUserIds(): List<String> {
        return lineUserRepository.findAll()
                .map(LineUser::lineId)
    }

    override fun getAnalyzeResults(): List<AnalyzeResult> {
        val works = findAllWorks()
        val tagCnt = mutableMapOf<String, Int>()
        works.flatMap(Work::tags).forEach { tag ->
            var cnt = tagCnt[tag] ?: 0
            cnt++
            tagCnt[tag] = cnt
        }
        val result = tagCnt.asSequence()
                .sortedByDescending { it.value }
                .map { AnalyzeResult(it.key, (it.value.toDouble() / works.size) * 100) }
                .toList()
        return result
    }

    override fun getRecommendedWorks(): List<Work> {
        val latestWorks = SeihekiAnalyzer.getLatestWorks()
                .map { Work(it.key, it.value) }
        val myFavoriteTags = getAnalyzeResults().takeWhile { it.percentage >= 10 }
                .map { it.tag }
        val recommends = latestWorks.filter { work ->
            val tagCnt = work.tags.intersect(myFavoriteTags).size
            tagCnt >= 2
        }
        return recommends
    }
}