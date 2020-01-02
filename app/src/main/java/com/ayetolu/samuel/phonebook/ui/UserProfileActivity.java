package com.ayetolu.samuel.phonebook.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ayetolu.samuel.phonebook.R;
import com.ayetolu.samuel.phonebook.viemodel.UserViewModel;
import com.ayetolu.samuel.phonebook.utils.Utils;
import com.ayetolu.samuel.phonebook.model.User;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "UserProfileFragment";
    private TextView fullName, email, phoneNumber, address;
    private Button saveButton;
    private UserViewModel mModel;
    private String viewState = "save";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        fullName = findViewById(R.id.full_name);
        email = findViewById(R.id.email_address);
        phoneNumber = findViewById(R.id.phone_number);
        address = findViewById(R.id.residential_address);
        saveButton = findViewById(R.id.save_user);

        mModel = ViewModelProviders.of(this).get(UserViewModel.class);
        saveButton.setOnClickListener(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(0xFFFFFFFF);
        }
        Intent intent = getIntent();


        if (intent.hasExtra(getString(R.string.user_intent))) {
            viewState = getString(R.string.update);
            User user = intent.getParcelableExtra(getString(R.string.user_intent));
            fullName.setText(user.getFullName());
            email.setText(user.getEmailAddress());
            address.setText(user.getResidentAddress());
            phoneNumber.setText(user.getPhoneNumber());
            saveButton.setText(getString(R.string.update));

        }
        Utils.newInstance().hideKeyboard(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        if (viewState.equalsIgnoreCase("save")) {
            menu.findItem(R.id.delete).setVisible(false);
            menu.findItem(R.id.call).setVisible(false);
            menu.findItem(R.id.chat).setVisible(false);
            menu.findItem(R.id.send_sms).setVisible(false);
        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.call: {
                Log.d(TAG, "onOptionsItemSelected: call");
                Utils.newInstance().callUser(this, phoneNumber.getText().toString());
                break;
            }
            case R.id.delete: {
                String phone_number = phoneNumber.getText().toString();
                if (phone_number.equals("") && phone_number.isEmpty()) {
                    Log.d(TAG, "onOptionsItemSelected: enter valid phone number");
                } else {
                    showDeleteUserDialog(phone_number);
                }
                break;
            }
            case R.id.chat: {
                Utils.newInstance().chatUser(this, phoneNumber.getText().toString());
                break;
            }
            case R.id.send_sms: {
                Utils.newInstance().smsUser(this, phoneNumber.getText().toString());
                break;
            }
        }

        return super.onOptionsItemSelected(item);

    }

    private boolean isTextEmpty() {
        if (phoneNumber.getText().toString().isEmpty()) {
            phoneNumber.setError(getString(R.string.no_empty_phone_number));
            return false;
        } else if (phoneNumber.getText().toString().length() != 11) {
            phoneNumber.setError(getString(R.string.invalid_phone_number));
            return false;
        } else if (email.getText().toString().isEmpty()) {
            email.setError(getString(R.string.no_empty_email));
            return false;
        } else if (fullName.getText().toString().isEmpty()) {
            fullName.setError(getString(R.string.no_empty_name));
            return false;
        } else if (address.getText().toString().isEmpty()) {
            address.setError(getString(R.string.no_empty_address));
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_user: {
                String buttonText = saveButton.getText().toString();
                if (buttonText.equalsIgnoreCase(getString(R.string.save))) {
                    if (isTextEmpty()) {
                        User user = new User();
                        user.setEmailAddress(email.getText().toString());
                        user.setFullName(fullName.getText().toString());
                        user.setPhoneNumber(phoneNumber.getText().toString());
                        user.setResidentAddress(address.getText().toString());
                        mModel.insertUser(user);
                        mModel.insertUserToCloud(user);
                        clearText();
                    }
                } else {
                    User user = new User();
                    user.setFullName(fullName.getText().toString());
                    user.setEmailAddress(email.getText().toString());
                    user.setResidentAddress(address.getText().toString());
                    user.setPhoneNumber(phoneNumber.getText().toString());
                    mModel.updateUser(this, user);
                    mModel.updateUserLocally(user.getFullName(), user.getEmailAddress(), user.getResidentAddress(), user.getPhoneNumber());
                }
            }
            break;
        }
    }


    private void clearText() {
        phoneNumber.setText(getString(R.string.empty_string));
        email.setText(getString(R.string.empty_string));
        address.setText(getString(R.string.empty_string));
        fullName.setText(getString(R.string.empty_string));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, UserListActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    public void showDeleteUserDialog(String userId) {
        DeleteUserDialog dialog = new DeleteUserDialog();
        Bundle args = new Bundle();
        args.putString(getString(R.string.delete_user_id), userId);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), getString(R.string.delete_user_id));
    }
}
