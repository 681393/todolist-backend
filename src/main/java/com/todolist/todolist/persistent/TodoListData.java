package com.todolist.todolist.persistent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "TODO_LIST")
public class TodoListData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TASK_ID")
	private int todoListId;

	@NotBlank(message = "Task cannot be blank")
	@Length(min = 1, max = 250, message = "Task cannot be more than 250 characters")
	@Column(name = "TASK")
	private String task;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "UPDATED_TS")
	private Date updatedTs;

	@ManyToOne()
	@JoinColumn(name = "USER_ID")
	@JsonIgnore
	private UserData userData;

	@PrePersist
	public void prePersist() {
		this.updatedTs = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedTs = new Date();
	}
}
