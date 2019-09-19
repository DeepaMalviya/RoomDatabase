package com.example.roomdatabaseapplication.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabaseapplication.R;
import com.example.roomdatabaseapplication.data.db.User;
import com.example.roomdatabaseapplication.ui.updateActivity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mCtx;
    private List<User> taskList;

    public UserAdapter(Context mCtx, List<User> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User t = taskList.get(position);
        holder.textViewTask.setText(t.getName());
        holder.textViewDesc.setText(t.getEmail());
        holder.textViewFinishBy.setText(t.getMobile());

        if (t.isFinished())
            holder.textViewStatus.setText("Completed");
        else
            holder.textViewStatus.setText("Not Completed");
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStatus, textViewTask, textViewDesc, textViewFinishBy;

        public UserViewHolder(View itemView) {
            super(itemView);

            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewFinishBy = itemView.findViewById(R.id.textViewFinishBy);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            User user = taskList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, updateActivity.class);
            intent.putExtra("user", user);

            mCtx.startActivity(intent);
        }
    }
}