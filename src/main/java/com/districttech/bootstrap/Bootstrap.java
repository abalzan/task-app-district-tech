package com.districttech.bootstrap;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.districttech.entity.Task;
import com.districttech.repository.TaskRepository;

@Component
public class Bootstrap implements CommandLineRunner{

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public void run(String... args) throws Exception {
		if(taskRepository.findAll().isEmpty()) {
			taskRepository.save(new Task("Challenge", "Challenge District Tech :)", LocalDate.now().plusDays(1)));
			taskRepository.save(new Task("Create Task", "Create	Task District Tech Tes", LocalDate.now().plusDays(1)));
			taskRepository.save(new Task("Retrieve Task", "Retrieve Task District Tech Tes", LocalDate.now()));
			taskRepository.save(new Task("Update Task", "Update Task District Tech Test", LocalDate.now()));
			taskRepository.save(new Task("Delete Task", "Delete Task District Tech Test", LocalDate.now()));
		}
	}

}
