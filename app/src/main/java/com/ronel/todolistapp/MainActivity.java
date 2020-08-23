package com.ronel.todolistapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ronel.todolistapp.adapter.TaskAdapter;
import com.ronel.todolistapp.pojo.Task;
import com.ronel.todolistapp.presenter.MainActivityPresenter;
import com.ronel.todolistapp.util.Utilities;
import com.ronel.todolistapp.view.MainActivityView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityView, SwipeRefreshLayout.OnRefreshListener {

    private MainActivityPresenter presenter;
    private ProgressDialog progressDialog;

    //Views
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;

    TaskAdapter taskAdapter;

    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        presenter = new MainActivityPresenter(this);

        //
        progressDialog = new ProgressDialog(this);
        searchView = findViewById(R.id.searchview);
        recyclerView = findViewById(R.id.rv_list);
        fab = findViewById(R.id.fab);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtaskDialog();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.showSpecificTask(Integer.parseInt(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);

        presenter.showTask();

    }


    @Override
    public void showTaskTodos(ArrayList<Task>  tasks) {


        Collections.sort(tasks);

        taskAdapter = new TaskAdapter(getApplicationContext(),tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(taskAdapter);

        Log.d("okhttp","TASK : "+  taskAdapter.getItemCount());

    }

    @Override
    public void refreshList() {
        presenter.showTask();
    }

    @Override
    public void showProgressBar() {
        swipeRefreshLayout.setRefreshing(true);
        if(progressDialog == null){
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait....");
            progressDialog.show();
        }
    }

    @Override
    public void hideProgressBar() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }


    public void addtaskDialog(){
       if(alertDialog == null){

           View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_task,null,false);
           final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
           builder.setTitle("Add Task");
           builder.setView(view);
           builder.setCancelable(false);

           final EditText inputTitle = view.findViewById(R.id.input_title);
           final EditText inputDate = view.findViewById(R.id.input_date);


           builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                 if(inputTitle.getText().toString().isEmpty()|| inputDate.getText().toString().isEmpty()){
                     Toast.makeText(MainActivity.this, "Invalid Input.", Toast.LENGTH_SHORT).show();
                 }else{
                     if(!Utilities.validateJavaDate(inputDate.getText().toString())){
                         Toast.makeText(getApplicationContext(),"Invalid date format",Toast.LENGTH_LONG).show();
                     }else{
                         Task task = new Task(inputTitle.getText().toString(),inputDate.getText().toString(),false);
                         presenter.addTask(task);
                         refreshList();
                         //alertDialog = null;
                     }
                 }
               }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   alertDialog = null;
               }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    alertDialog = null;
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        recyclerView.setAdapter(null);
        refreshList();
    }
}