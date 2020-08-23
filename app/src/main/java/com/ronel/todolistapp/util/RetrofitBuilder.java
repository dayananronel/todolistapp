package com.ronel.todolistapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    Retrofit retrofit;

   public Retrofit getRetrofitBuilder(){
       Gson gson = new GsonBuilder()
               .setLenient()
               .create();
       if(retrofit == null){
           retrofit = new Retrofit.Builder()
                   .baseUrl(CommonVariables.base_url)
                   .addConverterFactory(GsonConverterFactory.create(gson))
                   .build();
       }
       return  retrofit;
   }


}
