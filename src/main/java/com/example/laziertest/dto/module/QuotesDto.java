package com.example.laziertest.dto.module;

import com.example.laziertest.persist.entity.module.Quotes;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuotesDto {

    private String content;
    private String writer;

    public static QuotesDto of(Quotes quotes) {
        return QuotesDto.builder()
            .content(quotes.getContent())
            .writer(quotes.getWriter())
            .build();
    }
}
