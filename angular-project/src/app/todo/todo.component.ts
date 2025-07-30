import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

interface Task {
  title: string;
  description?: string;
  completed: boolean;
}

@Component({
  selector: 'app-todo',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css']
})
export class TodoComponent {
  taskForm: FormGroup;
  tasks: Task[] = [];

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']);
    }

    this.taskForm = this.fb.group({
      title: ['', Validators.required],
      description: ['']
    });

    this.loadTasks();
  }

  addTask() {
    if (this.taskForm.invalid) return;

    const task: Task = {
      title: this.taskForm.value.title,
      description: this.taskForm.value.description,
      completed: false
    };

    this.tasks.push(task);
    this.saveTasks();
    this.taskForm.reset();
  }

  toggleComplete(index: number) {
    this.tasks[index].completed = !this.tasks[index].completed;
    this.saveTasks();
  }

  deleteTask(index: number) {
    this.tasks.splice(index, 1);
    this.saveTasks();
  }

  saveTasks() {
    localStorage.setItem('tasks', JSON.stringify(this.tasks));
  }

  loadTasks() {
    const data = localStorage.getItem('tasks');
    if (data) {
      this.tasks = JSON.parse(data);
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  get pendingTasks() {
    return this.tasks.filter(task => !task.completed);
  }

  get completedTasks() {
    return this.tasks.filter(task => task.completed);
  }
}
