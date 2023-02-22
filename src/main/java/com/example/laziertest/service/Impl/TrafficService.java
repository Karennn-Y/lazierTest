package com.example.laziertest.service.Impl;

import com.example.laziertest.component.KakaoNavigationApi;
import com.example.laziertest.component.NaverGeocodingApi;
import com.example.laziertest.dto.module.DurationDto;
import com.example.laziertest.dto.module.TrafficDto;
import com.example.laziertest.dto.module.TrafficInput;
import com.example.laziertest.exception.UserAlreadyExistException;
import com.example.laziertest.exception.UserNotFoundException;
import com.example.laziertest.persist.entity.traffic.Traffic;
import com.example.laziertest.persist.entity.user.LazierUser;
import com.example.laziertest.persist.repository.traffic.TrafficRepository;
import com.example.laziertest.service.user.MemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrafficService {

    private final TrafficRepository trafficRepository;
    private final NaverGeocodingApi naverGeocodingApi;
    private final KakaoNavigationApi kakaoNavigationApi;
    private final MemberService memberService;

    public void add(HttpServletRequest request, TrafficInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        if (trafficRepository.existsByLazierUser(lazierUser)) {
            throw new UserAlreadyExistException("사용자 정보가 존재합니다.");
        }

        String startingGeoCode = naverGeocodingApi.getGeoCode(parameter.getStartingPoint());
        String destinationGeoCode = naverGeocodingApi.getGeoCode(parameter.getDestination());

        Traffic traffic = Traffic.builder()
            .lazierUser(lazierUser)
            .startingPoint(parameter.getStartingPoint())
            .startingGeoCode(startingGeoCode)
            .destination(parameter.getDestination())
            .destinationGeoCode(destinationGeoCode)
            .build();

        trafficRepository.save(traffic);
    }

    public TrafficDto getUserInfo(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        Traffic traffic = trafficRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        return TrafficDto.of(traffic);
    }

    public void update(HttpServletRequest request, TrafficInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        Traffic traffic = trafficRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        String startingGeoCode = naverGeocodingApi.getGeoCode(parameter.getStartingPoint());
        String destinationGeoCode = naverGeocodingApi.getGeoCode(parameter.getDestination());

        traffic.update(parameter.getStartingPoint(), parameter.getDestination(), startingGeoCode,
            destinationGeoCode);
    }

    public void delete(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        Traffic traffic = trafficRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        trafficRepository.delete(traffic);
    }

    public DurationDto getTrafficDuration(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        Traffic traffic = trafficRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        String duration = kakaoNavigationApi.getDuration(traffic.getStartingGeoCode(),
            traffic.getDestinationGeoCode());

        return DurationDto.builder()
            .duration(duration)
            .startingPoint(traffic.getStartingPoint())
            .destination(traffic.getDestination())
            .build();
    }
}
