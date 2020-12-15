package com.todolist.todolist.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todolist.todolist.Repository.UserRepository;
import com.todolist.todolist.persistent.CustomUserDetails;
import com.todolist.todolist.persistent.UserData;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserData> userData = userRepository.findByEmail(email);
		userData.orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
		return userData.map(CustomUserDetails::new).get();
	}

}
