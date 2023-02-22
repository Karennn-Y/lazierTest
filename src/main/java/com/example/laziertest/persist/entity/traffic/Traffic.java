package com.example.laziertest.persist.entity.traffic;

import com.example.laziertest.persist.entity.user.LazierUser;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Traffic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LazierUser lazierUser;

    private String startingPoint; // 출발지
    private String startingGeoCode; // 위경도 정보
    private String destination; // 도착지
    private String destinationGeoCode; // 위경도 정보

    public void update(String startingPoint, String destination, String startingGeoCode,
        String destinationGeoCode) {
        this.startingPoint = startingPoint;
        this.startingGeoCode = startingGeoCode;
        this.destination = destination;
        this.destinationGeoCode = destinationGeoCode;
    }
}