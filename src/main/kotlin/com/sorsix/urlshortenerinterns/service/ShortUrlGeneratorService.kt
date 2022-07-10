package com.sorsix.urlshortenerinterns.service

@FunctionalInterface
interface ShortUrlGeneratorService {

    fun generateShortUrl(): String

}