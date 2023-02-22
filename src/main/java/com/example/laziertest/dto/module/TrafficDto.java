package com.example.laziertest.dto.module;

import com.example.laziertest.persist.entity.traffic.Traffic;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TrafficDto {

    private long userId;
    private String startingPoint;
    private String startingGeoCode; // 위경도 정보
    private String destination;
    private String destinationGeoCode;

    public static TrafficDto of(Traffic traffic) {
        return TrafficDto.builder()
            .userId(traffic.getLazierUser().getUserId())
            .startingPoint(traffic.getStartingPoint())
            .startingGeoCode(traffic.getStartingGeoCode())
            .destination(traffic.getDestination())
            .destinationGeoCode(traffic.getDestinationGeoCode())
            .build();
    }
}
