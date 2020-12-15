package com.todolist.todolist.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.todolist.todolist.persistent.TodoListData;

@Repository
public interface TodoListRepository extends CrudRepository<TodoListData, Integer> {

}
