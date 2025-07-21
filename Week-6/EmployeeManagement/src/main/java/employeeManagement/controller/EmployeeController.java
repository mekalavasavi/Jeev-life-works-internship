package employeeManagement.controller;

import employeeManagement.model.Employee;
import employeeManagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> getAllEmployees(@AuthenticationPrincipal String username) {
        log.info("User '{}' requested all employees", username);
        return service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id, @AuthenticationPrincipal String username) {
        log.info("User '{}' requested employee with id {}", username, id);
        return service.getEmployeeById(id);
    }

    @PostMapping
    public String createEmployee(@Valid @RequestBody Employee emp, @AuthenticationPrincipal String username) {
        log.info("User '{}' is creating a new employee: {}", username, emp.getName());
        return service.createEmployee(emp);
    }

    @PutMapping("/{id}")
    public String updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee emp,
                                 @AuthenticationPrincipal String username) {
        log.info("User '{}' is updating employee with id {}: {}", username, id, emp.getName());
        return service.updateEmployee(id, emp);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id, @AuthenticationPrincipal String username) {
        log.info("User '{}' is deleting employee with id {}", username, id);
        return service.deleteEmployee(id);
    }

    @GetMapping("/filter-by-salary")
    public List<Employee> filterBySalary(@RequestParam double minSalary, @AuthenticationPrincipal String username) {
        log.info("User '{}' filtering employees by salary > {}", username, minSalary);
        return service.filterBySalary(minSalary);
    }

    @GetMapping("/group-by-dept")
    public Map<String, List<Employee>> groupByDept(@AuthenticationPrincipal String username) {
        log.info("User '{}' requested employees grouped by department", username);
        return service.groupByDepartment();
    }
}
