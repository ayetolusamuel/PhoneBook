package com.ayetolu.samuel.phonebook.repository;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.ayetolu.samuel.phonebook.api.GetDataService;
import com.ayetolu.samuel.phonebook.api.RetrofitInstance;
import com.ayetolu.samuel.phonebook.dao.UserDao;
import com.ayetolu.samuel.phonebook.dao.UserDatabase;
import com.ayetolu.samuel.phonebook.executor.AppExecutors;
import com.ayetolu.samuel.phonebook.model.Info;
import com.ayetolu.samuel.phonebook.model.User;
import com.ayetolu.samuel.phonebook.userinterface.UserListener;
import com.ayetolu.samuel.phonebook.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private static Context mContext = null;
    private static UserRepository mRepository;
   // private static MutableLiveData<List<User>> mListMutableLiveData;

//    static {
//        mListMutableLiveData = new MutableLiveData<>();
//    }

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    UserDao mDao;
    UserListener mUserListener;


    public UserRepository(Context context) {
        mContext = context.getApplicationContext();
        mDao = UserDatabase.getInstance(context).mDao();
        getData();
//        mListMutableLiveData.observeForever(new Observer<List<User>>() {
//            @Override
//            public void onChanged(List<User> users) {
//
//            }
//        });


    }

    public static UserRepository getInstance(Context context) {
        if (mRepository == null) {
            mRepository = new UserRepository(context);
        }
        return mRepository;

    }


    private void getData() {
        if (mUserListener != null) {
            mUserListener.onStarted();
        }

        GetDataService dataService = RetrofitInstance.getService();
        dataService.getResults().enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                Info info = response.body();
                if (info != null && info.getItems() != null) {
                    if (mUserListener != null) {
                        mUserListener.onSuccess(info.getItems());
                       // mListMutableLiveData.setValue(info.getItems());
                    }
                    //System.out.println(info.getItems());
                }
            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                if (mUserListener != null) {
                    mUserListener.onFailed(t.getMessage());
                }
                t.printStackTrace();
            }
        });

    }


    public void insertUserLocally(User user) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDao.insertUser(user);


            }
        });

    }


    public void insertUserToCloud(User user) {

        GetDataService dataService = RetrofitInstance.getService();
        dataService.saveUser(
                user.getFullName(),
                user.getPhoneNumber(),
                user.getEmailAddress(),
                user.getResidentAddress()
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Utils.newInstance().toast(mContext, "contact added successfully...");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
               t.printStackTrace();

            }
        });


    }


    public void updateUser(Activity activity, User user) {
  GetDataService dataService = RetrofitInstance.getService();
        dataService.updateUser(
                user.getPhoneNumber(),
                user.getEmailAddress(),
                user.getFullName(),
                user.getResidentAddress()


        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String message = "value for " + user.getPhoneNumber() + " is updated successfully.";
                Utils.newInstance().toast(mContext, message);
                Utils.newInstance().openIntent(activity);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
               t.printStackTrace();

            }
        });
    }


    public void deleteUser(String phone_number) {
        AppExecutors.getInstance().diskIO().execute(() -> mDao.deleteUser(phone_number));
        GetDataService dataService = RetrofitInstance.getService();
        dataService.deleteUser(phone_number).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Utils.newInstance().toast(mContext, "delete succesfully!");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void deleteUserLocally(String phoneNumber) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDao.deleteUser(phoneNumber);
            }
        });
    }


    public LiveData<List<User>> getUser() {
        return mDao.getAllUsers();
    }


    public void updateUserLocally(String fullName, String email, String address, String phoneNumber) {
        AppExecutors.getInstance().diskIO().execute(() -> mDao.updateUser(fullName, email, address, phoneNumber));
    }
}
