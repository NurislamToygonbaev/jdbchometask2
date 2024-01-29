package configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConfig {
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/jdbchometask2",
                    "postgres",
                    "rootroot");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
