package com.ayetolu.samuel.phonebook.viemodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ayetolu.samuel.phonebook.repository.UserRepository;
import com.ayetolu.samuel.phonebook.model.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository mRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = UserRepository.getInstance(application);
    }


    public void insertUser(User user) {
        mRepository.insertUserLocally(user);
    }

    public void insertUserToCloud(User user) {
        mRepository.insertUserToCloud(user);
    }

    public void deleteUser(String phone_number) {
        mRepository.deleteUser(phone_number);
    }

    public void deleteUserLocally(String phoneNumber) {
        mRepository.deleteUserLocally(phoneNumber);
    }


    public void updateUser(Activity activity, User user) {
        mRepository.updateUser(activity, user);
    }

    public LiveData<List<User>> getUser() {
        return mRepository.getUser();
    }

   public void updateUserLocally(String fullName, String email, String address, String phoneNumber) {
        mRepository.updateUserLocally(fullName, email, address, phoneNumber);
    }


}
