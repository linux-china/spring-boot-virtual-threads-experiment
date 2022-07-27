package com.example.loomservlet;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class MyController {
    final JdbcTemplate jdbcTemplate;

    public MyController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/")
    String getValue() throws InterruptedException {
        // Simulate a blocking call for one second. The thread should be put aside for about a second.
        Thread.sleep(1000);
        return "OK";
    }

    @GetMapping("/where-am-i")
    String getThreadName() {
        return Thread.currentThread().toString();
    }

    @GetMapping("/where-am-i-async")
    Callable<String> getAsyncThreadName() {
        return () -> Thread.currentThread().toString();
    }

    @GetMapping("/sql")
    String getFromDatabase() {

        // Simulate blocking I/O where the server side controls the timeout. The thread should be put aside for about a second.
        return jdbcTemplate.queryForList("select pg_sleep(1);").toString();
    }
}
