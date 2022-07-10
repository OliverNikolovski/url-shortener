package com.sorsix.urlshortenerinterns.service.impl

import com.sorsix.urlshortenerinterns.service.ShortUrlGeneratorService
import org.springframework.stereotype.Service


@Service
class RandomShortUrlGeneratorService : ShortUrlGeneratorService {

    /**
     *  Contains the ascii codes for the digits (0-9) and the lowercase letters (a-z)
     */
    val asciiCodes: List<Int> = (48..57).toList().plus((97..122).toList())

    /**
     * Generates random short URL id, composed of lowercase letters and numbers, of length 4
     */
    override fun generateShortUrl(): String {
        return Array(4) { asciiCodes.random().toChar() }.joinToString(separator = "")
    }

}