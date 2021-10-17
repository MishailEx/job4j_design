package ru.job4j.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.StringJoiner;

public class TableEditor implements AutoCloseable {

    private Connection connection;

    private Properties properties;

    public TableEditor(Properties properties) throws SQLException, ClassNotFoundException {
        this.properties = properties;
        initConnection();
    }

    private void initConnection() throws SQLException, ClassNotFoundException {
        Class.forName(properties.getProperty("jdbc.driver"));
        connection = DriverManager.getConnection(
                properties.getProperty("hibernate.connection.url"),
                properties.getProperty("hibernate.connection.username"),
                properties.getProperty("hibernate.connection.password"));

    }

    public void createTable(String tableName) throws SQLException {
        String sql = String.format(
                "create table if not exists %s (%s);",
                tableName,
                "id serial primary key"
        );
        statement(sql);
    }

    public void dropTable(String tableName) throws SQLException {
        String sql = String.format(
                "drop table if exists %s;",
                tableName
        );
        statement(sql);
    }

    public void addColumn(String tableName, String columnName, String type) throws SQLException {
        String sql = String.format(
                "alter table %s add %s %s;",
                tableName, columnName, type
        );
        statement(sql);
    }

    public void dropColumn(String tableName, String columnName) throws SQLException {
        String sql = String.format(
                "alter table %s drop column %s;",
                tableName, columnName
        );
        statement(sql);
    }

    public void renameColumn(String tableName, String columnName, String newColumnName) throws SQLException {
        String sql = String.format(
                "alter table %s rename column %s TO %s;",
                tableName, columnName, newColumnName
        );
        statement(sql);
    }

    private void statement(String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }


    public static String getTableScheme(Connection connection, String tableName) throws Exception {
        var rowSeparator = "-".repeat(30).concat(System.lineSeparator());
        var header = String.format("%-15s|%-15s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "select * from %s limit 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format("%-15s|%-15s%n",
                        metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("app.properties"));
        TableEditor tableEditor = new TableEditor(properties);
        tableEditor.createTable("rrr");
        tableEditor.addColumn("rrr", "name", "text");
        tableEditor.renameColumn("rrr", "name", "year");
        tableEditor.dropColumn("rrr", "year");
        tableEditor.dropTable("rrr");
    }
}