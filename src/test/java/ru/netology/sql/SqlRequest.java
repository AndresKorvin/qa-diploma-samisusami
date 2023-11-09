package ru.netology.sql;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlRequest {
    private static final String DB_URL = System.getProperty("datasource.url");

    private SqlRequest() {
    }

    @SneakyThrows
    public static Connection getConn() {
        return DriverManager.getConnection(DB_URL, "app", "pass");
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
    public static String getCreditPaymentStatus() {
        QueryRunner runner = new QueryRunner();
        String SqlStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (var connection = getConn()) {
            String result = runner.query(connection, SqlStatus, new ScalarHandler<>());
            return result;
        }
    }
}
