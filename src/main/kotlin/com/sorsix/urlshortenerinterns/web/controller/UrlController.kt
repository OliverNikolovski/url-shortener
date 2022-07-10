package com.sorsix.urlshortenerinterns.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class UrlController {

    @GetMapping
    fun getUrlShortenerPage(): String = "index.html"

}