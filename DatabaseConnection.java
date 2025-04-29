import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    private static final String DB_DIALECT = System.getenv("DB_DIALECT") != null ? System.getenv("DB_DIALECT") : "sqlite";
    private static final String DB_STORAGE = System.getenv("DB_STORAGE") != null ? System.getenv("DB_STORAGE") : "Database/identifier.sqlite";

    private static Connection connection;

    public static void main(String[] args) {
        connectToDatabase();
    }

    private static void connectToDatabase() {
        String url = "jdbc:" + DB_DIALECT + ":" + DB_STORAGE;
        //TODO: Fix this somehow bro :sob:
        try {
            connection = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
            System.out.println("\u001B[36mINFO\u001B[0m: Database connection has been established successfully.");
        } catch (SQLException e) {
            System.err.println("\u001B[91mERROR\u001B[0m: Unable to connect to the database: " + e.getMessage());
        }
    }
}