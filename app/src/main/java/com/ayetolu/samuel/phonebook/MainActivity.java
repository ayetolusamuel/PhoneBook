package com.ayetolu.samuel.phonebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ayetolu.samuel.phonebook.model.Info;
import com.ayetolu.samuel.phonebook.model.User;
import com.ayetolu.samuel.phonebook.service.GetCustomerDataService;
import com.ayetolu.samuel.phonebook.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    List<User> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void buttonClicked(View view) {
        Intent intent = new Intent(MainActivity.this,UserListActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
       /// getData();
    }

private void init(){


}
    private void getData(){

        GetCustomerDataService getCountryDataService= RetrofitInstance.getService();
        Call<Info> call=getCountryDataService.getResults();
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                Info info = response.body();
                if (info != null && info.getItems() != null){
                    System.out.println(info.getItems());
                 userList = info.getItems();
                    for (User item : userList){
                        System.out.println("ITEM "+item.getIdNumber()+ " ************* "+ item.getDate());
                    }

                }
            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                t.printStackTrace();
            }
        });




    }
}
