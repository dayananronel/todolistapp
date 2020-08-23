package com.ronel.todolistapp.rest;

import com.ronel.todolistapp.pojo.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TaskAPI {

    @GET("tasks")
    Call<ArrayList<Task> > getTask();

    @GET("tasks/{id}/")
    Call<ArrayList<Task>> getSpecificTask(@Path("id") int id);

    @PUT("tasks/{id}/")
    Call<ArrayList<Task>> updateTask(@Path("id") int id,
                                     @Body Task task);


    @POST("tasks")
    @FormUrlEncoded
    Call<Object> addTask(@Field("title") String title,
                             @Field("date") String date,
                             @Field("competed") boolean completed);



    @DELETE("tasks/{id}/")
    Call<Void> deleteTask(@Path("id") int id);


}
