package main.java.dao.jdbc;

import main.java.configuration.JDBCConfig;
import main.java.dao.Dao;
import main.java.exception.DaoException;
import main.java.model.PaymentSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentSystemJDBCDaoImpl implements Dao<PaymentSystem> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS payment_system(id SERIAL PRIMARY KEY, payment_system_name VARCHAR(50));
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS payment_system;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE payment_system;
            """;

    private static final String ADD_TO_TABLE_SQL = """
            INSERT INTO payment_system(payment_system_name)
            VALUES (?);
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM payment_system WHERE id=?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, payment_system_name
            FROM payment_system
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE payment_system
            SET payment_system_name = ?
            WHERE id = ?
            """;

    public PaymentSystemJDBCDaoImpl() {}



    @Override
    public void createTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("PaymentSystem table created");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable() {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("PaymentSystem table dropped");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void clearTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("PaymentSystem table cleared");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int add(PaymentSystem entity) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_TABLE_SQL)) {
            preparedStatement.setString(1, entity.getPaymentSystemName());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int deleteById(Long id) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<PaymentSystem> getAll() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PaymentSystem> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(new PaymentSystem(
                        resultSet.getLong("id"),
                        resultSet.getString("payment_system_name")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<PaymentSystem> getById(Long id) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new PaymentSystem(
                        resultSet.getLong("id"),
                        resultSet.getString("payment_system_name")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(PaymentSystem entity) {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getPaymentSystemName());
            preparedStatement.setLong(2, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
