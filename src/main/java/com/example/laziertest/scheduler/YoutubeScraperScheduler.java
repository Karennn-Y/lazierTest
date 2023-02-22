package com.example.laziertest.scheduler;

import com.example.laziertest.dto.module.ScrapedResult;
import com.example.laziertest.persist.entity.module.Youtube;
import com.example.laziertest.persist.repository.YoutubeRepository;
import com.example.laziertest.scraper.YoutubeScraper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@EnableCaching
@AllArgsConstructor
public class YoutubeScraperScheduler {

  private final YoutubeRepository youtubeRepository;
  private final YoutubeScraper youtubeScraper;

  //        @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
  @Scheduled(cron = "${scheduler.scrap.youtube}")
  @Transactional
  public void youtubeScheduling() {
    log.info("youtube scraping scheduler has started");

    // 업데이트 할 크롤링 내용
    ScrapedResult scrapedResult = this.youtubeScraper.crawl();

    // 스크래핑한 정보 중 없는 값을 저장
    scrapedResult.getYoutubeDtoList().stream()
        .map(e -> Youtube.builder()
            .videoId(e.getVideoId())
            .contentName(e.getContentName())
            .createdAt(e.getCreatedAt())
            .channelName(e.getChannelName())
            .updatedAt(e.getUpdatedAt())
            .length(e.getLength())
            .videoUrl(e.getVideoUrl())
            .imagePath(e.getImagePath())
            .hit(e.getHit())
            .updatedAt(LocalDateTime.now())
            .build()
        )

        .forEach(e ->
        {
          boolean exists = this.youtubeRepository.existsById(e.getVideoId());
          if (!exists) {
            this.youtubeRepository.save(e);
            System.out.println(e.getContentName() + " is saved");
          }
        });
//    스크래핑 연속요청이 되지 않도록 일시정지
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
  }

  //업로드 된지 1주일 이상 된 영상은 삭제한다.
  @Scheduled(cron = "${scheduler.scrap.youtube}")
  @Transactional
  public void youtubeDbUpdater() {
    log.info("youtube delete scheduler has started" );

    List<Youtube> youtubeEntities =
        this.youtubeRepository.findAllByCreatedAtBefore(LocalDateTime.now().minusDays(7));

    youtubeRepository.deleteAll(youtubeEntities);
  }


}

