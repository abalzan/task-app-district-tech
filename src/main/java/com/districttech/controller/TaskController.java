package com.districttech.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.districttech.entity.Task;
import com.districttech.exception.TaskNotFoundException;
import com.districttech.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tasks")
public class TaskController {

	private TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Task> getAllTasks() throws TaskNotFoundException {

		List<Task> tasks = taskService.getAllTasks();

		if (tasks.isEmpty()) {
			throw new TaskNotFoundException("No Task was found!!!");
		}
		log.debug("There is "+ tasks.size()+ " Tasks founded!!");
		return tasks;
	}

	@GetMapping("/{taskId}")
	@ResponseStatus(HttpStatus.OK)
	public Task getTaskById(@PathVariable String taskId) throws NumberFormatException {
		Long id = Long.parseLong(taskId);
		log.debug("Request Task: {}", taskId);
		Task task = taskService.getTaskById(id);

		return task;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Task postTask(@RequestBody Task newTask) {
		log.debug("Request to create Task: {}", newTask.toString());
		return taskService.saveTask(newTask);
		
	}

	@DeleteMapping("/{taskId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteTask(@Valid @PathVariable Long taskId) {
		log.debug("Request to delete Task: {}", taskId);
		taskService.deleteTask(taskId);
	}

}
