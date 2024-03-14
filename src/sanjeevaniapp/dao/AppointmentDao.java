package sanjeevaniapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sanjeevaniapp.dbutil.DBConnection;
import sanjeevaniapp.pojo.AppointmentPojo;
import sanjeevaniapp.pojo.PatientPojo;

public class AppointmentDao {

    public static boolean addAppointment(AppointmentPojo appt) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Insert into appointments values(?,?,?,?,?,?,?)");
        ps.setString(1, appt.getPatientId());
        ps.setString(2, appt.getPatientName());
        ps.setString(3, appt.getStatus());
        ps.setString(4, appt.getOpd());
        ps.setString(5, appt.getAppointmentDate());
        ps.setString(6, appt.getDoctorName());
        ps.setString(7, appt.getMobileNo());

        return ps.executeUpdate() == 1;
    }

    public static List<AppointmentPojo> getAllAppointmentsByDoctorName(String doctorName) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from appointments where doctor_name=? and status='REQUEST' order by patient_id");
        ps.setString(1, doctorName);
        ResultSet rs = ps.executeQuery();
        List<AppointmentPojo> appList = new ArrayList<>();
        while (rs.next()) {
            AppointmentPojo app = new AppointmentPojo();
            app.setPatientId(rs.getString("patient_id"));
            app.setPatientName(rs.getString("patient_name"));
            app.setOpd(rs.getString("opd"));
            app.setAppointmentDate(rs.getString("date_time"));
            app.setStatus(rs.getString("status"));
            app.setMobileNo(rs.getString("mobile_no"));
            app.setDoctorName(rs.getString("doctor_name"));
            appList.add(app);

        }
        return appList;
    }

    public static boolean updateStatus(AppointmentPojo app) throws SQLException {
        PatientDao.updateStatus(app);
        Connection conn = DBConnection.getConnection();
        //.............
        PreparedStatement ps = conn.prepareStatement("Update appointments set status=?,date_time=? where patient_id=?");
        ps.setString(1, app.getStatus());
        ps.setString(2, app.getAppointmentDate());
        ps.setString(3, app.getPatientId());
        return ps.executeUpdate() == 1;

    }

    //.............................................................
    public static boolean updateAppointment(PatientPojo patient) throws SQLException {
        String DocName = DoctorDao.getDoctorNameById(patient.getDoctorId());
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Update Appointments set patient_name=?,OPD=?,Doctor_Name=?,Mobile_No=? where patient_Id=?");
        ps.setString(1, patient.getFirstName() + " " + patient.getLastName());
        ps.setString(2, patient.getOpd());
        ps.setString(3, DocName);
        ps.setString(4, patient.getMno());
        ps.setString(5, patient.getPatientId());
        return ps.executeUpdate() == 1;
    }

    public static List<AppointmentPojo> getAllConfirmAppointments(String doctorName) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from appointments where doctor_name=? and status='CONFIRM' order by patient_id");
        ps.setString(1, doctorName);
        ResultSet rs = ps.executeQuery();
        List<AppointmentPojo> appList = new ArrayList<>();
        while (rs.next()) {
            AppointmentPojo app = new AppointmentPojo();
            app.setPatientId(rs.getString("patient_id"));
            app.setPatientName(rs.getString("patient_name"));
            app.setOpd(rs.getString("opd"));
            app.setAppointmentDate(rs.getString("date_time"));
            app.setStatus(rs.getString("status"));
//            app.setMobileNo(rs.getString("mobile_no"));
//            app.setDoctorName(rs.getString("doctor_name"));
            appList.add(app);
        }
        return appList;
    }
}
