package com.example.laziertest.dto.module;

import com.example.laziertest.persist.entity.module.Youtube;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeDto {

  private String videoId;
  private String channelName;
  private String contentName;
  private String hit; // 조회수
  private LocalDateTime createdAt;
  private String length;
  private String imagePath;
  private String videoUrl;
  private LocalDateTime updatedAt;
  @Builder
  public YoutubeDto from(Youtube entity){

    this.videoId = entity.getVideoId();
    this.channelName = entity.getChannelName();
    this.contentName = entity.getContentName();
    this.hit = entity.getHit();
    this.createdAt = entity.getCreatedAt();
    this.length = entity.getLength();
    this.videoUrl = entity.getVideoUrl();
    this.imagePath = entity.getImagePath();

    return this;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
