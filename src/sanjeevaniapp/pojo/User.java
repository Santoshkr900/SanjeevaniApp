/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanjeevaniapp.pojo;

public class User {

    @Override
    public String toString() {
        return "User{" + "loginId=" + loginId + ", password=" + password + ", userType=" + userType + '}';
    }

    private String loginId;
    private String password;
    private String userType;

    public User() {

    }

    public User(String loginId, String password, String userType) {
        this.loginId = loginId;
        this.password = password;
        this.userType = userType;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
