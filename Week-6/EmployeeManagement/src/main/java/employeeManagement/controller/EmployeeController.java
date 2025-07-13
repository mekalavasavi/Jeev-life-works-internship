package employeeManagement.controller;

import employeeManagement.model.Employee;
import employeeManagement.security.JwtUtil;
import employeeManagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @Autowired
    private JwtUtil jwtUtil;

    private String getUsernameFromAuthHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                return jwtUtil.extractUsername(token);
            }
        }
        return "Unknown";
    }

    @GetMapping
    public List<Employee> getAllEmployees(@RequestHeader("Authorization") String authHeader) {
        String username = getUsernameFromAuthHeader(authHeader);
        log.info("User '{}' requested all employees", username);
        return service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        String username = getUsernameFromAuthHeader(authHeader);
        log.info("User '{}' requested employee with id {}", username, id);
        return service.getEmployeeById(id);
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@Valid @RequestBody Employee emp,
                                                 @RequestHeader("Authorization") String authHeader) {
        String username = getUsernameFromAuthHeader(authHeader);
        log.info("User '{}' is creating a new employee: {}", username, emp.getName());
        return ResponseEntity.ok(service.createEmployee(emp));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee emp,
                                                 @RequestHeader("Authorization") String authHeader) {
        String username = getUsernameFromAuthHeader(authHeader);
        log.info("User '{}' is updating employee with id {}: {}", username, id, emp.getName());
        return ResponseEntity.ok(service.updateEmployee(id, emp));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        String username = getUsernameFromAuthHeader(authHeader);
        log.info("User '{}' is deleting employee with id {}", username, id);
        return ResponseEntity.ok(service.deleteEmployee(id));
    }
    @GetMapping("/filter-by-salary")
    public List<Employee> filterBySalary(@RequestParam double minSalary,
                                         @RequestHeader("Authorization") String authHeader) {
        return service.filterBySalary(minSalary);
    }

    @GetMapping("/group-by-dept")
    public Map<String, List<Employee>> groupByDept(@RequestHeader("Authorization") String authHeader) {
        return service.groupByDepartment();
    }
}
