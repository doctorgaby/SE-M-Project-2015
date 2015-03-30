package group8.com.application.Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public abstract class DBHandler {
    //The important information about the database to make the connection.
    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_USERNAME = "sql457184";
    private final static String DB_URL = "jdbc:mysql://sql4.freesqldatabase.com/" + DB_USERNAME;
    private final static String DB_PASSWORD = "kV1%xI3*";
    //The connection is used to do querys, updates and connections to the DB.
    private static Connection connection = null;

    //                  ***************************
    //                  **      DB Functions     **
    //                  ***************************

    /**
     * Initializes the connection to the Database.
     */
    public static void initializeDB() {
        try {
            Class.forName(JDBC_DRIVER);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Connect to a database
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Terminates the connection to the Database.
     */
    public static void terminateDB() {
        try {
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Commits a specific event stored in the connection.
     */
    private static void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Does the update of a specific SQL statement.
     *
     * @param stmt
     */
    protected static void update(PreparedStatement stmt) {
        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        commit();
    }

    /**
     * Does a query with the provided sql statement.
     *
     * @param stmt
     * @return the resultSet product of the query in the database.
     */
    protected static ResultSet query(PreparedStatement stmt) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        commit();
        return rs;
    }

    //                  *****************************
    //                  ** Session Query Functions **
    //                  *****************************

    /**
     * The method checks if a specific username and password match in a specific table.
     *
     * @param username
     * @param password
     * @return True or false depending if the username and password exists in the table.
     */
    public static boolean checkCredentials(String username, String password) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM Users WHERE Username= ? AND Password= ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet rs = query(stmt);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Adds a user to the DB.
     *
     * @param username
     * @param password
     */
    public static void addUser(String username, String password) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("INSERT INTO Users (?,?) VALUES");
            stmt.setString(1, username);
            stmt.setString(2, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        update(stmt);
    }

    /**
     * Method checks if a specific username is already in use in a specific table.
     *
     * @param username
     * @return true or false depending if a username exists in that table.
     */
    public static boolean checkUsername(String username) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM Users WHERE Username= ? ");
            stmt.setString(1, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet rs = query(stmt);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //                  *******************************
    //                  ** Additional functions here **
    //                  *******************************

}


