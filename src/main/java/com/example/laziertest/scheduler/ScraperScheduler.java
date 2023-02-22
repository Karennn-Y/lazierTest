package com.example.laziertest.scheduler;

import com.example.laziertest.persist.entity.module.UserWeather;
import com.example.laziertest.persist.repository.UserWeatherRepository;
import com.example.laziertest.service.Impl.WeatherService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ScraperScheduler {

    private final UserWeatherRepository userWeatherRepository;
    private final WeatherService weatherService;

    @Scheduled(cron = "${scheduler.scrap.weather}")
    public void naverWeatherScheduling() {
        log.info("scraping is started");

        List<UserWeather> userWeathers = userWeatherRepository.findAll();

        for (UserWeather userWeather : userWeathers) {
            log.info(userWeather.getLazierUser().getUserId().toString());
            weatherService.add(userWeather);
            // 연속 요청 방지
            try {
                Thread.sleep(3000); // 3초 정지
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

}
