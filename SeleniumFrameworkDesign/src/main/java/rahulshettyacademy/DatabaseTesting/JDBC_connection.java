package rahulshettyacademy.DatabaseTesting;

import java.sql.*;

public class JDBC_connection
{
    public static void main(String[] args) throws SQLException {
        String host = "localhost";
        String port = "3306";
        String dbName = "Apps";

        //Following will make connection to the database
        Connection con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbName,"root","root");

        //Following will create statement with database.
        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("select * from credentials where scenario = 'zerobalanceaccount'");
        while(rs.next()) {
            rs.getString("username");
            rs.getString("password");
        }
    }
}
