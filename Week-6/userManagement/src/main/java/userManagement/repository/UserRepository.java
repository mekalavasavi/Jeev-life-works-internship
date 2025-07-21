package userManagement.repository;

import userManagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    //save a new user intodatabase
    public int save(User user) {
        String sql = "INSERT INTO users(username, password, email) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }
    // find a user by username
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            User user=jdbcTemplate.queryForObject(sql,new Object[] {username},new UserRowMapper());
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    // find a user by email
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
        	User user=jdbcTemplate.queryForObject(sql, new Object[] {email},new UserRowMapper());
        	return Optional.ofNullable(user);
        	
            
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    // find a user by username and password
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
        	User user = jdbcTemplate.queryForObject(sql, new Object[]{username, password}, new UserRowMapper());
            return Optional.ofNullable(user);
            
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    // coverts resultset row into a user object
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
            );
        }
    }
}

