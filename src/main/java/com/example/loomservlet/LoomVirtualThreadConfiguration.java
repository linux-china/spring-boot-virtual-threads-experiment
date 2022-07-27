package com.example.loomservlet;

import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class LoomVirtualThreadConfiguration {
    @Bean
    AsyncTaskExecutor applicationTaskExecutor() {
        // enable async servlet support
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        return new TaskExecutorAdapter(executorService::execute);
    }

    @Bean
    TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> {
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }
}
