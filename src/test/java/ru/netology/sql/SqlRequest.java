package ru.netology.sql;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class SqlRequest {
//    private static final String DB_URL = System.getProperty("datasource.url");
//    private static final String name = System.getProperty("datasource.username");
//    private static final String password = System.getProperty("datasource.password");


    private SqlRequest() {
    }

    @SneakyThrows
    public static Connection getConn() {
        Properties prop = new Properties();
        prop.load(new FileInputStream("application.properties"));
        String url = prop.getProperty("spring.datasource.url");
        String username = prop.getProperty("spring.datasource.username");
        String password = prop.getProperty("spring.datasource.password");
        return DriverManager.getConnection(url, username, password);
    }

    @SneakyThrows
    public static void clearDataBase() {
        QueryRunner runner = new QueryRunner();
        try (var connection = getConn()) {
            runner.execute(connection, "DELETE FROM credit_request_entity");
            runner.execute(connection, "DELETE FROM order_entity");
            runner.execute(connection, "DELETE FROM payment_entity");
        }
    }

    @SneakyThrows
    public static String getDebitPurchaseStatus() {
        QueryRunner runner = new QueryRunner();
        String SqlStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var connection = getConn()) {
            String result = runner.query(connection, SqlStatus, new ScalarHandler<>());
            return result;
        }
    }

    @SneakyThrows
    public static String getCreditPurchaseStatus() {
        QueryRunner runner = new QueryRunner();
        String SqlStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (var connection = getConn()) {
            String result = runner.query(connection, SqlStatus, new ScalarHandler<>());
            return result;
        }
    }
}
