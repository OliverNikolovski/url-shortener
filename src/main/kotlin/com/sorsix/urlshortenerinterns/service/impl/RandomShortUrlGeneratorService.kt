package com.sorsix.urlshortenerinterns.service.impl

import com.sorsix.urlshortenerinterns.service.ShortUrlGeneratorService
import org.springframework.stereotype.Service


@Service
class RandomShortUrlGeneratorService : ShortUrlGeneratorService {

    val chars: List<Char> = ('0'..'9') + ('a'..'z')

    /**
     * Generates random short URL id, composed of lowercase letters and numbers, of length 4
     */
    override fun generateShortUrl(): String {
        return Array(4) { chars.random() }.joinToString(separator = "")
    }

}