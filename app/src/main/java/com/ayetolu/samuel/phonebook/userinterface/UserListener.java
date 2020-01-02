package com.ayetolu.samuel.phonebook.userinterface;

import com.ayetolu.samuel.phonebook.model.User;

import java.util.List;

public interface UserListener {
    void onStarted();
    void onSuccess(List<User> users);
    void onFailed(String message);

}
