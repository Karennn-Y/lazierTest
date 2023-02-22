package com.example.laziertest.scraper;

import com.example.laziertest.dto.module.ExchangeDto;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExchangeScraper {
    private static String EXCHANGE_URL = "https://www.kita.net/cmmrcInfo/ehgtGnrlzInfo/rltmEhgt.do";

    public List<ExchangeDto> scrap() {

        List<ExchangeDto> exchangeDtoList = new ArrayList<>();

        try {
            Document document = Jsoup.connect(EXCHANGE_URL).get();

            for (int i = 0; i < 10; i++) {
                Elements tbody = document.select(
                    "#contents > div.boardArea > div.tableSt.st4.alc > table > tbody > "
                        + "tr:nth-child(" + (i + 1) + ")");

                Elements div = document.select(
                    "#contents > div.boardArea > div.titArea2 > div.exInfo");

                String[] str = tbody.text().split("\n");
                String[] exInfo = str[0].split(" ");

                String[] str2 = div.text().split("\n");
                String[] exInfo2 = str2[0].split(" ");

                ExchangeDto exchangeDto = ExchangeDto.builder()
                                                .currencyName(exInfo[0])
                                                .countryName(exInfo[1])
                                                .tradingStandardRate(exInfo[2])
                                                .comparedPreviousDay(exInfo[3] + " " + exInfo[4])
                                                .fluctuationRate(exInfo[5])
                                                .buyCash(exInfo[6])
                                                .sellCash(exInfo[7])
                                                .sendMoney(exInfo[8])
                                                .receiveMoney(exInfo[9])
                                                .updateAt(updatedDateText(LocalDateTime.now()))
                                                .round(exInfo2[1])
                                                .build();
                exchangeDtoList.add(exchangeDto);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return exchangeDtoList;
    }

    public String updatedDateText(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return now != null ? now.format(formatter) : "";
    }

}
