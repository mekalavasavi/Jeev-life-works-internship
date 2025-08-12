package com.taskmanager.task_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.taskmanager.repository")
@EntityScan(basePackages = "com.taskmanager.entity") 
@ComponentScan(basePackages = {
        "com.taskmanager",           // Your main package
        "com.taskmanager.controller",// Controllers
        "com.taskmanager.config",    // Config classes
        "com.taskmanager.service",   // Services
        "com.taskmanager.repository" // Repositories
})
public class TaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

}
