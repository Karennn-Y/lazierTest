package com.example.laziertest.persist.repository;

import com.example.laziertest.persist.entity.todo.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<Todo, Long> {

	@Query("select count(*) from Todo td where td.lazierUser.userId = :userId")
	Long countByUserId(@Param("userId") Long userId);

	@Query("select td from Todo td where td.lazierUser.userId = :userId")
	List<Todo> findAllByUserId(@Param("userId") Long userId);

}
