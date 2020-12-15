package com.todolist.todolist.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todolist.todolist.Repository.TodoListRepository;
import com.todolist.todolist.Repository.UserRepository;
import com.todolist.todolist.exception.UsernameAlreadyExistException;
import com.todolist.todolist.persistent.TodoListData;
import com.todolist.todolist.persistent.UserData;

@Service
public class TodoService {

	@Autowired
	TodoListRepository todoListRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public UserData signup(UserData userData) {
		try {
			userData.setPassword(passwordEncoder.encode(userData.getPassword()));
			return userRepository.save(userData);
		} catch (DataIntegrityViolationException dive) {
			throw new UsernameAlreadyExistException("Email already exist in the system");
		} catch (Exception ex) {
			throw new DataRetrievalFailureException("Could not retreive data. Please try again after sometime.");
		}
	}

	public List<TodoListData> getTodoList(String email) {
		Optional<UserData> userData = this.userRepository.findByEmail(email);
		return userData.get().getTodoListData();
	}

	public TodoListData saveTodoTask(TodoListData todoListData, String email) {
		UserData userData = userRepository.findByEmail(email).get();
		todoListData.setUserData(userData);
		return this.todoListRepository.save(todoListData);
	}

	public void deleteTodoTask(int todoTaskId) {
		this.todoListRepository.deleteById(todoTaskId);
	}

}
