package main.java.dao.jdbc;

import main.java.configuration.JDBCConfig;
import main.java.dao.Dao;
import main.java.exception.DaoException;
import main.java.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AccountJDBCDaoImpl implements Dao<Account> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS account(
        id SERIAL PRIMARY KEY,
        account_number VARCHAR(50),
        balance DECIMAL,
        currency_id BIGINT,
        issuing_bank_id BIGINT,
        FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (issuing_bank_id) REFERENCES issuing_bank(id) ON DELETE RESTRICT ON UPDATE CASCADE
    );
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS account;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE account;
            """;

    private static final String ADD_TO_TABLE_SQL = """
            INSERT INTO account(account_number, balance, currency_id, issuing_bank_id)
            VALUES (?, ?, ?, ?);
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM account WHERE id=?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, account_number, balance, currency_id, issuing_bank_id
            FROM account
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE account
            SET account_number = ?,
                balance = ?,
                currency_id = ?,
                issuing_bank_id = ?
            WHERE id = ?
            """;

    public AccountJDBCDaoImpl() {}

    @Override
    public void createTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("Account table created");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable() {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("Account table dropped");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void clearTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("Account table cleared");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int add(Account entity) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_TABLE_SQL)) {
            preparedStatement.setString(1, entity.getAccountNumber());
            preparedStatement.setDouble(2, entity.getBalance());
            preparedStatement.setLong(3, entity.getCurrencyId());
            preparedStatement.setLong(4, entity.getIssuingBankId());

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
    public List<Account> getAll() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Account> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("account_number"),
                        resultSet.getDouble("balance"),
                        resultSet.getLong("currency_id"),
                        resultSet.getLong("issuing_bank_id")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Account> getById(Long id) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("account_number"),
                        resultSet.getDouble("balance"),
                        resultSet.getLong("currency_id"),
                        resultSet.getLong("issuing_bank_id")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Account entity) {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getAccountNumber());
            preparedStatement.setDouble(2, entity.getBalance());
            preparedStatement.setLong(3, entity.getCurrencyId());
            preparedStatement.setLong(4, entity.getIssuingBankId());
            preparedStatement.setLong(5, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}



