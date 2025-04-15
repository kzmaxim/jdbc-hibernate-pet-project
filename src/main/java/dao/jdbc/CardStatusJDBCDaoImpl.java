package dao.jdbc;

import main.java.configuration.JDBCConfig;
import dao.Dao;
import exception.DaoException;
import model.CardStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class CardStatusJDBCDaoImpl implements Dao<CardStatus> {
        private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS card_status(id SERIAL PRIMARY KEY, card_status_name VARCHAR(255));
    """;

        private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS card_status CASCADE;
            """;

        private static final String CLEAR_TABLE_SQL = """
            TRUNCATE card_status;
            """;

        private static final String ADD_TO_TABLE_SQL = """
            INSERT INTO card_status(card_status_name)
            VALUES (?);
            """;

        private static final String DELETE_BY_ID_SQL = """
            DELETE FROM card_status WHERE id=?;
            """;

        private static final String FIND_ALL_SQL = """
            SELECT id, card_status_name
            FROM card_status
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE card_status
            SET card_status_name = ?
            WHERE id = ?
            """;

    public CardStatusJDBCDaoImpl() {}

    @Override
    public void createTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("CardStatus table created");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable() {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("CardStatus table dropped");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void clearTable() {
        try(Connection connection = JDBCConfig.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("CardStatus table cleared");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int add(CardStatus entity) {
        try(Connection connection = JDBCConfig.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_TABLE_SQL)) {
            preparedStatement.setString(1, entity.getCardStatusName());
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
    public List<CardStatus> getAll() {
        try(Connection connection = JDBCConfig.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CardStatus> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(new CardStatus(
                        //resultSet.getLong("id"),
                        resultSet.getString("card_status_name")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<CardStatus> getById(Long id) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new CardStatus(
                        //resultSet.getLong("id"),
                        resultSet.getString("card_status_name")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(CardStatus entity) {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getCardStatusName());
            preparedStatement.setLong(2, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
