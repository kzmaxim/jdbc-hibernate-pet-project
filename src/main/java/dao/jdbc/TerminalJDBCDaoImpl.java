package main.java.dao.jdbc;

import main.java.configuration.JDBCConfig;
import main.java.dao.Dao;
import main.java.exception.DaoException;
import main.java.model.Terminal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


class TerminalJDBCDaoImpl implements Dao<Terminal> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS terminal(
        id SERIAL PRIMARY KEY,
        terminal_id VARCHAR(9),
        mcc_id BIGINT NOT NULL,
        pos_id BIGINT NOT NULL,
        FOREIGN KEY (mcc_id) REFERENCES merchant_category_code(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (pos_id) REFERENCES sales_point (id) ON DELETE RESTRICT ON UPDATE CASCADE
    );
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS terminal;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE terminal;
            """;

    private static final String ADD_TO_TABLE_SQL = """
            INSERT INTO terminal(terminal_id, mcc_id, pos_id)
            VALUES (?, ?, ?);
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM terminal WHERE id=?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, terminal_id, mcc_id, pos_id
            FROM terminal
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE terminal
            SET terminal_id = ?,
                mcc_id = ?,
                pos_id = ?
            WHERE id = ?
            """;

    @Override
    public void createTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("Terminal table created");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable() {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("Terminal table dropped");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void clearTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("Terminal table cleared");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int add(Terminal entity) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_TABLE_SQL)) {
            preparedStatement.setString(1, entity.getTerminalId());
            preparedStatement.setLong(2, entity.getMccId());
            preparedStatement.setLong(3, entity.getPosId());

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
    public List<Terminal> getAll() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Terminal> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(new Terminal(
                        resultSet.getLong("id"),
                        resultSet.getString("terminal_id"),
                        resultSet.getLong("mcc_id"),
                        resultSet.getLong("pos_id")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Terminal> getById(Long id) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Terminal(
                        resultSet.getLong("id"),
                        resultSet.getString("terminal_id"),
                        resultSet.getLong("mcc_id"),
                        resultSet.getLong("pos_id")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Terminal entity) {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getTerminalId());
            preparedStatement.setLong(2, entity.getMccId());
            preparedStatement.setLong(3, entity.getPosId());
            preparedStatement.setLong(4, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}


