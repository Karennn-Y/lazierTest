package com.example.laziertest.persist.entity.module;


import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "YOUTUBE")
@Builder
public class Youtube {

  @Id
  private String videoId;

  private String channelName;
  private String contentName;
  private String hit;
  private LocalDateTime createdAt;
  private String length;

  private String videoUrl;
  private String imagePath;

  private LocalDateTime updatedAt;


}



