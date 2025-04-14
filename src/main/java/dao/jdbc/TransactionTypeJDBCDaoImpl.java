package dao.jdbc;

import main.java.configuration.JDBCConfig;
import dao.Dao;
import exception.DaoException;
import model.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TransactionTypeJDBCDaoImpl implements Dao<TransactionType> {
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS transaction_type(
              id SERIAL PRIMARY KEY,
              transaction_type_name VARCHAR(255),
              operator VARCHAR(1)
          );
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS transaction_type;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE transaction_type;
            """;

    private static final String ADD_TO_TABLE_SQL = """
            INSERT INTO transaction_type(transaction_type_name, operator)
            VALUES (?, ?);
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM transaction_type WHERE id=?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, transaction_type_name, operator
            FROM transaction_type
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE transaction_type
            SET transaction_type_name = ?,
                operator = ?
            WHERE id = ?
            """;

    public TransactionTypeJDBCDaoImpl() {}

    @Override
    public void createTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("TransactionType table created");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable() {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("TransactionType table dropped");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void clearTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("TransactionType table cleared");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int add(TransactionType entity) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_TABLE_SQL)) {
            preparedStatement.setString(1, entity.getTransactionTypeName());
            preparedStatement.setString(2, entity.getOperator());

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
    public List<TransactionType> getAll() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TransactionType> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(new TransactionType(
                        resultSet.getLong("id"),
                        resultSet.getString("transaction_type_name"),
                        resultSet.getString("operator")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<TransactionType> getById(Long id) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new TransactionType(
                        resultSet.getLong("id"),
                        resultSet.getString("transaction_type_name"),
                        resultSet.getString("operator")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(TransactionType entity) {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getTransactionTypeName());
            preparedStatement.setString(2, entity.getOperator());
            preparedStatement.setLong(3, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

