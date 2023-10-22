package br.com.guedes.apartments.dao;

import br.com.guedes.apartments.ConnectionFactory;
import br.com.guedes.apartments.models.dto.responses.UserFetcherDTO;
import br.com.guedes.apartments.models.dto.authorization.UserSecurityDetails;
import br.com.guedes.apartments.models.enums.Role;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class UserDAO {

    private final ConnectionFactory connectionFactory;

    public UserDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public void insertAuthenticationRegisterUser(UserSecurityDetails user) {
        Connection conn = connectionFactory.getConnection();

        String sql = "INSERT INTO users (cpf, name, password, role, creation)" +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getCpf());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole().name());
            preparedStatement.setString(5, dateFormatForDataBase(new Date()));

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("The insert query has something wrong", e);
        }
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

    //TODO refatorar, fazer outro metodo de fetch para usar na buca de passowrd and cpf para jwt token
    public UserSecurityDetails selectUserForCPF(String cpf) {
        Connection conn = connectionFactory.getConnection();
        String sql = "SELECT id, cpf, password, role, name, creation FROM users WHERE cpf = ?";
        UserSecurityDetails userResponse = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, cpf);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userResponse = mapUserForUserDetails(resultSet);
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

    private UserSecurityDetails mapUserForUserDetails(ResultSet rs) throws SQLException {
        UserSecurityDetails userSecurityDetails = new UserSecurityDetails();
        userSecurityDetails.setId(rs.getLong("id"));
        userSecurityDetails.setCpf(rs.getString("cpf"));
        userSecurityDetails.setPassword(rs.getString("password"));
        userSecurityDetails.setName(rs.getString("name"));
        userSecurityDetails.setCreationOnDate(rs.getString("creation"));
        String roleString = rs.getString("role");
        Role role = convertStringToRole(roleString);
        userSecurityDetails.setRole(role);

        return userSecurityDetails;
    }

    private String dateFormatForDataBase(Date creation) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return dateFormat.format(creation.getTime());
    }

    private Role convertStringToRole(String roleString) {
        if (roleString != null) {
            if (roleString.equals("ADMIN_SUPREME")) {
                return Role.ADMIN_SUPREME;
            } else if (roleString.equals("USER_NOOB")) {
                return Role.USER_NOOB;
            }
        }
        return Role.USER_NOOB;
    }

}