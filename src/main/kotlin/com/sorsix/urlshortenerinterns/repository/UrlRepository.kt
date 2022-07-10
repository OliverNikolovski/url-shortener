package com.sorsix.urlshortenerinterns.repository

import com.sorsix.urlshortenerinterns.domain.Url
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface UrlRepository: JpaRepository<Url, Long> {

    fun findByOriginalUrl(originalUrl: String): Url?

    fun findByShortUrlId(shortUrlId: String): Url?

    fun deleteUrlsByTimestampBefore(timestamp: LocalDateTime)

    fun deleteUrlByOriginalUrlEquals(originalUrl: String)

    fun deleteUrlByShortUrlIdEquals(shortUrlId: String)

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Url u SET u.timestamp = :newTimestamp WHERE u.id = :urlId")
    fun updateTimestampForUrl(urlId: Long, newTimestamp: LocalDateTime)

}