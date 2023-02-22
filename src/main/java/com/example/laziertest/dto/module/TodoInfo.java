package com.example.laziertest.dto.module;

import com.example.laziertest.persist.entity.todo.Todo;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoInfo {

	private String content;
	private String id;

	public static List<TodoInfo> of(List<Todo> list) {
		List<TodoInfo> todoInfoList = new ArrayList<>();
		for (Todo todo : list) {
			todoInfoList.add(TodoInfo.of(todo));
		}
		return todoInfoList;
	}

	public static TodoInfo of(Todo todo) {
		return TodoInfo.builder()
			.id(todo.getId().toString())
			.content(todo.getContent())
			.build();
	}
}
