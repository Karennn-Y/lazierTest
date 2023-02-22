package com.example.laziertest.scraper;

import com.example.laziertest.dto.module.ScrapedResult;
import com.example.laziertest.dto.module.YoutubeDto;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class YoutubeScraper {

  ScrapedResult scrapResult = new ScrapedResult();
  String html = null;
  HttpURLConnection con;
  String YOUTUBE_URL = "https://www.youtube.com/feed/trending";

  public ScrapedResult crawl() {
    scrapResult = new ScrapedResult();
    try {
      URL url = new URL(YOUTUBE_URL);
      con = (HttpURLConnection) url.openConnection();
      con.setUseCaches(true);
      con.setConnectTimeout(5000);
      BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      StringBuilder sb = new StringBuilder();
      while (true) {
        String line = br.readLine();
        if (line == null) {
          break;
        }
        sb.append(line + "\n");
      }
      html = sb.toString();
      br.close();
      con.disconnect();
      String str;
      String str2 = null;
      Document document = Jsoup.parse(html);
      //<script> </script> 태그 찾기
      Elements elements = document.getElementsByTag("script");
      for (
          Element element : elements) {
        //text 중 "var ytInitialData" 로 시작하는 html 찾기
        if (element.html().startsWith("var ytInitialData")) {
          str = element.html();
          str = str.replace("var ytInitialData = ", "");
          str2 = str.substring(0, str.lastIndexOf(";"));
        }
      }
      //찾아낸 html 에 대하여 Json으로 파싱 후 관련된 부분까지 drill

      Gson gson = new Gson();
      JsonObject jsonObject = gson.fromJson(str2, JsonObject.class);

      // 비디오클립 리스트를 JsonArray 으로 받아오기
      JsonArray hotClipArray = jsonObject.getAsJsonObject("contents")
          .getAsJsonObject("twoColumnBrowseResultsRenderer")
          .get("tabs").getAsJsonArray().get(0)
          .getAsJsonObject()
          .getAsJsonObject("tabRenderer")
          .getAsJsonObject("content")
          .getAsJsonObject("sectionListRenderer")
          .getAsJsonArray("contents").get(3).getAsJsonObject()
          .getAsJsonObject("itemSectionRenderer")
          .getAsJsonArray("contents").get(0).getAsJsonObject()
          .getAsJsonObject("shelfRenderer")
          .getAsJsonObject("content")
          .getAsJsonObject("expandedShelfContentsRenderer")
          .getAsJsonArray("items");

      //Array 순서대로 돌며 youtube 객체로 저장
      List<YoutubeDto> youtubeDtoList = new ArrayList<>();

      for (int i = 0; i < hotClipArray.size(); i++) {
        YoutubeDto youtubeDto = new YoutubeDto();
        JsonObject baseObject = hotClipArray.get(i).getAsJsonObject()
            .getAsJsonObject("videoRenderer");
        youtubeDto.setVideoId(baseObject.get("videoId").getAsString());
        youtubeDto.setVideoUrl("https://www.youtube.com/watch?v=" + baseObject.get("videoId").getAsString());
        youtubeDto.setContentName(
            baseObject.getAsJsonObject("title").getAsJsonArray("runs").get(0).getAsJsonObject()
                .get("text").getAsString());
        youtubeDto.setChannelName(
            baseObject.getAsJsonObject("longBylineText").getAsJsonArray("runs").get(0)
                .getAsJsonObject().get("text").getAsString());
        youtubeDto.setImagePath(baseObject.getAsJsonObject("thumbnail")
            .getAsJsonArray("thumbnails").get(0).getAsJsonObject().get("url").getAsString());

        //수정필요
        String dateString = baseObject.getAsJsonObject("publishedTimeText").get("simpleText")
            .getAsString();               //*일 전   //*주 전
        String beforeText = dateString.substring(0, dateString.lastIndexOf(" ")).replace("스트리밍 시간: ","").trim();
        String beforeDayOrWeek = beforeText.substring(beforeText.length() - 1);
        int beforeNum ;
        if (beforeDayOrWeek.equals("간")) {
          beforeNum = Integer.parseInt(beforeText.substring(0, beforeText.length() - 2));
        } else beforeNum = Integer.parseInt(beforeText.substring(0, beforeText.length() - 1));

        if (beforeDayOrWeek.equals("일")) {
          youtubeDto.setCreatedAt(LocalDateTime.now().minusDays(beforeNum));
        } else if (beforeDayOrWeek.equals("간")) {
          youtubeDto.setCreatedAt(LocalDateTime.now().minusHours(beforeNum));
        } else {
          youtubeDto.setCreatedAt(LocalDateTime.now().minusWeeks(beforeNum));
        }

        youtubeDto.setLength(
            baseObject.getAsJsonObject("lengthText").get("simpleText").getAsString());
        youtubeDto.setHit(baseObject.getAsJsonObject("viewCountText")
            .get("simpleText").getAsString());

        youtubeDto.setUpdatedAt(LocalDateTime.now());

//                컨텐츠 설명은 우선 저장하지 않는 것으로
//                youtube.setContentExplanation(baseObject.getAsJsonObject("descriptionSnippet")
//                        .getAsJsonArray("runs").get(0).getAsJsonObject().get("text").getAsString());
        youtubeDtoList.add(youtubeDto);
      }
      scrapResult.setYoutubeDtoList(youtubeDtoList);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return scrapResult;
  }
}
