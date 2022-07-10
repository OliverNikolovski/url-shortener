package com.sorsix.urlshortenerinterns.service

import com.sorsix.urlshortenerinterns.domain.Url
import com.sorsix.urlshortenerinterns.domain.UrlResult

interface UrlService {

    fun getAllUrls(): List<Url>

    fun getUrlById(id: Long): Url?

    fun getOriginalUrl(originalUrl: String): Url?

    fun getShortUrl(shortUrlId: String): Url?

    fun saveUrl(originalUrl: String): UrlResult

    fun deleteUrlByOriginalName(originalUrl: String)

    fun deleteUrlByShortName(shortUrlId: String)

    fun deleteUrlById(id: Long)

    fun deleteUrlsOlderThan(numberOfDays: Long)

}