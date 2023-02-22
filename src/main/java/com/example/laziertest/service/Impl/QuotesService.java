package com.example.laziertest.service.Impl;

import com.example.laziertest.dto.module.QuotesDto;
import com.example.laziertest.exception.QuotesException.WrongIdNumberException;
import com.example.laziertest.persist.entity.module.Quotes;
import com.example.laziertest.persist.repository.QuotesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuotesService {

    public final QuotesRepository quotesRepository;

    public QuotesDto get() {
        // DB에 10개만 저장 예정이여서 10개로 설정하였습니다.
        long randomNumber = (long) (Math.random() * 10 + 1);
        Quotes quotes = quotesRepository.findById(randomNumber)
            .orElseThrow(() -> new WrongIdNumberException("잘못된 아이디 정보 입니다."));
        return QuotesDto.of(quotes);
    }
}
