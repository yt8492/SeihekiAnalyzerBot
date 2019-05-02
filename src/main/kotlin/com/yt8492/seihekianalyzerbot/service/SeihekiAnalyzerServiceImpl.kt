package com.yt8492.seihekianalyzerbot.service

import com.yt8492.seihekianalyzerbot.entity.Tag
import com.yt8492.seihekianalyzerbot.entity.Url
import com.yt8492.seihekianalyzerbot.entity.UrlTag
import com.yt8492.seihekianalyzerbot.entity.Work
import com.yt8492.seihekianalyzerbot.property.SeihekiAnalyzerConfiguration
import com.yt8492.seihekianalyzerbot.repository.TagRepository
import com.yt8492.seihekianalyzerbot.repository.UrlRepository
import com.yt8492.seihekianalyzerbot.repository.UrlTagRepository
import com.yt8492.seihekianalyzerbot.tools.SeihekiAnalyzer
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SeihekiAnalyzerServiceImpl(private val urlRepository: UrlRepository,
                                 private val tagRepository: TagRepository,
                                 private val urlTagRepository: UrlTagRepository,
                                 private val seihekiAnalyzerConfiguration: SeihekiAnalyzerConfiguration)
    : SeihekiAnalyzerService {
    override fun findAll(): List<Work> {
        val seihekiAnalyzer = SeihekiAnalyzer.login(seihekiAnalyzerConfiguration.id, seihekiAnalyzerConfiguration.password) ?: return listOf()
        val allUrls = seihekiAnalyzer.getUrls()
        val existsUrls = urlRepository.findAll().map(Url::url)
        val notExistsUrls = allUrls.subtract(existsUrls)
        val notExistsWorks = notExistsUrls.associate { it to SeihekiAnalyzer.getTagsfromUrl(it) }.map { Work(it.key, it.value) }
        notExistsWorks.forEach { saveWork(it) }
        val allWorks = urlRepository.findAll().map { url ->
            fetchWorkByUrl(url.url)
        }
        return allWorks
    }

    override fun fetchWorkByUrl(url: String): Work = Work(url, urlTagRepository.findByUrlId(urlRepository.findByUrl(url).id!!).map { urlTag ->
        tagRepository.findByIdOrNull(urlTag.tagId)!!.tag
    })

    override fun saveWork(work: Work) {
        val url = Url(url = work.url)
        val tags = work.tags.map { Tag(tag = it) }
        urlRepository.save(url)
        urlRepository.flush()
        tagRepository.saveAll(tags)
        tagRepository.flush()
        val urlTags = tags.map { UrlTag(urlId = url.id!!, tagId = it.id!!) }
        urlTagRepository.saveAll(urlTags)
        urlTagRepository.flush()
    }
}