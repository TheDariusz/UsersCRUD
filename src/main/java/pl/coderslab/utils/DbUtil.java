package pl.coderslab.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class DbUtil {
    public static final String DB_SCHEMA = "workshop";
    private static final Logger logger = LoggerFactory.getLogger(DbUtil.class);
    private static DataSource dataSource;


    private DbUtil() {
        throw new IllegalStateException("Utility class!");
    }


    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }

    private static DataSource getInstance() {
        if (dataSource==null) {
            try {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                dataSource = (DataSource) envContext.lookup("jdbc/users");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }

    public static void insert(Connection conn, String query, String... params) {
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Problem with insert row query!", e);
        }
    }

    public static void printData(Connection conn, String query, String... columnNames) {

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            StringBuilder sb = new StringBuilder();
            sb.append("printData results:\n");
            while (resultSet.next()) {
                for (String param : columnNames) {
                    sb.append(resultSet.getString(param)).append(" | ");
                }
                sb.append("\n");
            }
            logger.info("{}", sb);
        } catch (Exception e) {
            logger.error("Problem with print data query!", e);
        }
    }

    private static final String DELETE_QUERY = "DELETE FROM tableName where id = ?";

    public static void remove(Connection conn, String tableName, int id) {
        try (PreparedStatement statement =
                     conn.prepareStatement(DELETE_QUERY.replace("tableName", tableName))) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            logger.error("Problem with delete row query!", e);
        }
    }

    public static void printAllData(Connection conn, String query) {
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            StringBuilder sb = new StringBuilder();
            sb.append("printAllData results:\n");
            while (rs.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    sb.append(rs.getString(i)).append(" | ");
                }
                sb.append("\n");
            }
            logger.info("{}", sb);
        } catch (Exception e) {
            logger.error("Problem with print all data query!", e);
        }
    }

    public static void printDataRS(ResultSet rs, int... colNumbers) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("printDataRS results:\n");
        while (rs.next()) {
            for (int column : colNumbers) {
                sb.append(rs.getString(column)).append(" | ");
            }
            sb.append("\n");
        }
        logger.info("{}", sb);
    }

    public static void execute(Connection conn, String query) {
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Problem with execute update statement!", e);
        }
    }

    public static void writeLogToDatabase(String browser, String currentTime, String messageType, String message) {
        String queryInsertLog =
                "INSERT INTO logs (user_browser, requestDate, message_type, message) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(queryInsertLog)) {
            stmt.setString(1, browser);
            stmt.setString(2, currentTime);
            stmt.setString(3, messageType);
            stmt.setString(4, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Problem with logger in WriteLogDatabase",e);
        }
    }
}
