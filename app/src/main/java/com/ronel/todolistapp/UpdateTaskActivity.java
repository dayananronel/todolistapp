package com.ronel.todolistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ronel.todolistapp.pojo.Task;
import com.ronel.todolistapp.presenter.MainActivityPresenter;
import com.ronel.todolistapp.util.Utilities;
import com.ronel.todolistapp.view.MainActivityView;
import com.ronel.todolistapp.view.UpdateTaskView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateTaskActivity extends AppCompatActivity implements UpdateTaskView {

    Task task;

    Toolbar toolbar;
    EditText editTitle,editdate;
    CheckBox edit_isCompleted;

    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        task = new Gson().fromJson(getIntent().getStringExtra("task"),Task.class);

        editTitle = findViewById(R.id.edit_title);
        editdate = findViewById(R.id.edit_date);
        edit_isCompleted = findViewById(R.id.edit_isCompleted);

        editTitle.setText(task.getTitle());
        editdate.setText(task.getDate());

        edit_isCompleted.setChecked(task.isCompleted());

        if(task.isCompleted()){
            edit_isCompleted.setText("Completed");
        }else{
            edit_isCompleted.setText("Not Completed");
        }

        edit_isCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edit_isCompleted.setText("Completed");
                }else{
                    edit_isCompleted.setText("Not Completed.");
                }
            }
        });

        presenter = new MainActivityPresenter(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                    presenter.deleteTask(task);
                    return  true;
            case R.id.done:
                if(editdate.getText().toString().isEmpty() || editTitle.getText().toString().isEmpty()){
                    updateresult("Invalid Input");
                }else{
                    String title,date;
                    boolean isCompleted;

                    title = editTitle.getText().toString();
                    date = editdate.getText().toString();
                    isCompleted = edit_isCompleted.isChecked();

                    if(!Utilities.validateJavaDate(date)){
                        Toast.makeText(getApplicationContext(), "Invalid Date Format", Toast.LENGTH_SHORT).show();
                    }else{
                        Task newTask = new Task(title,date,isCompleted);
                        presenter.updateTask(newTask);
                    }

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updateresult(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void deleteResult(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }


}