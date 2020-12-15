package com.todolist.todolist.Controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.todolist.Service.TodoService;
import com.todolist.todolist.exception.BindingResultToMapValidator;
import com.todolist.todolist.exception.PasswordMismatchException;
import com.todolist.todolist.persistent.CustomUserDetails;
import com.todolist.todolist.persistent.TodoListData;
import com.todolist.todolist.persistent.UserData;
import com.todolist.todolist.security.AuthenticationRequest;
import com.todolist.todolist.security.AuthenticationResponse;
import com.todolist.todolist.security.JwtUtil;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class TodoListController {

	@Autowired
	TodoService todoService;

	@Autowired
	BindingResultToMapValidator bindingResultToMapValidator;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordMismatchException passwordMismatchException;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest authenticationRequest,
			BindingResult result) {
		Map<String, String> errors = bindingResultToMapValidator.validate(result);
		if (!errors.isEmpty()) {
			return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
		}

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getEmail(), authenticationRequest.getPassword()));

		final String token = jwtUtil.generateToken(((UserDetails) authentication.getPrincipal()).getUsername(),
				((CustomUserDetails) authentication.getPrincipal()).getName());
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody UserData userData, BindingResult result) {
		passwordMismatchException.validate(userData, result);
		Map<String, String> errors = bindingResultToMapValidator.validate(result);
		if (!errors.isEmpty()) {
			return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
		}

		todoService.signup(userData);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@GetMapping("/todoList/{email}")
	public Iterable<TodoListData> getTodoList(@PathVariable String email) {
		return this.todoService.getTodoList(email);
	}

	@PostMapping("/todoList/{email}")
	public TodoListData saveTodoTask(@RequestBody() TodoListData todoListData, @PathVariable String email) {
		return this.todoService.saveTodoTask(todoListData, email);
	}

	@PutMapping("/todoList/{email}")
	public TodoListData updateTodoTask(@RequestBody() TodoListData todoListData, @PathVariable String email) {
		return this.todoService.saveTodoTask(todoListData, email);
	}

	@DeleteMapping("/todoList/{taskId}")
	public void deleteTodoTask(@PathVariable("taskId") int taskId) {
		this.todoService.deleteTodoTask(taskId);
	}
}
