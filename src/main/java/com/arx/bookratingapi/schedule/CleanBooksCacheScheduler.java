package com.arx.bookratingapi.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CleanBooksCacheScheduler {

  @CacheEvict(value = "books", allEntries = true)
  @Scheduled(cron = "${app.scheduler.clean-book-cache-cron}")
  public void cleanBooksCacheScheduler() {
    log.info("Cleaning books cache");
  }

}
