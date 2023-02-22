package com.example.laziertest.persist.entity.todo;

import com.example.laziertest.dto.module.TodoInfo;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private LazierUser lazierUser;

	//private Long userId;
	private String content;

	public static Todo of(LazierUser lazierUser, TodoInfo todoInfo) {
		return Todo.builder()
			.lazierUser(lazierUser)
			.content(todoInfo.getContent())
			.build();
	}

}
