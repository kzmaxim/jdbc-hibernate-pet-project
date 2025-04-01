package main.java.dao.jdbc;

import main.java.configuration.JDBCConfig;
import main.java.dao.Dao;
import main.java.exception.DaoException;
import main.java.model.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CardJDBCDaoImpl implements Dao<Card> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS card(
        id SERIAL PRIMARY KEY,
        card_number VARCHAR(50),
        expiration_date DATE,
        holder_name VARCHAR(50),
        card_status_id BIGINT,
        payment_system_id BIGINT,
        account_id BIGINT,
        received_from_issuing_bank TIMESTAMP,
        sent_to_issuing_bank TIMESTAMP,
        FOREIGN KEY (card_status_id) REFERENCES card_status(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (payment_system_id) REFERENCES payment_system(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE RESTRICT ON UPDATE CASCADE
    );
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS card;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE card;
            """;

    private static final String ADD_TO_TABLE_SQL = """
            INSERT INTO card(card_number, expiration_date, holder_name, card_status_id, payment_system_id, account_id, received_from_issuing_bank, sent_to_issuing_bank)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM card WHERE id=?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, card_number, expiration_date, holder_name, card_status_id, payment_system_id, account_id, received_from_issuing_bank, sent_to_issuing_bank
            FROM card
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE card
            SET card_number = ?,
                expiration_date = ?,
                holder_name = ?,
                card_status_id = ?,
                payment_system_id = ?,
                account_id = ?,
                received_from_issuing_bank = ?,
                sent_to_issuing_bank = ?
            WHERE id = ?
            """;

    public CardJDBCDaoImpl() {}

    @Override
    public void createTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("Card table created");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable() {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("Card table dropped");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void clearTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("Card table cleared");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int add(Card entity) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_TABLE_SQL)) {
            preparedStatement.setString(1, entity.getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(entity.getExpirationDate()));
            preparedStatement.setString(3, entity.getHolderName());
            preparedStatement.setLong(4, entity.getCardStatusId());
            preparedStatement.setLong(5, entity.getPaymentSystemId());
            preparedStatement.setLong(6, entity.getAccountId());
            preparedStatement.setTimestamp(7, entity.getReceivedFromIssuingBank());
            preparedStatement.setTimestamp(8, entity.getSentToIssuingBank());

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
    public List<Card> getAll() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Card> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(new Card(
                        resultSet.getLong("id"),
                        resultSet.getString("card_number"),
                        resultSet.getDate("expiration_date").toLocalDate(),
                        resultSet.getString("holder_name"),
                        resultSet.getLong("card_status_id"),
                        resultSet.getLong("payment_system_id"),
                        resultSet.getLong("account_id"),
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
    public Optional<Card> getById(Long id) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Card(
                        resultSet.getLong("id"),
                        resultSet.getString("card_number"),
                        resultSet.getDate("expiration_date").toLocalDate(),
                        resultSet.getString("holder_name"),
                        resultSet.getLong("card_status_id"),
                        resultSet.getLong("payment_system_id"),
                        resultSet.getLong("account_id"),
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
    public void update(Card entity) {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(entity.getExpirationDate()));
            preparedStatement.setString(3, entity.getHolderName());
            preparedStatement.setLong(4, entity.getCardStatusId());
            preparedStatement.setLong(5, entity.getPaymentSystemId());
            preparedStatement.setLong(6, entity.getAccountId());
            preparedStatement.setTimestamp(7, entity.getReceivedFromIssuingBank());
            preparedStatement.setTimestamp(8, entity.getSentToIssuingBank());
            preparedStatement.setLong(9, entity.getCardId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}




