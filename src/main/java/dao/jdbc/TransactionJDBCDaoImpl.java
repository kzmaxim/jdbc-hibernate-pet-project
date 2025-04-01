package main.java.dao.jdbc;

import main.java.configuration.JDBCConfig;
import main.java.dao.Dao;
import main.java.exception.DaoException;
import main.java.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TransactionJDBCDaoImpl implements Dao<Transaction> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS transaction(
        id SERIAL PRIMARY KEY,
        transaction_date DATE,
        sum DECIMAL,
        transaction_name VARCHAR(255),
        account_id BIGINT,
        transaction_type_id BIGINT,
        card_id BIGINT,
        terminal_id BIGINT,
        response_code_id BIGINT,
        authorization_code VARCHAR(6),
        received_from_issuing_bank TIMESTAMP,
        sent_to_issuing_bank TIMESTAMP,
        FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (transaction_type_id) REFERENCES transaction_type(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (card_id) REFERENCES card(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (terminal_id) REFERENCES terminal(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (response_code_id) REFERENCES response_code(id) ON DELETE RESTRICT ON UPDATE CASCADE
    );
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS transaction;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE transaction;
            """;

    private static final String ADD_TO_TABLE_SQL = """
            INSERT INTO transaction(transaction_date, sum, transaction_name, account_id, transaction_type_id, card_id, terminal_id, response_code_id, authorization_code, received_from_issuing_bank, sent_to_issuing_bank)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM transaction WHERE id=?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, transaction_date, sum, transaction_name, account_id, transaction_type_id, card_id, terminal_id, response_code_id, authorization_code, received_from_issuing_bank, sent_to_issuing_bank
            FROM transaction
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE transaction
            SET transaction_date = ?,
                sum = ?,
                transaction_name = ?,
                account_id = ?,
                transaction_type_id = ?,
                card_id = ?,
                terminal_id = ?,
                response_code_id = ?,
                authorization_code = ?,
                received_from_issuing_bank = ?,
                sent_to_issuing_bank = ?
            WHERE id = ?
            """;

    public TransactionJDBCDaoImpl() {}

    @Override
    public void createTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("Transaction table created");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable() {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("Transaction table dropped");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void clearTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("Transaction table cleared");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int add(Transaction entity) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_TABLE_SQL)) {
            preparedStatement.setDate(1, Date.valueOf(entity.getTransactionDate()));
            preparedStatement.setDouble(2, entity.getSum());
            preparedStatement.setString(3, entity.getTransactionName());
            preparedStatement.setLong(4, entity.getAccountId());
            preparedStatement.setLong(5, entity.getTransactionTypeId());
            preparedStatement.setLong(6, entity.getCardId());
            preparedStatement.setLong(7, entity.getTerminalId());
            preparedStatement.setLong(8, entity.getResponseCodeId());
            preparedStatement.setString(9, entity.getAuthorizationCode());
            preparedStatement.setTimestamp(10, entity.getReceivedFromIssuingBank());
            preparedStatement.setTimestamp(11, entity.getSentToIssuingBank());

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
    public List<Transaction> getAll() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Transaction> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(new Transaction(
                        resultSet.getLong("id"),
                        resultSet.getDate("transaction_date").toLocalDate(),
                        resultSet.getDouble("sum"),
                        resultSet.getString("transaction_name"),
                        resultSet.getLong("account_id"),
                        resultSet.getLong("transaction_type_id"),
                        resultSet.getLong("card_id"),
                        resultSet.getLong("terminal_id"),
                        resultSet.getLong("response_code_id"),
                        resultSet.getString("authorization_code"),
                        resultSet.getTimestamp("received_from_issuing_bank"),
                        resultSet.getTimestamp("sent_to_issuing_bank")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Transaction> getById(Long id) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Transaction(
                        resultSet.getLong("id"),
                        resultSet.getDate("transaction_date").toLocalDate(),
                        resultSet.getDouble("sum"),
                        resultSet.getString("transaction_name"),
                        resultSet.getLong("account_id"),
                        resultSet.getLong("transaction_type_id"),
                        resultSet.getLong("card_id"),
                        resultSet.getLong("terminal_id"),
                        resultSet.getLong("response_code_id"),
                        resultSet.getString("authorization_code"),
                        resultSet.getTimestamp("received_from_issuing_bank"),
                        resultSet.getTimestamp("sent_to_issuing_bank")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Transaction entity) {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setDate(1, Date.valueOf(entity.getTransactionDate()));
            preparedStatement.setDouble(2, entity.getSum());
            preparedStatement.setString(3, entity.getTransactionName());
            preparedStatement.setLong(4, entity.getAccountId());
            preparedStatement.setLong(5, entity.getTransactionTypeId());
            preparedStatement.setLong(6, entity.getCardId());
            preparedStatement.setLong(7, entity.getTerminalId());
            preparedStatement.setLong(8, entity.getResponseCodeId());
            preparedStatement.setString(9, entity.getAuthorizationCode());
            preparedStatement.setTimestamp(10, entity.getReceivedFromIssuingBank());
            preparedStatement.setTimestamp(11, entity.getSentToIssuingBank());
            preparedStatement.setLong(12, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

