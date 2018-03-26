package com.districttech.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Task {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=3)
	private String title;
	
	@Size(min=3)
	private String description;
	
	@FutureOrPresent
	private LocalDate dueDate;
	
	public Task(String title, String description, LocalDate dueDate) {
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
	}
}
