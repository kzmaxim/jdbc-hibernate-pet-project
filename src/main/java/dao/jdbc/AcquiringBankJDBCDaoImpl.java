package main.java.dao.jdbc;

import main.java.configuration.JDBCConfig;
import main.java.dao.Dao;
import main.java.exception.DaoException;
import main.java.model.AcquiringBank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



class AcquiringBankJDBCDaoImpl implements Dao<AcquiringBank> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS acquiring_bank(
        id SERIAL PRIMARY KEY,
        bic VARCHAR(9),
        abbreviated_name VARCHAR(255)
    );
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS acquiring_bank;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE acquiring_bank;
            """;

    private static final String ADD_TO_TABLE_SQL = """
            INSERT INTO acquiring_bank(bic, abbreviated_name)
            VALUES (?, ?);
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM issuing_bank WHERE id=?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, bic, abbreviated_name
            FROM issuing_bank
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE issuing_bank
            SET bic = ?,
                abbreviated_name = ?
            WHERE id = ?
            """;

    @Override
    public void createTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("AcquiringBank table created");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable() {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("AcquiringBank table dropped");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void clearTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("AcquiringBank table cleared");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int add(AcquiringBank entity) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_TABLE_SQL)) {
            preparedStatement.setString(1, entity.getBic());
            preparedStatement.setString(2, entity.getAbbreviatedName());

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
    public List<AcquiringBank> getAll() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AcquiringBank> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(new AcquiringBank(
                        resultSet.getLong("id"),
                        resultSet.getString("bic"),
                        resultSet.getString("abbreviated_name")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<AcquiringBank> getById(Long id) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new AcquiringBank(
                        resultSet.getLong("id"),
                        resultSet.getString("bic"),
                        resultSet.getString("abbreviated_name")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(AcquiringBank entity) {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getBic());
            preparedStatement.setString(2, entity.getAbbreviatedName());
            preparedStatement.setLong(3, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

