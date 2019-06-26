package com.yt8492.seihekianalyzerbot.service

import com.yt8492.seihekianalyzerbot.model.*
import com.yt8492.seihekianalyzerbot.property.SeihekiAnalyzerConfiguration
import com.yt8492.seihekianalyzerbot.repository.*
import com.yt8492.seihekianalyzerbot.tools.SeihekiAnalyzer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SeihekiAnalyzerServiceImpl(private val lineUserRepository: LineUserRepository,
                                 private val workRepository: WorkRepository,
                                 seihekiAnalyzerConfiguration: SeihekiAnalyzerConfiguration)
    : SeihekiAnalyzerService {
    val seihekiAnalyzer = SeihekiAnalyzer.login(seihekiAnalyzerConfiguration.id, seihekiAnalyzerConfiguration.password)
            ?: error("invalid userId or password")

    override fun findAllWorks(): List<Work> {
        val allUrls = seihekiAnalyzer.getUrls()
        val allWorks = allUrls.map { fetchWorkByUrl(it) }
        return allWorks
    }

    override fun fetchWorkByUrl(url: String): Work {
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
        return lineUserRepository.findAll().map(LineUser::lineId)
    }
}