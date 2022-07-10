package com.sorsix.urlshortenerinterns.web

sealed interface UrlResponse

data class UrlSuccess(val originalUrl: String, val shortUrl: String) : UrlResponse

data class UrlError(val error: String) : UrlResponse