package com.districttech.service;

import java.util.List;

import javax.validation.Valid;

import com.districttech.entity.Task;

public interface TaskService {

	List<Task> getAllTasks();

	Task getTaskById(@Valid Long taskid);

	void deleteTask(@Valid Long taskId);

	Task saveTask(@Valid Task newTask);

}
