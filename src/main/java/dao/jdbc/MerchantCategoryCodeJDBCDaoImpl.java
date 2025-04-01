package main.java.dao.jdbc;

import main.java.configuration.JDBCConfig;
import main.java.dao.Dao;
import main.java.exception.DaoException;
import main.java.model.MerchantCategoryCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class MerchantCategoryCodeJDBCDaoImpl implements Dao<MerchantCategoryCode> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS merchant_category_code(
        id SERIAL PRIMARY KEY,
        mcc VARCHAR(4),
        mcc_name VARCHAR(255)
    );
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS merchant_category_code;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE merchant_category_code;
            """;

    private static final String ADD_TO_TABLE_SQL = """
            INSERT INTO merchant_category_code(mcc, mcc_name)
            VALUES (?, ?);
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM merchant_category_code WHERE id=?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, mcc, mcc_name
            FROM merchant_category_code
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE merchant_category_code
            SET mcc = ?,
                mcc_name = ?
            WHERE id = ?
            """;

    public MerchantCategoryCodeJDBCDaoImpl() {}

    @Override
    public void createTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("MerchantCategoryCode table created");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable() {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("MerchantCategoryCode table dropped");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void clearTable() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {
            preparedStatement.execute();
            System.out.println("MerchantCategoryCode table cleared");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int add(MerchantCategoryCode entity) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_TABLE_SQL)) {
            preparedStatement.setString(1, entity.getMcc());
            preparedStatement.setString(2, entity.getMccName());

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
    public List<MerchantCategoryCode> getAll() {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MerchantCategoryCode> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(new MerchantCategoryCode(
                        resultSet.getLong("id"),
                        resultSet.getString("mcc"),
                        resultSet.getString("mcc_name")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<MerchantCategoryCode> getById(Long id) {
        try(Connection connection = JDBCConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new MerchantCategoryCode(
                        resultSet.getLong("id"),
                        resultSet.getString("mcc"),
                        resultSet.getString("mcc_name")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(MerchantCategoryCode entity) {
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getMcc());
            preparedStatement.setString(2, entity.getMccName());
            preparedStatement.setLong(3, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

