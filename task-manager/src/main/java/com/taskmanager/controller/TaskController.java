package com.taskmanager.controller;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;
    // Manager only: create task
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody Task task, Authentication authentication) {
        String email = authentication.getName();
        User creator = userRepository.findByEmail(email).orElse(null);
        if (creator == null) {
            return ResponseEntity.badRequest().body("Creator not found");
        }
        task.setCreator(creator);
        taskRepository.save(task);
        return ResponseEntity.ok(task);
    }
    // Admin and Manager can see all tasks; Employee only own tasks
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        String role = user.getRole();

        if ("ADMIN".equals(role) || "MANAGER".equals(role)) {
            return ResponseEntity.ok(taskRepository.findAll());
        } else {
            // Employee only sees own tasks
            List<Task> tasks = taskRepository.findByAssignee(user);
            return ResponseEntity.ok(tasks);
        }
    }
    // Employee can update their own task's status; Manager/Admin can update any task
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task updatedTask, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task task = optionalTask.get();
        String role = user.getRole();

        if ("EMPLOYEE".equals(role)) {
            // Employee can update only status and only if assigned to them
            if (!task.getAssignee().getId().equals(user.getId())) {
                return ResponseEntity.status(403).body("Not allowed to update this task");
            }
            task.setStatus(updatedTask.getStatus());
        } else {
            // Manager/Admin can update all fields
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setPriority(updatedTask.getPriority());
            task.setStatus(updatedTask.getStatus());
            task.setTags(updatedTask.getTags());
            // Can reassign task
            task.setAssignee(updatedTask.getAssignee());
        }

        taskRepository.save(task);
        return ResponseEntity.ok(task);
    }

    // Admin only: delete task
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.ok("Task deleted successfully");
    }
    @GetMapping("/filter")
    public List<Task> filterTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueBefore,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueAfter,
            Authentication authentication
    ) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        // Employees can only filter their own tasks
        if (role.equals("ROLE_EMPLOYEE")) {
            // Get employee's userId from authentication principal (adjust based on your UserDetails implementation)
            assigneeId = getCurrentUserId(authentication);
        }

        return taskService.filterTasks(status, priority, assigneeId, dueBefore, dueAfter);
    }

    private Long getCurrentUserId(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email).map(user -> user.getId()).orElse(null);
    }
}
