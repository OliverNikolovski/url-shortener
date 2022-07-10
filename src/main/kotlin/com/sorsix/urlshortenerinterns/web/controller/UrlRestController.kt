package com.sorsix.urlshortenerinterns.web.controller

import com.sorsix.urlshortenerinterns.domain.*
import com.sorsix.urlshortenerinterns.service.UrlService
import com.sorsix.urlshortenerinterns.web.UrlError
import com.sorsix.urlshortenerinterns.web.UrlRequest
import com.sorsix.urlshortenerinterns.web.UrlResponse
import com.sorsix.urlshortenerinterns.web.UrlSuccess
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shorturl")
class UrlRestController(val urlService: UrlService) {

    @PostMapping(consumes = ["application/x-www-form-urlencoded"])
    fun getShortUrl(urlRequest: UrlRequest): ResponseEntity<UrlResponse> {
        return when (val urlResult = urlService.saveUrl(urlRequest.url)) {
            is UrlSaved -> ResponseEntity.ok(UrlSuccess(urlResult.url.originalUrl, urlResult.url.shortUrlId))
            is UrlNotValid -> ResponseEntity.badRequest().body(UrlError(urlResult.error))
            is UrlSavingError -> ResponseEntity.internalServerError().body(UrlError(urlResult.error))
        }
    }

    @GetMapping("/{id}")
    fun redirectToOriginalUrl(@PathVariable id: String): ResponseEntity<Any> {
        val url: Url = this.urlService.getShortUrl(id)
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "No short URL found for the given input"))
        val responseHeaders = HttpHeaders()
        responseHeaders.set("Location", url.originalUrl)
        return ResponseEntity.status(HttpStatus.FOUND).headers(responseHeaders).build()
    }
}