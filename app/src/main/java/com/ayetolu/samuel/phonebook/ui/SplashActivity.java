package com.ayetolu.samuel.phonebook.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ayetolu.samuel.phonebook.R;
import com.ayetolu.samuel.phonebook.model.User;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private static final String TAG = "SplashActivity";

    List<User> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");


    }

    public void buttonClicked(View view) {
        Intent intent = new Intent(SplashActivity.this,UserListActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        Log.d(TAG, "buttonClicked: ");

    }


}
