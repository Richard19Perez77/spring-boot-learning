package com.rick.webservice.rick;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RickApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void helloEndpointReturnsGreeting() throws Exception {
		mockMvc.perform(get("/api/hello"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello, Spring Boot!"));
	}

	@Test
	void financeEndpointReturnsGreeting() throws Exception {
		mockMvc.perform(get("/api/finance/hellofinance"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello, Finance!"));
	}

	@Test
	void ecommEndpointReturnsGreeting() throws Exception {
		mockMvc.perform(get("/api/ecomm"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello, EComm!"));
	}
}
