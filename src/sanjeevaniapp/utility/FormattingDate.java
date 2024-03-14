/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanjeevaniapp.utility;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author HP
 */
public class FormattingDate {

    public static void main(String[] args) throws ParseException {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr = sdf.format(today);
        System.out.println(dateStr);
        java.util.Date d1 = sdf.parse(dateStr);
        long ms = d1.getTime();
        java.sql.Date d2 = new java.sql.Date(ms);
        System.out.println(d2);
    }
}
