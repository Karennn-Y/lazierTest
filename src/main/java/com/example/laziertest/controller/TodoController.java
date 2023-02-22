package com.example.laziertest.controller;

import com.example.laziertest.dto.module.TodoInfo;
import com.example.laziertest.service.Impl.TodoService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/todolist")
@RequiredArgsConstructor
public class TodoController {

	private final TodoService todoService;

	@PostMapping("/write")
	public ResponseEntity<?> write(HttpServletRequest request,
		@RequestBody TodoInfo todoInfo) {

		todoService.write(request, todoInfo);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody TodoInfo todoInfo) {

		todoService.delete(todoInfo);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody TodoInfo todoInfo) {

		todoService.update(todoInfo);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/search")
	public ResponseEntity<?> search(HttpServletRequest request) {
		return new ResponseEntity<>(todoService.search(request), HttpStatus.OK);
	}

}
