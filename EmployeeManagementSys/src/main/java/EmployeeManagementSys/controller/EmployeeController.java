package EmployeeManagementSys.controller;

import EmployeeManagementSys.entity.Employee;
import EmployeeManagementSys.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeRepository repo;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Employee create(@RequestBody Employee e) {
        return repo.save(e);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public List<Employee> list() {
        return repo.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee e) {
        return repo.findById(id).map(emp -> {
            emp.setName(e.getName());
            emp.setDepartment(e.getDepartment());
            emp.setSalary(e.getSalary());
            repo.save(emp);
            return ResponseEntity.ok(emp);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) 
        	return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

