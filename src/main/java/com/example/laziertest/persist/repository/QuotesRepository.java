package com.example.laziertest.persist.repository;

import com.example.laziertest.persist.entity.module.Quotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotesRepository extends JpaRepository<Quotes, Long> {

}
