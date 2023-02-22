package com.example.laziertest.persist.repository;


import com.example.laziertest.persist.entity.module.Weather;
import com.example.laziertest.persist.entity.user.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Optional<Weather> findFirstByLazierUserOrderByUpdatedAt(LazierUser lazierUser);
}
