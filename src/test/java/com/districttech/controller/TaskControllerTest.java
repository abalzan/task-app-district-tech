package com.districttech.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.districttech.entity.Task;
import com.districttech.exception.CustomizedResponseEntityExceptionHandler;
import com.districttech.service.TaskService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@WebAppConfiguration
public class TaskControllerTest {

	private static final String TITLE = "Challenge";

	private static final String DESCRIPTION = "Challenge District Tech :)";

	@Mock
	private TaskService taskService;

	private TaskController controller;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		controller = new TaskController(taskService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(CustomizedResponseEntityExceptionHandler.class).build();
	}

	@Test
	public void testGetAllTasks() throws Exception {
		List<Task> tasks = new ArrayList<>();

		Task task = new Task("TestTitle", "testDescription", LocalDate.now().plusDays(2));
		task.setId(2L);

		tasks.add(createTask());
		tasks.add(task);

		when(taskService.getAllTasks()).thenReturn(tasks);

		mockMvc.perform(get("/tasks").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].title", equalTo(TITLE)))
				.andExpect(jsonPath("$[0].description", equalTo(DESCRIPTION)))
				.andExpect(jsonPath("$[1].title", equalTo("TestTitle")))
				.andExpect(jsonPath("$[1].description", equalTo("testDescription")));
	}
	
	@Test
	public void testGetTaskById() throws Exception {
		Task task = createTask();

		when(taskService.getTaskById(Mockito.anyLong())).thenReturn(task);

		mockMvc.perform(get("/tasks/1")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.title", equalTo(TITLE)))
				.andExpect(jsonPath("$.description", equalTo(DESCRIPTION)));
	}
	
	@Test
	public void testGetTaskByIdNumberFormatException() throws Exception {
		Task task = createTask();

		when(taskService.getTaskById(Mockito.anyLong())).thenReturn(task);

		mockMvc.perform(get("/tasks/hgdhd")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void testPostTask() throws Exception {
		
		Task task = new Task(TITLE, DESCRIPTION, LocalDate.now().plusDays(365));

		String jsonRequest = "{\"title\":\"Challenge\",\"description\":\"Challenge District Tech :)\",\"dueDate\":\"2019-03-27\"}";
		
		when(taskService.saveTask(Mockito.any(Task.class))).thenReturn(task);
		
		mockMvc.perform(post("/tasks")
		   			.contentType(MediaType.APPLICATION_JSON)
		   			.content(jsonRequest.getBytes()))
                .andExpect(status().isCreated())
    			.andExpect(jsonPath("$.title", equalTo(TITLE)))
    			.andExpect(jsonPath("$.description", equalTo(DESCRIPTION)));
	}

	@Test
	public void testDeleteTask() throws Exception {

		mockMvc.perform(delete("/tasks/1")).andExpect(status().isOk());

		verify(taskService, times(1)).deleteTask(Mockito.anyLong());

	}

	private Task createTask() {
		Task task = new Task(TITLE, DESCRIPTION, LocalDate.now().plusDays(1));
		task.setId(1L);

		return task;
	}
}
