package com.sorsix.urlshortenerinterns.domain

sealed interface UrlResult

data class UrlSaved(val url: Url) : UrlResult

data class UrlNotValidOrDoesNotExist(val error: String) : UrlResult

data class UrlSavingError(val error: String) : UrlResult