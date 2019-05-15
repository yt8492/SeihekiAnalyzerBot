package com.yt8492.seihekianalyzerbot.service

import com.yt8492.seihekianalyzerbot.entity.*
import com.yt8492.seihekianalyzerbot.property.SeihekiAnalyzerConfiguration
import com.yt8492.seihekianalyzerbot.repository.*
import com.yt8492.seihekianalyzerbot.tools.SeihekiAnalyzer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SeihekiAnalyzerServiceImpl(private val urlRepository: UrlRepository,
                                 private val tagRepository: TagRepository,
                                 private val urlTagRepository: UrlTagRepository,
                                 private val userRepository: UserRepository,
                                 seihekiAnalyzerConfiguration: SeihekiAnalyzerConfiguration,
                                 private val testRepository: TestRepository)
    : SeihekiAnalyzerService {
    val seihekiAnalyzer = SeihekiAnalyzer.login(seihekiAnalyzerConfiguration.id, seihekiAnalyzerConfiguration.password)
            ?: error("invalid userId or password")

    override fun findAll(): List<Work> {
        val allUrls = seihekiAnalyzer.getUrls()
        val allWorks = allUrls.map { fetchWorkByUrl(it) }
        return allWorks
    }

    override fun fetchWorkByUrl(url: String): Work {
        val urlId = urlRepository.findByUrl(url)?.id
        return if (urlId != null) {
            val urlTag = urlTagRepository.findAllByUrlId(urlId)
            val tags = urlTag.map { tagRepository.findById(it.tagId) }
                    .mapNotNull { it?.tag }
            Work(url, tags)
        } else {
            createWork(url)
        }
    }

    private fun createWork(url: String): Work {
        val urlId = urlRepository.save(url).id
        val tags = SeihekiAnalyzer.getTagsfromUrl(url)
        tags.map{ tagRepository.findByTag(it) ?: tagRepository.save(it) }
                .forEach { tag ->
                    urlTagRepository.findByUrlIdAndTagId(urlId, tag.id) ?: urlTagRepository.save(urlId, tag.id)
                }
        return Work(url, tags)
    }

    override fun registerUser(userId: String) {
        userRepository.save(userId)
    }

    override fun saveTest(test: String) {
        if (testRepository.findByTestData(test) == null) {
            testRepository.save(test)
        }
    }
}