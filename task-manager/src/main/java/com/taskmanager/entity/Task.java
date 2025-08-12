package com.taskmanager.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection
    private Set<String> tags;

    // Who created the task (Manager)
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    // Who is assigned this task (Employee)
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }


    public enum Status {
        TO_DO, IN_PROGRESS, BLOCKED, COMPLETED
    }

    public enum Priority {
        LOW, MEDIUM, HIGH
    }
}
