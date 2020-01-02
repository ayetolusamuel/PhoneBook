package com.ayetolu.samuel.phonebook.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users",indices =  @Index(value = {"phoneNumber"},unique = true))
public class User implements Parcelable {


    @SerializedName("full_name")
    @Expose
    private String fullName;

    @PrimaryKey()
    @SerializedName("phone_number")
    @Expose
    @NonNull
    private String phoneNumber;

    @SerializedName("email_address")
    @Expose
    private String emailAddress;

    @SerializedName("resident_address")
    @Expose
    private String residentAddress;


    public User() {

    }

    protected User(Parcel in) {

        fullName = in.readString();
        phoneNumber = in.readString();
        emailAddress = in.readString();
        residentAddress = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(phoneNumber);
        dest.writeString(emailAddress);
        dest.writeString(residentAddress);
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


}