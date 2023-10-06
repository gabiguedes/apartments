package br.com.guedes.apartments.dao;

import br.com.guedes.apartments.ConnectionFactory;
import br.com.guedes.apartments.models.UserRequest;
import br.com.guedes.apartments.models.dto.UserResponse;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {

    private final ConnectionFactory connectionFactory;

    public UserDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public void insert(UserRequest user) {
        Connection conn = connectionFactory.getConnection();

        String sql = "INSERT INTO users (cpf, name, password, role)" +
                     "VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getCpf());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole().name());

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("The insert query has something wrong", e);
        }
    }

    public List<UserResponse> getAllUsers() {
        Connection conn = connectionFactory.getConnection();
        List<UserResponse> usersList = new ArrayList<>();
        String sql = "SELECT id, cpf, password, name, role FROM users";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserResponse user = mapUser(resultSet);
                usersList.add(user);
            }
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error when searching for users", e);
        }

        return usersList;
    }

    public UserResponse findByName(String username) {
        Connection conn = connectionFactory.getConnection();
        String sql = "SELECT id, cpf, name, password, role FROM users WHERE name = ?";
        UserResponse userResponse = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userResponse = mapUser(resultSet);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error when searching for name user", e);
        }

        return userResponse;
    }

    private UserResponse mapUser(ResultSet rs) throws SQLException {
        UserResponse user = new UserResponse();
        user.setId(rs.getLong("id"));
        user.setCpf(rs.getString("cpf"));
        user.setName(rs.getString("name"));
        user.setRole(rs.getString("role"));
        return user;
    }

}
