package com.example.laziertest.dto.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDto {

    private String currencyName;            // 통화명
    private String countryName;             // 국가명
    private String tradingStandardRate;     // 매매기준율
    private String comparedPreviousDay;     // 전일대비
    private String fluctuationRate;         // 등락율
    private String buyCash;                 // 현찰 살 때
    private String sellCash;                // 현찰 팔 때
    private String sendMoney;               // 송금 보낼 때
    private String receiveMoney;            // 송금 받을 때
    private String updateAt;                // 업데이트 날짜
    private String round;                   // 고시회차

}
