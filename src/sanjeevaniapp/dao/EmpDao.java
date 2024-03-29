/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanjeevaniapp.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import sanjeevaniapp.dbutil.DBConnection;
import sanjeevaniapp.pojo.EmpPojo;

/**
 *
 * @author HP
 */
public class EmpDao {
     
    public static void updateName(String currName, String newName) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Update employees set emp_name=? where emp_name=?");
        ps.setString(1, newName);
        ps.setString(2, currName);
        ps.executeUpdate();
    }

    public static String getNextEmpId() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select max(emp_id) from employees");
        rs.next();
        String str = rs.getString(1);
        int empId = 101;
        if (str != null) {
            String id = str.substring(1);
            empId = Integer.parseInt(id);
            empId++;
        }
        String newId = "E" + empId;
        return newId;
    }

    public static boolean addEmp(EmpPojo emp) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Insert into employees values(?,?,?,?)");
        ps.setString(1, emp.getEmpId());
        ps.setString(2, emp.getEmpName());
        ps.setString(3, emp.getEmpDept());
        ps.setDouble(4, emp.getEmpSal());
        return ps.executeUpdate() == 1;
    }

    public static List<String> getAllemployeeId() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select emp_id from employees");
        List<String> empIdList = new ArrayList<>();
        while (rs.next()) {
            empIdList.add(rs.getString(1));
        }
        return empIdList;
    }

    public static EmpPojo getEmployeeDetails(String empId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from employees where emp_id=?");
        ps.setString(1, empId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        EmpPojo emp = new EmpPojo();
        emp.setEmpId(rs.getString(1));
        emp.setEmpName(rs.getString(2));
        emp.setEmpDept(rs.getString(3));
        emp.setEmpSal(rs.getDouble(4));
        return emp;

    }

    public static boolean updateEmployee(EmpPojo emp) throws SQLException {
        updateName(emp);
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Update employees set emp_name=?,emp_sal=? where emp_id=?");
        ps.setString(1, emp.getEmpName());
        ps.setDouble(2, emp.getEmpSal());
        ps.setString(3, emp.getEmpId());
        return ps.executeUpdate() == 1;
    }

    public static void updateName(EmpPojo emp) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select emp_name from employees where emp_id=?");
        ps.setString(1, emp.getEmpId());
        ResultSet rs = ps.executeQuery();
        rs.next();
        String currName = rs.getString(1);
        String newName = emp.getEmpName();
        UserDao.updateName(currName, newName);
        if (emp.getEmpDept().equalsIgnoreCase("Receptionist")) {
            ReceptionistDao.updateName(currName, newName);
        } else if (emp.getEmpDept().equalsIgnoreCase("Doctor")) {
            DoctorDao.updateName(currName, newName);
        }

    }

    public static List<EmpPojo> getAllEmployeeDetails() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from employees order by emp_id");
        List<EmpPojo> empList = new ArrayList<>();
        while (rs.next()) {
            EmpPojo emp = new EmpPojo();
            emp.setEmpId(rs.getString(1));
            emp.setEmpName(rs.getString(2));
            emp.setEmpDept(rs.getString(3));
            emp.setEmpSal(rs.getDouble(4));
            empList.add(emp);
        }
        return empList;
    }

    public static Map<String, String> getUnRegisteredDoctors() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select emp_id,emp_name from employees where emp_dept='Doctor' and emp_name not in (select user_name from users where user_type='Doctor')order by emp_id");
        Map<String, String> unRegDocList = new HashMap<>();
        while (rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            unRegDocList.put(id, name);
        }
        return unRegDocList;
    }

    //..............................................................................................
    public static boolean deleteEmployeeById(String EmpId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select Emp_name from employees where Emp_id=?");
        ps.setString(1, EmpId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String EmpName = rs.getString(1);
        UserDao.deleteUserByName(EmpName);
        ps = conn.prepareStatement("Delete from employees where Emp_id=?");
        ps.setString(1, EmpId);
        return ps.executeUpdate() == 1;
    }

    //............................................
    public static Map<String, String> getUnRegisteredReceptionists() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select emp_id, emp_name from employees where emp_dept='Receptionist' and emp_name not in (select user_name from users where user_type ='Receptionist') order by emp_id");
        Map<String, String> unRegReceptList = new HashMap<>();
        while (rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            unRegReceptList.put(id, name);
        }
        return unRegReceptList;
    }

}
