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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sanjeevaniapp.dbutil.DBConnection;
import sanjeevaniapp.pojo.ReceptionistPojo;

/**
 *
 * @author HP
 */
public class ReceptionistDao {

    public static void updateName(String currName, String newName) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Update receptionists set recept_name=? where recept_name=?");
        ps.setString(1, newName);
        ps.setString(2, currName);
        ps.executeUpdate();
    }

    public static String getNewRecId() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select max(recept_id) from receptionists");
        rs.next();
        int recId = 101;
        String id = rs.getString(1);
        if (id != null) {
            String num = id.substring(3);
            recId = Integer.parseInt(num) + 1;
        }
        return "REC" + recId;
    }

    public static boolean addReceptionist(ReceptionistPojo rec) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Insert into receptionists values(?,?,?)");
        ps.setString(1, rec.getReceptionistId());
        ps.setString(2, rec.getReceptionistName());
        ps.setString(3, rec.getGender());

        return ps.executeUpdate() == 1;
    }

    public static List<ReceptionistPojo> getAllReceptionistDetails() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from receptionists order by recept_id");
        List<ReceptionistPojo> recList = new ArrayList<>();
        while (rs.next()) {
            ReceptionistPojo recep = new ReceptionistPojo();
            recep.setReceptionistId(rs.getString(1));
            recep.setReceptionistName(rs.getString(2));
            recep.setGender(rs.getString(3));
            recList.add(recep);

        }
        return recList;
    }

    public static List<String> getAllReceptionistId() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select recept_id from receptionists");
        List<String> receptList = new ArrayList<>();
        while (rs.next()) {
            receptList.add(rs.getString(1));
        }
        return receptList;
    }

    public static boolean deleteReceptionistById(String receptId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select recept_name from receptionists where recept_id=?");
        ps.setString(1, receptId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String receptName = rs.getString(1);
        UserDao.deleteUserByName(receptName);
        ps = conn.prepareStatement("Delete from receptionists where recept_id=?");
        ps.setString(1, receptId);
        return ps.executeUpdate() == 1;
    }

    public static ReceptionistPojo getAllReceptionistDetailsById(String receptId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from receptionists where recept_id=?");
        ps.setString(1, receptId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        ReceptionistPojo rec = new ReceptionistPojo();
        rec.setReceptionistName(rs.getString(2));
        rec.setGender(rs.getString(3));
        return rec;
    }

    public static boolean updateReceptionist(ReceptionistPojo recept) throws SQLException {
        updateName(recept);
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Update receptionists set recept_name=?,gender=? where recept_id=?");
        ps.setString(1, recept.getReceptionistName());
        ps.setString(2, recept.getGender());
        ps.setString(3, recept.getReceptionistId());
        return ps.executeUpdate() == 1;
    }

    private static void updateName(ReceptionistPojo recept) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select recept_name from receptionists where recept_id=?");
        ps.setString(1, recept.getReceptionistId());
        ResultSet rs = ps.executeQuery();
        rs.next();
        String currName = rs.getString(1);
        String newName = recept.getReceptionistName();
        EmpDao.updateName(currName, newName);
    }

}
