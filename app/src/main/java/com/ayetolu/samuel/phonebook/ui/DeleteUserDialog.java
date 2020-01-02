package com.ayetolu.samuel.phonebook.ui;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.ayetolu.samuel.phonebook.R;
import com.ayetolu.samuel.phonebook.ui.UserListActivity;
import com.ayetolu.samuel.phonebook.viemodel.UserViewModel;

import java.util.Objects;


public class DeleteUserDialog extends DialogFragment {

    private static final String TAG = "DeleteUserDialog";
    private UserViewModel mViewModel;

    //create a new bundle and set the arguments to avoid a null pointer
    public DeleteUserDialog(){
        super();
        setArguments(new Bundle());
    }

    private String mUserId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        Log.d(TAG, "onCreate: started");
       mUserId = getArguments().getString(getString(R.string.delete_user_id));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_delete_user, container, false);


        TextView delete = view.findViewById(R.id.confirm_delete);
        delete.setOnClickListener(v -> {
           mViewModel.deleteUserLocally(mUserId);
            mViewModel.deleteUser(mUserId);
            Objects.requireNonNull(getActivity()).startActivity(new Intent(getContext(), UserListActivity.class));
        });

        TextView cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> {
            Log.d(TAG, "onClick: cenceling deletion of chatroom");
            getDialog().dismiss();
        });


        return view;
    }

}

















