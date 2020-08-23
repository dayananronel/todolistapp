package com.ronel.todolistapp.view;

import com.ronel.todolistapp.pojo.Task;

import java.util.ArrayList;
import java.util.List;

public interface MainActivityView {

    void showTaskTodos(ArrayList<Task> tasks);
    void refreshList();
    void showProgressBar();
    void hideProgressBar();
    void showError(String error);

}
