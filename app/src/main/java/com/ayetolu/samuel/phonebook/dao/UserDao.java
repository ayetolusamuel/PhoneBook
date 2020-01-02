package com.ayetolu.samuel.phonebook.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ayetolu.samuel.phonebook.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertUser(User user);

    @Query("SELECT * FROM users ORDER BY fullName")
    LiveData<List<User>> getAllUsers();

    @Query("UPDATE users SET fullName =:fullName,emailAddress =:email, residentAddress =:address WHERE phoneNumber =:phoneNumber")
    void updateUser(String fullName,String email,String address,String phoneNumber);

    @Query("SELECT count(*) FROM users")
    Long getDatabaseCount();

    @Query("DELETE FROM users  WHERE phoneNumber =:phone_number")
    void deleteUser(String phone_number);

}
