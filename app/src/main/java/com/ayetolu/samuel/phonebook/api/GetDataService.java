package com.ayetolu.samuel.phonebook.api;









import com.ayetolu.samuel.phonebook.model.Info;
import com.ayetolu.samuel.phonebook.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface GetDataService {

      @GET("/macros/s/AKfycbxv2qHdE6-GAS-JqEKzAkIvRDetPNd6ua5wT2DmDBD7od8Kxhs/exec?action=read_user_details")
      Call<Info> getResults();

      @POST("/macros/s/AKfycbxv2qHdE6-GAS-JqEKzAkIvRDetPNd6ua5wT2DmDBD7od8Kxhs/exec?action=add_user_details")
      @FormUrlEncoded
      Call<User> saveUser(@Field("full_name") String full_name,
                          @Field("phone_number") String phone_number,
                          @Field("email_address") String email_address,
                          @Field("resident_address") String resident_address);


      @POST("/macros/s/AKfycbxv2qHdE6-GAS-JqEKzAkIvRDetPNd6ua5wT2DmDBD7od8Kxhs/exec?action=update_user_details")
      @FormUrlEncoded
      Call<User> updateUser(@Field("phone_number") String phone_number,
                            @Field("email_address") String email_address,
                            @Field("full_name") String full_name,
                            @Field("resident_address") String resident_address);

      @POST("/macros/s/AKfycbxv2qHdE6-GAS-JqEKzAkIvRDetPNd6ua5wT2DmDBD7od8Kxhs/exec?action=delete_user_details")
      @FormUrlEncoded
      Call<User> deleteUser(@Field("phone_number") String title);



}
