package com.loginmvvm.demo.data.model;

public class NewUser {

    private String displayName, emailId, mobileNo, password, address, pincode, city;

    public NewUser(String displayName, String emailId, String mobileNo, String password,
                   String address, String pincode, String city) {
        this.displayName = displayName;
        this.emailId = emailId;
        this.mobileNo = mobileNo;
        this.password = password;
        this.address = address;
        this.pincode = pincode;
        this.city = city;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCity() {
        return city;
    }
}
