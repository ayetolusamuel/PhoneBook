package com.ayetolu.samuel.phonebook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("serial_number")
    @Expose
    private String serialNumber;
    @SerializedName("id_number")
    @Expose
    private String idNumber;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("email_address")
    @Expose
    private String emailAddress;
    @SerializedName("resident_address")
    @Expose
    private String residentAddress;
    public final static Parcelable.Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }};

    protected User(Parcel in) {
        this.serialNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.idNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.fullName = ((String) in.readValue((String.class.getClassLoader())));
        this.phoneNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.emailAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.residentAddress = ((String) in.readValue((String.class.getClassLoader())));
    }





    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getResidentAddress() {
        return residentAddress;
    }

    public void setResidentAddress(String residentAddress) {
        this.residentAddress = residentAddress;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(serialNumber);
        dest.writeValue(idNumber);
        dest.writeValue(date);
        dest.writeValue(fullName);
        dest.writeValue(phoneNumber);
        dest.writeValue(emailAddress);
        dest.writeValue(residentAddress);
    }

    public int describeContents() {
        return 0;
    }

}