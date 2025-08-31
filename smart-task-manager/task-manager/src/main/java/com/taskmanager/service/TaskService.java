package com.taskmanager.service;

import com.taskmanager.entity.Task;
import com.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> filterTasks(String status, String priority, Long assigneeId, LocalDate dueBefore, LocalDate dueAfter) {
        Task.Status statusEnum = null;
        if (status != null && !status.isEmpty()) {
            try {
                statusEnum = Task.Status.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                // handle invalid status string
            }
        }

        Task.Priority priorityEnum = null;
        if (priority != null && !priority.isEmpty()) {
            try {
                priorityEnum = Task.Priority.valueOf(priority.toUpperCase());
            } catch (IllegalArgumentException e) {
                // handle invalid priority string
            }
        }

        return taskRepository.filterTasks(statusEnum, priorityEnum, assigneeId, dueBefore, dueAfter);
    }



}
