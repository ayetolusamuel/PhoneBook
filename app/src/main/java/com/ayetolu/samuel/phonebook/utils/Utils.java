package com.ayetolu.samuel.phonebook.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ayetolu.samuel.phonebook.ui.UserListActivity;

public class Utils {
    private static Utils mUtils;
    private static final String TAG = "Utils";


    public static Utils newInstance(){
        if (mUtils== null){
            mUtils = new Utils();
        }
        return mUtils;
    }

    public void chatUser(Activity activity,String phone_number){
       if (!phone_number.isEmpty()){

           String whatsappLink = "https://wa.me/234"+phone_number;
           Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(whatsappLink));
           activity.startActivity(browserIntent);
           activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
       }
    }
    public void callUser(Activity activity,String phone_number){

      if (!phone_number.isEmpty()){
          Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone_number, null));
          activity.startActivity(intent);
          activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
      }

    }
    public void smsUser(Activity context,String phone_number){
        if (!phone_number.isEmpty()){
            Uri sms_uri = Uri.parse("smsto:"+phone_number);
            Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
            sms_intent.putExtra("sms_body", "Hi");
            context.startActivity(sms_intent);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }


    }

    public void toast(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    public void openIntent(Activity activity) {
        activity.startActivity(new Intent(activity, UserListActivity.class));
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


    }
    public void hideKeyboard(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


}
