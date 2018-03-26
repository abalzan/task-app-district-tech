package com.districttech.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.districttech.entity.Task;
import com.districttech.exception.TaskNotFoundException;
import com.districttech.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService{
	
	private TaskRepository taskRepository;

	public TaskServiceImpl(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	@Override
	public Task getTaskById(@Valid Long taskid) {
		Optional<Task> task = taskRepository.findById(taskid);
		
		if(!task.isPresent()) {
			throw new TaskNotFoundException("Task with id "+ taskid+ " not found!!!");
		}
		
		return task.get();
			
	}

	@Override
	public Task saveTask(@Valid Task newTask) {
		return taskRepository.save(newTask);
	}
	
	@Override
	public void deleteTask(@Valid Long taskId) {
		taskRepository.deleteById(taskId);
	}

}
