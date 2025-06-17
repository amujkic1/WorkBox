package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserController userController;

	@Autowired UserService userService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

/*
	@Test
	void contextLoads() {
		assertThat(userController).isNotNull();
		assertThat(userService).isNotNull();
	}

	@Test
	void givenLazyListBasedUser_WhenFetchingAllUsers_ThenIssueOneRequests() {
		userService.getAllUsers();
		assertSelectCount(1);
	}

	@Test
	void givenLazyListBasedUser_WhenFetchingAllUsers_ThenIssueOneRequest() {
		// Prebrojite broj upita pre izvršenja
		int initialQueryCount = getQueryCount();

		// Pozovite metodu koja vraća sve korisnike
		userService.getAllUsers();

		// Proverite da li je broj upita ostao na 1 (samo jedan upit za korisnike)
		int finalQueryCount = getQueryCount();
		assertThat(finalQueryCount - initialQueryCount).isEqualTo(1);
	}

	// Pomoćna metoda koja vraća broj izvršenih SQL upita
	private int getQueryCount() {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.QUERY_LOG WHERE QUERY LIKE 'SELECT%'", Integer.class);
	}

    */


}
