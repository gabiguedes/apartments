package br.com.guedes.apartments.dao;

import br.com.guedes.apartments.ConnectionFactory;
import br.com.guedes.apartments.models.dto.responses.UserFetcherDTO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminSupremeDAO {

    private final ConnectionFactory connectionFactory;

    public AdminSupremeDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public List<UserFetcherDTO> selectAllUsers() {
        Connection conn = connectionFactory.getConnection();
        List<UserFetcherDTO> usersList = new ArrayList<>();
        String sql = "SELECT id, cpf, password, name, role, creation FROM users";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserFetcherDTO user = mapUser(resultSet);
                usersList.add(user);
            }
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error when searching for users", e);
        }

        return usersList;
    }

    public UserFetcherDTO selectUserForName(String username) {
        Connection conn = connectionFactory.getConnection();
        String sql = "SELECT id, cpf, name, role, creation FROM users WHERE name = ?";
        UserFetcherDTO userResponse = null;

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

    public UserFetcherDTO selectUserForCpf(String cpf) {
        Connection conn = connectionFactory.getConnection();
        String sql = "SELECT id, cpf, name, role, creation FROM users WHERE cpf = ?";
        UserFetcherDTO userResponse = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, cpf);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userResponse = mapUser(resultSet);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error when searching for cpf user", e);
        }

        return userResponse;
    }

    private UserFetcherDTO mapUser(ResultSet rs) throws SQLException {
        return new UserFetcherDTO(
                rs.getLong("id"),
                rs.getString("cpf"),
                rs.getString("name"),
                rs.getString("role"),
                rs.getString("creation"));
    }

}