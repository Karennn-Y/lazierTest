package com.example.laziertest.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoNavigationApi {

    @Value("${kakao.navigation.url}")
    private String statisticUrl;

    @Value("${kakao.navigation.key}")
    private String key;

    public String getDuration(String startingGeoCode, String destinationGeoCode) {
        String data = getData(startingGeoCode, destinationGeoCode);

        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
            JSONArray jsonArray = (JSONArray) jsonObject.get("routes");
            JSONObject object = (JSONObject) jsonArray.get(0);
            JSONObject summary = (JSONObject) object.get("summary");

            return summary.get("duration").toString();
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse data");
        }
    }

    public String getData(String startingGeoCode, String destinationGeoCode) {
        try {
            String apiUrl = String.format(statisticUrl, startingGeoCode, destinationGeoCode);

            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "KakaoAK " + key);
            connection.connect();

            InputStreamReader input = new InputStreamReader(connection.getInputStream(),
                StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(input);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }

            bf.close();
            input.close();
            connection.disconnect();

            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get response");
        }
    }
}
