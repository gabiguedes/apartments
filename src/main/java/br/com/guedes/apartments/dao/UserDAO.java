package br.com.guedes.apartments.dao;

import br.com.guedes.apartments.ConnectionFactory;
import br.com.guedes.apartments.models.dto.authorization.UserSecurityDetails;
import br.com.guedes.apartments.models.enums.Role;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public UserSecurityDetails selectUserDetailsForCPF(String cpf) {
        Connection conn = connectionFactory.getConnection();
        String sql = "SELECT cpf, password, role FROM users WHERE cpf = ?";
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

    private UserSecurityDetails mapUserForUserDetails(ResultSet rs) throws SQLException {
        UserSecurityDetails userSecurityDetails = new UserSecurityDetails();
        userSecurityDetails.setCpf(rs.getString("cpf"));
        userSecurityDetails.setPassword(rs.getString("password"));
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