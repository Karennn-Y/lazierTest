package com.example.laziertest.dto.module;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ScrapedResult {

  private List<YoutubeDto> youtubeDtoList;

  public ScrapedResult() {
    this.youtubeDtoList = new ArrayList<>();
  }

}
