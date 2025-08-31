package com.taskmanager.repository;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Find all tasks assigned to a specific user (employee)
    List<Task> findByAssignee(User assignee);

    @Query("SELECT t FROM Task t WHERE "
            + "(:status IS NULL OR t.status = :status) AND "
            + "(:priority IS NULL OR t.priority = :priority) AND "
            + "(:assigneeId IS NULL OR t.assignee.id = :assigneeId) AND "
            + "(:dueBefore IS NULL OR t.dueDate <= :dueBefore) AND "
            + "(:dueAfter IS NULL OR t.dueDate >= :dueAfter)")
    List<Task> filterTasks(
        @Param("status") Task.Status status,
        @Param("priority") Task.Priority priority,
        @Param("assigneeId") Long assigneeId,
        @Param("dueBefore") LocalDate dueBefore,
        @Param("dueAfter") LocalDate dueAfter
    );


}


