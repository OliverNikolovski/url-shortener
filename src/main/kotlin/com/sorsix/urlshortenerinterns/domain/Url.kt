package com.sorsix.urlshortenerinterns.domain

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Url (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(unique = true, name = "original_url", length = 2000)
    val originalUrl: String,

    @Column(name = "short_url_id", length = 4, unique = true)
    val shortUrlId: String,

    val timestamp: LocalDateTime
)
