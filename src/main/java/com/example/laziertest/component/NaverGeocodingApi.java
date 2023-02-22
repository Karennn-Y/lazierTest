package com.example.laziertest.component;

import com.example.laziertest.exception.WrongAddressFormException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NaverGeocodingApi {

    @Value("${naver.geocoding.url}")
    private String statisticUrl;

    @Value("${naver.geocoding.id}")
    private String clientId;

    @Value("${naver.geocoding.key}")
    private String key;

    public String getGeoCode(String location) {
        String data = getData(location);

        StringBuilder geoCode = new StringBuilder();
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
            JSONArray jsonArray = (JSONArray) jsonObject.get("addresses");

            if (jsonArray.isEmpty()) {
                throw new WrongAddressFormException("주소가 잘못되었습니다.");

            }

            for (Object o : jsonArray) {
                JSONObject object = (JSONObject) o;
                if (object.get("x") != null && object.get("y") != null) {
                    geoCode.append(object.get("x").toString()).append(",")
                        .append(object.get("y").toString());
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse data");
        }
        return geoCode.toString();
    }

    public String getData(String location) {
        StringBuilder sb = new StringBuilder();

        try {
            String address = URLEncoder.encode(location, "UTF-8");
            String apiUrl = statisticUrl + address;

            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            connection.setRequestProperty("X-NCP-APIGW-API-KEY", key);

            InputStreamReader input = new InputStreamReader(connection.getInputStream(),
                StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(input);

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
