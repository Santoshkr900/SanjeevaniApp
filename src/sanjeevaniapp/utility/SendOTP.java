/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanjeevaniapp.utility;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import java.util.Scanner;

/**
 *
 * @author HP
 */
public class SendOTP {

    public static void main(String[] args) {
        try {
            Unirest.setTimeouts(0, 0);
            Scanner kb = new Scanner(System.in);
            System.out.println("Enter mobile number:");
            String mobNo = kb.next();
            int otp = 1000 + ((int) (Math.random() * 999));
            String url = "https://2factor.in/API/V1/383ebe2e-b0dc-11ed-813b-0200cd936042/SMS/" + mobNo + "/" + otp + "/OTP1";
            System.out.println("OTP is:" + otp);
            GetRequest gr = Unirest.get(url);
            HttpResponse<String> response = gr.asString();
            String result = response.getBody();
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println("Error in sending otp");
            ex.printStackTrace();
        }
    }
}
