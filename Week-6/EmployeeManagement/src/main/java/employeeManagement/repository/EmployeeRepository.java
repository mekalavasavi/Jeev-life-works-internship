package employeeManagement.repository;

import employeeManagement.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Employee CRUD operations using JDBC Template
 */
@Repository
public class EmployeeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try {
            Employee emp = jdbcTemplate.queryForObject(sql, new Object[]{id}, new EmployeeRowMapper());
            return Optional.ofNullable(emp);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public int save(Employee emp) {
        String sql = "INSERT INTO employees(name, department, position, salary) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, emp.getName(), emp.getDepartment(), emp.getPosition(), emp.getSalary());
    }

    public int update(Long id, Employee emp) {
        String sql = "UPDATE employees SET name=?, department=?, position=?, salary=? WHERE id=?";
        return jdbcTemplate.update(sql, emp.getName(), emp.getDepartment(), emp.getPosition(), emp.getSalary(), id);
    }

    public int delete(Long id) {
        String sql = "DELETE FROM employees WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }

    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Employee(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getString("position"),
                    rs.getDouble("salary")
            );
        }
    }
}

