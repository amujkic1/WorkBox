package com.example.systemeventsserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "grpc.enabled=false")
class SystemEventsServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
