package com.ayetolu.samuel.phonebook.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayetolu.samuel.phonebook.R;
import com.ayetolu.samuel.phonebook.viemodel.UserViewModel;
import com.ayetolu.samuel.phonebook.adapter.UserAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserListActivity extends AppCompatActivity {
    private static final String TAG = "UserListActivity";
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private SearchView mSearchView;
    private UserViewModel mViewModel;
    private String productSearch;
    private RelativeLayout mRelativeLayout;
    private TextView mSearchProductErrorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        FloatingActionButton actionButton = findViewById(R.id.fab);

        recyclerView = findViewById(R.id.recyclerView);
        mSearchView = findViewById(R.id.searchView);
        mRelativeLayout = findViewById(R.id.relLayout);
        mSearchProductErrorName = findViewById(R.id.check_product_name_text_view);

        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userAdapter = new UserAdapter(this);

        init();
        setUpViewModel();
        initSearchView();


        actionButton.setOnClickListener((click) -> {
            Intent intent = new Intent(UserListActivity.this, UserProfileActivity.class);
            intent.putExtra(getString(R.string.intent_passed), R.string.constant);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userAdapter);

        initListener();
    }

    /*
     the method handle click on the recyclerview item
      */
    private void initListener() {
        userAdapter.setListener((view, user) -> {
            Intent intent = new Intent(UserListActivity.this, UserProfileActivity.class);
            intent.putExtra(getString(R.string.user_intent), user);
            startActivity(intent);

        });
    }


    private void setUpViewModel() {
        mViewModel.getUser().observe(this, users -> {
            if (users.size() == 0) {
                mRelativeLayout.setVisibility(View.VISIBLE);
                return;
            }
            mRelativeLayout.setVisibility(View.GONE);
            userAdapter.setUser(users);
        });
    }


    private void initSearchView() {
        Log.d(TAG, "initSearchView: ");
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager
                .getSearchableInfo(this.getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                userAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (userAdapter.getItemCount() == 0) {
                    mRelativeLayout.setVisibility(View.GONE);
                }
                productSearch = query;
                userAdapter.getFilter().filter(query);
                searchItemisEmptyView();
                return false;
            }
        });
    }

    private void searchItemisEmptyView() {
        userAdapter.setFilterProductListener(count -> {
            if (count == 0) {
                runOnUiThread(() -> {
                    mSearchProductErrorName.setText(getString(R.string.error_message, productSearch));
                    mRelativeLayout.setVisibility(View.VISIBLE);
                });
            }
        });

    }


}
