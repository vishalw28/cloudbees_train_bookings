package com.cloudbees;

import com.cloudbees.model.BookingRequest;
import com.cloudbees.repository.CoachRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebAppConfiguration()
//@ContextConfiguration(classes = {TestWebConfig.class, TestBackEndConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Execution(ExecutionMode.CONCURRENT)
class DemoApplicationTests {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private WebApplicationContext wac;


	private MockMvc mockMvc;

//	@BeforeEach
//	void setup() {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//	}

	@BeforeEach
	public void setup(){
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Autowired
	CoachRepository coachRepository;

//	@Autowired
//	private MockMvc mockMvc;
	@Test
	void contextLoads() {
	}


	@Test
	void shouldCreateProduct() throws Exception {
		String payload = objectMapper.writeValueAsString(bookingRequest());
		mockMvc.perform(MockMvcRequestBuilders.post("/booking")
			.contentType(MediaType.APPLICATION_JSON)
			.content(payload)
		).andExpect(status().isCreated());
		System.out.println(coachRepository.findAll());
		//Assert.assertTrue(productRepository.findAll().size() == 1);

	}

	BookingRequest bookingRequest(){
		return new BookingRequest(1, 10112,"SBC", "PNE",
			LocalDateTime.now(),"AC");
	}

}
