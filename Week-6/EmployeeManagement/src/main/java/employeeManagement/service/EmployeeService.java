package employeeManagement.service;
import employeeManagement.exception.ResourceNotFoundException;
import employeeManagement.model.Employee;
import employeeManagement.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service layer for Employee operations
 */
@Slf4j
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public String createEmployee(Employee emp) {
        repository.save(emp);
        log.info("Employee created: {}", emp.getName());
        return "Employee created successfully";
    }

    public String updateEmployee(Long id, Employee emp) {
        Employee existing = getEmployeeById(id);
        emp.setId(existing.getId());
        repository.update(id, emp);
        log.info("Employee updated: {}", emp.getName());
        return "Employee updated successfully";
    }

    public String deleteEmployee(Long id) {
        getEmployeeById(id);  // check exists or throw
        repository.delete(id);
        log.info("Employee deleted with id: {}", id);
        return "Employee deleted successfully";
    }
    public List<Employee> filterBySalary(double minSalary) {
        List<Employee> all = getAllEmployees();
        return all.stream()
                .filter(emp -> emp.getSalary() > minSalary)
                .collect(Collectors.toList());
    }

    // ✅ Simple Java 8 Feature: Group employees by department
    public Map<String, List<Employee>> groupByDepartment() {
        List<Employee> all = getAllEmployees();
        return all.stream()
                .collect(Collectors.groupingBy(emp -> emp.getDepartment()));
    }
}

