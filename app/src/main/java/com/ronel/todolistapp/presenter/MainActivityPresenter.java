package com.ronel.todolistapp.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.ronel.todolistapp.pojo.Task;
import com.ronel.todolistapp.rest.TaskAPI;
import com.ronel.todolistapp.util.RetrofitBuilder;
import com.ronel.todolistapp.view.MainActivityView;
import com.ronel.todolistapp.view.UpdateTaskView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivityPresenter {

    private Task task;
    private MainActivityView view;
    private UpdateTaskView updateTaskView;
    TaskAPI taskAPI;
    Retrofit retrofit;

    public MainActivityPresenter(MainActivityView view){
        this.view = view;
        retrofit = new RetrofitBuilder().getRetrofitBuilder();
        taskAPI  = retrofit.create(TaskAPI.class);

    }

    public MainActivityPresenter(UpdateTaskView view){
        this.updateTaskView = view;
        retrofit = new RetrofitBuilder().getRetrofitBuilder();
        taskAPI  = retrofit.create(TaskAPI.class);

    }


    public void showTask(){
        view.showProgressBar();
        Call<ArrayList<Task> > call = taskAPI.getTask();
        call.enqueue(new Callback<ArrayList<Task> >() {
            @Override
            public void onResponse(Call<ArrayList<Task> > call, Response<ArrayList<Task>> response) {
                Log.d("okhttp",new Gson().toJson(response.body()));
                if(response.body() != null){
                    view.showTaskTodos(response.body());
                    Log.d("okhttp", "COUNT : "+ String.valueOf(response.body().size()));
                }

                view.hideProgressBar();
            }


            @Override
            public void onFailure(Call<ArrayList<Task> > call, Throwable t) {
                Log.d("okhttp",new Gson().toJson(t));
                 view.showError(t.getMessage());
                 t.printStackTrace();
                 view.hideProgressBar();
            }
        });
    }

    public void showSpecificTask(int id){
        view.showProgressBar();
        Call<ArrayList<Task> > call = taskAPI.getSpecificTask(id);
        call.enqueue(new Callback<ArrayList<Task> >() {
            @Override
            public void onResponse(Call<ArrayList<Task> > call, Response<ArrayList<Task>> response) {
                Log.d("okhttp",new Gson().toJson(response.body()));
                if(response.body() != null){
                    view.showTaskTodos(response.body());
                    Log.d("okhttp", "COUNT : "+ String.valueOf(response.body().size()));
                }
                view.hideProgressBar();
            }
            @Override
            public void onFailure(Call<ArrayList<Task> > call, Throwable t) {
                Log.d("okhttp",new Gson().toJson(t));
                view.showError(t.getMessage());
                t.printStackTrace();
                view.hideProgressBar();
            }
        });
    }

    public void addTask(Task task){

        view.showProgressBar();

        Call<Object> call = taskAPI.addTask(task.getTitle(),
                                            task.getDate(),
                                            task.isCompleted());
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("okhttp",new Gson().toJson(response));
                view.showError("Task Added.");
                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                view.showError(t.getMessage());
                t.printStackTrace();
                view.hideProgressBar();
            }
        });
    }

    public void updateTask(Task task){
        Call<ArrayList<Task> > call = taskAPI.updateTask(task.getId(),task);
        call.enqueue(new Callback<ArrayList<Task> >() {
            @Override
            public void onResponse(Call<ArrayList<Task> > call, Response<ArrayList<Task> > response) {
                updateTaskView.updateresult("Task Updated.");
            }

            @Override
            public void onFailure(Call<ArrayList<Task> > call, Throwable t) {
                updateTaskView.updateresult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void deleteTask(Task task){
        Call<Void> call = taskAPI.deleteTask(task.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                updateTaskView.deleteResult("Task Deleted.");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                updateTaskView.deleteResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }


}
