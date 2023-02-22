package com.example.laziertest.persist.repository;


import com.example.laziertest.persist.entity.module.UserWeather;
import com.example.laziertest.persist.entity.user.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserWeatherRepository extends JpaRepository<UserWeather, String> {

    Optional<UserWeather> findByLazierUser(LazierUser lazierUser);

    boolean existsByLazierUser(LazierUser lazierUser);
}
