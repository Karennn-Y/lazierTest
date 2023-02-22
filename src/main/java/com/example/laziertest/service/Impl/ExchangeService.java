package com.example.laziertest.service.Impl;

import com.example.laziertest.dto.module.ExchangeDto;
import com.example.laziertest.persist.entity.module.Exchange;
import com.example.laziertest.persist.repository.ExchangeRepository;
import com.example.laziertest.scraper.ExchangeScraper;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final ExchangeScraper scraper;

    // 환율 정보 추가 (10개 정보)
    @Transactional
    public void add() {
        delete();
        List<ExchangeDto> exchangeDto = scraper.scrap();

        for (int i = 0; i < 10; i++) {
            exchangeRepository.save(Exchange.builder()
                            .currencyName(exchangeDto.get(i).getCurrencyName())
                            .countryName(exchangeDto.get(i).getCountryName())
                            .tradingStandardRate(exchangeDto.get(i).getTradingStandardRate())
                            .comparedPreviousDay(exchangeDto.get(i).getComparedPreviousDay())
                            .fluctuationRate(exchangeDto.get(i).getFluctuationRate())
                            .buyCash(exchangeDto.get(i).getBuyCash())
                            .sellCash(exchangeDto.get(i).getSellCash())
                            .sendMoney(exchangeDto.get(i).getSendMoney())
                            .receiveMoney(exchangeDto.get(i).getReceiveMoney())
                            .updateAt(exchangeDto.get(i).getUpdateAt())
                            .round(exchangeDto.get(i).getRound())
                            .build());
        }

    }

    @Transactional
    public void delete() {
        exchangeRepository.deleteAll();
    }
}
