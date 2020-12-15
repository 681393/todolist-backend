package com.todolist.todolist.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.todolist.todolist.persistent.UserData;

@Repository
public interface UserRepository extends CrudRepository<UserData, Long> {

	Optional<UserData> findByEmail(String email);

}
