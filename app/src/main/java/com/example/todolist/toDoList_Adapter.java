package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class toDoList_Adapter
        extends RecyclerView.Adapter {

    public Context ctx;
    public List<toDoList> todo;

    public toDoList_Adapter(Context ctx, List<toDoList> todo) {
        this.ctx = ctx;
        this.todo = todo;
    }

    class VHTodo extends RecyclerView.ViewHolder{

        TextView tvKegiatan;
        TextView tvWaktu;

        public VHTodo(View rowView) {
            super(rowView);
            this.tvKegiatan = rowView.findViewById(R.id.kegiatan);
            this.tvWaktu = rowView.findViewById(R.id.time);

        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(this.ctx).inflate
                (R.layout.row_todo, parent, false);
        return new VHTodo(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VHTodo vh =(VHTodo) holder;
        toDoList t = todo.get(position);

        vh.tvKegiatan.setText(t.kegiatan);
        vh.tvWaktu.setText(t.waktu);
    }

    @Override
    public int getItemCount() {
        return todo.size();
    }
}
