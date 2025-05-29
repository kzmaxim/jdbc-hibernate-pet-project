package main.java.configuration;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class JDBCConfig {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String POOL_SIZE_KEY = "db.pool_size";
    private static final Integer DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> connectionPool;
    private static List<Connection> sourceConnections;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        initConnectionPool();
    }

    private static void initConnectionPool() {
        var poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
        var size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        connectionPool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            var connection = open();
            var proxyConnection = (Connection)
                    Proxy.newProxyInstance(JDBCConfig.class.getClassLoader(), new Class[]{Connection.class},
                            (proxy, method, args) -> method.getName().equals("close") ? connectionPool.add((Connection)proxy)
                                    : method.invoke(connection, args));
            connectionPool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    public static Connection getConnection() {
        try {
            return connectionPool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private JDBCConfig() {}

    private static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void closePool(Connection connection) {
        for (Connection sourceConnection : sourceConnections) {
            try {
                sourceConnection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}