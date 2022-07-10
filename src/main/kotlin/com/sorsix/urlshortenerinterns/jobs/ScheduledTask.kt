package com.sorsix.urlshortenerinterns.jobs

import com.sorsix.urlshortenerinterns.service.UrlService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class ScheduledTask(val urlService: UrlService) {

    /**
     *  Every 7 days, delete the URLs that are older than 7 days
     */
    @Scheduled(fixedDelay = 7, timeUnit = TimeUnit.DAYS)
    fun deleteOldUrls() {
        urlService.deleteUrlsOlderThan(7)
    }

}