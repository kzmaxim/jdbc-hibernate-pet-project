package dao.jdbc;

import main.java.configuration.JDBCConfig;
import dao.Dao;
import exception.DaoException;
import model.SalesPoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class SalesPointJDBCDaoImpl implements Dao<SalesPoint> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS sales_point(
        id SERIAL PRIMARY KEY,
        pos_name VARCHAR(255),
        pos_address VARCHAR(255),
        pos_inn VARCHAR(12),
        acquiring_bank_id BIGINT,
        FOREIGN KEY (acquiring_bank_id) REFERENCES acquiring_bank(id) ON DELETE CASCADE ON UPDATE CASCADE
    );
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS sales_point;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE sales_point;
            """;

    private static final String ADD_TO_TABLE_SQL = """
            INSERT INTO sales_point(pos_name, pos_address, pos_inn, acquiring_bank_id)
            VALUES (?, ?, ?, ?);
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM sales_point WHERE id=?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, pos_name, pos_address, pos_inn, acquiring_bank_id
            FROM sales_point
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE sales_point
            SET pos_name = ?,
                pos_address = ?,
                pos_inn = ?,
                acquiring_bank_id = ?
            WHERE id = ?
            """;

    public SalesPointJDBCDaoImpl() {}

    @Override
    public void createTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("SalesPoint table created");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable() {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("SalesPoint table dropped");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void clearTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("SalesPoint table cleared");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int add(SalesPoint entity) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_TABLE_SQL)) {
            preparedStatement.setString(1, entity.getPosName());
            preparedStatement.setString(2, entity.getPosAddress());
            preparedStatement.setString(3, entity.getPosInn());
            preparedStatement.setLong(4, entity.getAcquiringBankId());

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
    public List<SalesPoint> getAll() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<SalesPoint> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(new SalesPoint(
                        resultSet.getLong("id"),
                        resultSet.getString("pos_name"),
                        resultSet.getString("pos_address"),
                        resultSet.getString("pos_inn"),
                        resultSet.getLong("acquiring_bank_id")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<SalesPoint> getById(Long id) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new SalesPoint(
                        resultSet.getLong("id"),
                        resultSet.getString("pos_name"),
                        resultSet.getString("pos_address"),
                        resultSet.getString("pos_inn"),
                        resultSet.getLong("acquiring_bank_id")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(SalesPoint entity) {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getPosName());
            preparedStatement.setString(2, entity.getPosAddress());
            preparedStatement.setString(3, entity.getPosInn());
            preparedStatement.setLong(4, entity.getAcquiringBankId());
            preparedStatement.setLong(5, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}


