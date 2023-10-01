package br.com.guedes.apartments.dao;

import br.com.guedes.apartments.ConnectionFactory;
import br.com.guedes.apartments.models.UserRequest;
import br.com.guedes.apartments.models.enums.Role;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class UserDAO {

    private final ConnectionFactory connectionFactory;

    public UserDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public void insert(UserRequest user) {
        Connection conn = connectionFactory.getConnection();

        String sql = "INSERT INTO usuarios (cpf, name, password, role)" +
                     "VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getCpf());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, Role.USER_NOOB.getCode());

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("The insert query has something wrong", e);
        }
    }

}
