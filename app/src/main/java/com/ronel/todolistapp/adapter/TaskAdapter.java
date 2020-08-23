package com.ronel.todolistapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ronel.todolistapp.R;
import com.ronel.todolistapp.UpdateTaskActivity;
import com.ronel.todolistapp.pojo.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;
        public TextView iscompleted;
        public LinearLayout task_layout;
        public MyViewHolder(View v) {
            super(v);
             title = v.findViewById(R.id.task_title);
             date = v.findViewById(R.id.task_date);
             iscompleted = v.findViewById(R.id.task_is_compeleted);
             task_layout = v.findViewById(R.id.task_layout);
        }
    }

    public Context mContext;
    public ArrayList<Task>  mTask = new ArrayList<>();

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        this.mContext = context;
        this.mTask = tasks;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       final Task task = mTask.get(position);

       holder.title.setText(task.getTitle());
       holder.date.setText(task.getDate().toString());

       if(task.isCompleted()){
           holder.iscompleted.setText("Completed");
       }else{
           holder.iscompleted.setText("Not Completed");
       }

       holder.task_layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mContext.startActivity(new Intent(mContext, UpdateTaskActivity.class)
                       .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                       .putExtra("task",new Gson().toJson(task)));
           }
       });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
       return mTask.size();
    }
}
