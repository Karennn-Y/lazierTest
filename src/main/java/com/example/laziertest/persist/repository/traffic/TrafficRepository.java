package com.example.laziertest.persist.repository.traffic;

import com.example.laziertest.persist.entity.traffic.Traffic;
import com.example.laziertest.persist.entity.user.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrafficRepository extends JpaRepository<Traffic, String> {

    boolean existsByLazierUser(LazierUser lazierUser);

    Optional<Traffic> findByLazierUser(LazierUser lazierUser);
}
