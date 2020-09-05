package com.example.refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerSwiperAdaptr extends RecyclerView.Adapter<RecyclerSwiperAdaptr.Recycle> {
    Context context;
    ArrayList<String>name;

    public RecyclerSwiperAdaptr(Context context, ArrayList<String> name) {
        this.context = context;
        this.name = name;
    }

    @NonNull
    @Override
    public Recycle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.rootlayout,parent,false);
        return new Recycle(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Recycle holder, int position) {

        holder.textView.setText(name.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class Recycle extends RecyclerView.ViewHolder {
        TextView textView;
        public Recycle(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tname);
        }
    }
}
