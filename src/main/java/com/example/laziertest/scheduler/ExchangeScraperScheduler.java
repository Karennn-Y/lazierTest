package com.example.laziertest.scheduler;

import com.example.laziertest.service.Impl.ExchangeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ExchangeScraperScheduler {
    private final ExchangeService exchangeService;

    // 일정 기간동안 수행
    @Scheduled(cron = "${scheduler.scrap.exchange}")
    public void exchangeScheduling() {
        log.info("scraping exchange scheduler is started");
        exchangeService.delete();
        exchangeService.add();
    }
}
