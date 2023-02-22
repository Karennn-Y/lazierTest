package com.example.laziertest.controller;

import com.example.laziertest.dto.module.YoutubeDto;
import com.example.laziertest.service.Impl.YoutubeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/youtube")
@RequiredArgsConstructor
public class YoutubeController {

  private final YoutubeService youtubeService;
//    private final CacheManager redisCacheManager;

  @GetMapping
  public ResponseEntity<?> showYoutube() {
    List<YoutubeDto> result = this.youtubeService.getYoutube();
    return ResponseEntity.ok(result);
  }

  //서비스 미사용 컨트롤러(작동확인용)
  @GetMapping("/init")
  public ResponseEntity<?> initDB() {
    this.youtubeService.youtubeinit();
    return ResponseEntity.ok("유튜브 DB가 크롤링되었습니다.");
  }

  //서비스 미사용 컨트롤러(작동확인용)
  @DeleteMapping("/dbupdate")
  public ResponseEntity<?> deleteOldYoutube() {
    this.youtubeService.updateYoutubeDbTest();
    return ResponseEntity.ok("오래된 DB를 삭제하였습니다.");
  }
}


