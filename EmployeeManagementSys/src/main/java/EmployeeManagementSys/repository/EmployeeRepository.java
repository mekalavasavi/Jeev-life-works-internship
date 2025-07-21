package EmployeeManagementSys.repository;

import EmployeeManagementSys.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}

