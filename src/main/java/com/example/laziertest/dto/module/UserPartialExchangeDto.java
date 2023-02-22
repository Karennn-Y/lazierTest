package com.example.laziertest.dto.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPartialExchangeDto {

    private String currencyName;            // 통화명
    private String countryName;             // 국가명
    private String tradingStandardRate;     // 매매기준율
    private String comparedPreviousDay;     // 전일대비
    private String fluctuationRate;         // 등락율

}
