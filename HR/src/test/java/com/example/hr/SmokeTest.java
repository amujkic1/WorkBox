package com.example.hr;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hr.controller.ApplicationController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmokeTest {

    @Autowired
    private ApplicationController applicationController;

    @Test
    void contextLoads() throws Exception {
        assertThat(applicationController).isNotNull();
    }
}