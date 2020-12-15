package com.todolist.todolist.persistent;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table(name = "USERS")
public class UserData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private Long userId;

	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Invalid Email")
	@Column(name = "EMAIL", unique = true)
	private String email;

	@NotBlank(message = "Password cannot be blank")
	@Column(name = "PASSWORD")
	private String password;

	@NotBlank(message = "Confirm Password cannot be blank")
	@Transient
	private String confirmPassword;

	@NotBlank(message = "Name cannot be blank")
	@Length(max = 30, message = "Name cannot be more than 30 character")
	@Column(name = "NAME")
	private String name;

	@OneToMany(mappedBy = "userData", cascade = CascadeType.ALL)
	private List<TodoListData> todoListData;
}
