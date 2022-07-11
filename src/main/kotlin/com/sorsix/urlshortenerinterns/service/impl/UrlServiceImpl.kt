package com.sorsix.urlshortenerinterns.service.impl

import com.sorsix.urlshortenerinterns.domain.*
import com.sorsix.urlshortenerinterns.repository.UrlRepository
import com.sorsix.urlshortenerinterns.service.ShortUrlGeneratorService
import com.sorsix.urlshortenerinterns.service.UrlService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime

@Service
class UrlServiceImpl(
    val urlRepository: UrlRepository,
    val shortUrlGeneratorService: ShortUrlGeneratorService) : UrlService {

    val logger: Logger = LoggerFactory.getLogger(UrlServiceImpl::class.java)
    val validUrlRegex =
        """https?://(www\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)""".toRegex()

    override fun getAllUrls(): List<Url> = urlRepository.findAll()

    override fun getUrlById(id: Long): Url? = urlRepository.findByIdOrNull(id)

    override fun getOriginalUrl(originalUrl: String): Url? =
        urlRepository.findByOriginalUrl(originalUrl)

    override fun getShortUrl(shortUrlId: String): Url? =
        urlRepository.findByShortUrlId(shortUrlId)

    @Transactional
    override fun saveUrl(originalUrl: String): UrlResult {
        if (!originalUrl.matches(this.validUrlRegex)) {
            this.logger.debug("Url not valid: {}", originalUrl)
            return UrlNotValidOrDoesNotExist("Invalid URL")
        }

        if (!this.urlExists(originalUrl)) {
            this.logger.debug("Url does not exist: {}", originalUrl);
            return UrlNotValidOrDoesNotExist("Invalid Hostname")
        }

        val urlFromDb = this.urlRepository.findByOriginalUrl(originalUrl)
        if (urlFromDb != null) {
            this.logger.debug("Url already exists in database: {}", urlFromDb)
            this.urlRepository.updateTimestampForUrl(urlFromDb.id, LocalDateTime.now())
            return UrlSaved(urlFromDb)
        }

        val shortUrlId = this.shortUrlGeneratorService.generateShortUrl()
        if (this.getShortUrl(shortUrlId) != null)
            this.deleteUrlByShortName(shortUrlId)

        val newUrl = Url(id = 0, originalUrl, shortUrlId, timestamp = LocalDateTime.now())

        return try {
            this.logger.info("Saving url: {}", newUrl)
            UrlSaved(this.urlRepository.save(newUrl))
        }
        catch (ex: Exception) {
            this.logger.error("Error saving url to database: {}", newUrl)
            UrlSavingError("Error saving url to database: $newUrl")
        }
    }

    override fun deleteUrlByOriginalName(originalUrl: String) =
        this.urlRepository.deleteUrlByOriginalUrlEquals(originalUrl)

    override fun deleteUrlByShortName(shortUrlId: String) =
        this.urlRepository.deleteUrlByShortUrlIdEquals(shortUrlId)

    override fun deleteUrlById(id: Long) = this.urlRepository.deleteById(id)

    override fun deleteUrlsOlderThan(numberOfDays: Long) {
        val date = LocalDateTime.now().minusDays(numberOfDays)
        this.urlRepository.deleteUrlsByTimestampBefore(date)
    }

    override fun urlExists(url: String): Boolean {
        return try {
            val urlObject = URL(url)
            val connection = urlObject.openConnection() as HttpURLConnection
            connection.requestMethod = "HEAD"
            HttpStatus.valueOf(connection.responseCode).is2xxSuccessful
        }
        catch (ex: Exception) {
            false
        }
    }

}