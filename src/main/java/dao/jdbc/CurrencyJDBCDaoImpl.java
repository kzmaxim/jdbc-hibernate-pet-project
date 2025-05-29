package dao.jdbc;

import main.java.configuration.JDBCConfig;
import dao.Dao;
import exception.DaoException;
import model.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyJDBCDaoImpl implements Dao<Currency> {
    private static final String CREATE_TABLE_SQL = """
        CREATE TABLE IF NOT EXISTS currency(
            id SERIAL PRIMARY KEY,
            currency_digital_code VARCHAR(3),
            currency_letter_code VARCHAR(3),
            currency_name VARCHAR(255)
            );
        """;

        private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS currency;
            """;

        private static final String CLEAR_TABLE_SQL = """
            TRUNCATE currency;
            """;

        private static final String ADD_TO_TABLE_SQL = """
            INSERT INTO currency(currency_digital_code, currency_letter_code, currency_name)
            VALUES (?, ?, ?);
            """;

        private static final String DELETE_BY_ID_SQL = """
            DELETE FROM currency WHERE id=?;
            """;

        private static final String FIND_ALL_SQL = """
            SELECT id, currency_digital_code, currency_letter_code, currency_name
            FROM currency
            """;

        private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

        private static final String UPDATE_SQL = """
            UPDATE currency
            SET currency_digital_code = ?, currency_letter_code = ?, currency_name = ?
            WHERE id = ?
            """;

        public CurrencyJDBCDaoImpl() {}

        @Override
        public void createTable() {
            try(Connection connection = JDBCConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
                preparedStatement.execute();
                System.out.println("Currency table created");
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }

        @Override
        public void dropTable() {
            try (Connection connection = JDBCConfig.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
                preparedStatement.execute();
                System.out.println("Currency table dropped");
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }

        @Override
        public void clearTable() {
            try(Connection connection = JDBCConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {
                preparedStatement.execute();
                System.out.println("Currency table cleared");
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }

        @Override
        public int add(Currency entity) {
            try(Connection connection = JDBCConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_TABLE_SQL)) {
                preparedStatement.setString(1, entity.getCurrencyDigitalCode());
                preparedStatement.setString(2, entity.getCurrencyLetterCode());
                preparedStatement.setString(3, entity.getCurrencyName());

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
        public List<Currency> getAll() {
            try(Connection connection = JDBCConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Currency> list = new ArrayList<>();
                while(resultSet.next()) {
                    list.add(new Currency(
                            //resultSet.getLong("id"),
                            resultSet.getString("currency_digital_code"),
                            resultSet.getString("currency_letter_code"),
                            resultSet.getString("currency_name")
                    ));
                }
                return list;
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }

        @Override
        public Optional<Currency> getById(Long id) {
            try(Connection connection = JDBCConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return Optional.of(new Currency(
                            //resultSet.getLong("id"),
                            resultSet.getString("currency_digital_code"),
                            resultSet.getString("currency_letter_code"),
                            resultSet.getString("currency_name")
                    ));
                }
                return Optional.empty();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }

        @Override
        public void update(Currency entity) {
            try (Connection connection = JDBCConfig.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
                preparedStatement.setString(1, entity.getCurrencyDigitalCode());
                preparedStatement.setString(2, entity.getCurrencyLetterCode());
                preparedStatement.setString(3, entity.getCurrencyName());
                preparedStatement.setLong(4, entity.getId());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }
