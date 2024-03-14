/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanjeevaniapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sanjeevaniapp.dbutil.DBConnection;
import sanjeevaniapp.pojo.ReceptionistPojo;
import sanjeevaniapp.pojo.User;
import sanjeevaniapp.pojo.UserPojo;

/**
 *
 * @author HP
 */
public class UserDao {

    public static String validateUser(User user) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select user_name from users where login_id=? and password=? and user_type=?");
        ps.setString(1, user.getLoginId());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getUserType());
        ResultSet rs = ps.executeQuery();
        String name = null;
        if (rs.next()) {
            name = rs.getString("user_name");
            //System.out.println(name);
        }
        return name;
    }

    public static void updateName(String currName, String newName) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Update users set user_name=? where user_name=?");
        ps.setString(1, newName);
        ps.setString(2, currName);
        ps.executeUpdate();
    }

    public static boolean addUser(UserPojo user) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Insert into users values(?,?,?,?)");
        ps.setString(1, user.getLoginId());
        ps.setString(2, user.getUserName());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getUserType());
        return ps.executeUpdate() == 1;
    }

    static void deleteUserByName(String docName) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("delete from users where user_name=?");
        ps.setString(1, docName);
        ps.executeUpdate();
    }

    public static UserPojo getUserDetailsByName(String userName) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from users where user_name=?");
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        rs.next();

        UserPojo user = new UserPojo();
        user.setLoginId(rs.getString(1));
        user.setUserName(rs.getString(2));
        user.setPassword(rs.getString(3));

        return user;
    }

    public static boolean updateUser(UserPojo user, ReceptionistPojo recept) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select recept_name from receptionists where recept_id=?");
        ps.setString(1, recept.getReceptionistId());
        ResultSet rs = ps.executeQuery();
        rs.next();
        String currName = rs.getString(1);
        ps = conn.prepareStatement("Update users set login_Id=?,user_name=?,Password=? where user_name=?");
        ps.setString(1, user.getLoginId());
        //System.out.println(user.getLoginId());
        ps.setString(2, user.getUserName());
        ps.setString(3, user.getPassword());
        ps.setString(4, currName);
        return ps.executeUpdate() == 1;
    }

}
