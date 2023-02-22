package com.example.laziertest.persist.repository;

import com.example.laziertest.persist.entity.module.Youtube;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YoutubeRepository extends JpaRepository<Youtube, String> {

  List<Youtube> findTop3ByOrderByCreatedAtDesc();

  List<Youtube> findAllByCreatedAtBefore(LocalDateTime dateTime);

}
