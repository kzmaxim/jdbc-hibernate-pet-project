package main.java.dao.jdbc;

import main.java.configuration.JDBCConfig;
import main.java.dao.Dao;
import main.java.exception.DaoException;
import main.java.model.ResponseCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class ResponseCodeJDBCDaoImpl implements Dao<ResponseCode> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS response_code(
        id SERIAL PRIMARY KEY,
        error_code VARCHAR(1),
        error_description VARCHAR(255),
        error_level VARCHAR(255)
    );
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS response_code;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE response_code;
            """;

    private static final String ADD_TO_TABLE_SQL = """
            INSERT INTO response_code(error_code, error_description, error_level)
            VALUES (?, ?, ?);
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM response_code WHERE id=?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, error_code, error_description, error_level
            FROM response_code
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE response_code
            SET error_code = ?,
                error_description = ?,
                error_level = ?
            WHERE id = ?
            """;

    public ResponseCodeJDBCDaoImpl() {}

    @Override
    public void createTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("ResponseCode table created");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable() {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("ResponseCode table dropped");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void clearTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("ResponseCode table cleared");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int add(ResponseCode entity) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_TABLE_SQL)) {
            preparedStatement.setString(1, entity.getErrorCode());
            preparedStatement.setString(2, entity.getErrorDescription());
            preparedStatement.setString(3, entity.getErrorLevel());

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
    public List<ResponseCode> getAll() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ResponseCode> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(new ResponseCode(
                        resultSet.getLong("id"),
                        resultSet.getString("error_code"),
                        resultSet.getString("error_description"),
                        resultSet.getString("error_level")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<ResponseCode> getById(Long id) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new ResponseCode(
                        resultSet.getLong("id"),
                        resultSet.getString("error_code"),
                        resultSet.getString("error_description"),
                        resultSet.getString("error_level")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(ResponseCode entity) {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getErrorCode());
            preparedStatement.setString(2, entity.getErrorDescription());
            preparedStatement.setString(3, entity.getErrorLevel());
            preparedStatement.setLong(4, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}



