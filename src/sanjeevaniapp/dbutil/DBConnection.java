/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanjeevaniapp.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class DBConnection {

    private static Connection conn = null;

    static {
//        try {
//            Class.forName("oracle.jdbc.OracleDriver");
//            System.out.println("Driver Loaded Successfully");
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//            System.exit(0);
//        }
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//LAPTOP-QJ5A2E5K:1521/xe", "Sanjeevani", "sanjeevani");
            System.out.println("Connection Open Successfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("conn close successfully");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
