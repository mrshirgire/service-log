package com.creditsuisse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.creditsuisse.controller.EventController;
import com.creditsuisse.model.Event;
import com.creditsuisse.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@TestMethodOrder(OrderAnnotation.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
class ServiceLogApplicationTests {

	@Autowired
	private WebApplicationContext context;

	@InjectMocks
	EventController eventController;

	@Mock
	EventService eventService;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}

   @Test
   @Order(1)
   public void testPublishEventApi() throws Exception {

	Event event = new Event();
	event.setLogType("testcase-1");

	ObjectMapper mapper = new ObjectMapper();
	String jsonBody = mapper.writeValueAsString(event);

	this.mockMvc.perform(post("/api/event/publish").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andDo(print());

   }

   @Test
   @Order(2)
   public void testPublishEventByMultipleThreads() throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

	   List<Callable<Event>> taskList = new ArrayList<>();
	   for (int i = 0; i < 100; i++) {

		   Callable<Event> task =  () -> {

			   Event event = new Event();
			   event.setLogType("testcase-2");

			   MockHttpServletRequest request = new MockHttpServletRequest();
			   request.addParameter("remoteHost", "192.168.31.25");

			   executorService.submit( () -> eventService.publishEvent(event,request));

			   return event;
		   };

		   taskList.add(task);
	   }

	   executorService.invokeAll(taskList);
	   executorService.shutdown();
   }

	@Test
	@Order(3)
	public void testPublishEventApiByMultipleThreads() throws Exception  {
		ExecutorService executorService = Executors.newFixedThreadPool(5);

		List<Callable<Event>> taskList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {

			Callable<Event> task =  () -> {
					Event event = new Event();
					event.setLogType("testcase-3");

					ObjectMapper mapper = new ObjectMapper();
					String jsonBody = mapper.writeValueAsString(event);

					this.mockMvc.perform(post("/api/event/publish").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
							.andExpect(status().isCreated())
							.andDo(print());

				return event;
			};

			taskList.add(task);
		}

		executorService.invokeAll(taskList);
		executorService.shutdown();
	}




}
