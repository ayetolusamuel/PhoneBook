package com.ayetolu.samuel.phonebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ayetolu.samuel.phonebook.adapter.UserAdapter;
import com.ayetolu.samuel.phonebook.model.Info;
import com.ayetolu.samuel.phonebook.model.User;
import com.ayetolu.samuel.phonebook.service.GetCustomerDataService;
import com.ayetolu.samuel.phonebook.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    List<User> userList;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        recyclerView = findViewById(R.id.user_list);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }
        //display arrow on toolbar..........
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = findViewById(R.id.progressBar);
        getData();


    }


    private void init(List<User> users){

        userAdapter = new UserAdapter(getApplicationContext(),users);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userAdapter);
        Toast.makeText(this, "Number of Items Retrieved "+users.size(), Toast.LENGTH_SHORT).show();



    }

    private void getData(){
        progressBar.setVisibility(View.VISIBLE);

        userList = new ArrayList<>();
        GetCustomerDataService getCountryDataService= RetrofitInstance.getService();
        Call<Info> call=getCountryDataService.getResults();
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                Info info = response.body();
                if (info != null && info.getItems() != null){
                    progressBar.setVisibility(View.INVISIBLE);
                    System.out.println(info.getItems());
                    userList = info.getItems();
                    init(userList);
                }
                else{
                    Toast.makeText(UserListActivity.this, "Error getting data!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                t.printStackTrace();
            }
        });




    }
}
