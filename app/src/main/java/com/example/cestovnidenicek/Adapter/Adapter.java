package com.example.cestovnidenicek.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cestovnidenicek.List;
import com.example.cestovnidenicek.Constants.Constants;
import com.example.cestovnidenicek.R;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    java.util.List<String> titles;
    java.util.List<Integer> images;
    LayoutInflater inflater;
    Activity activity;

    public Adapter(Context context, java.util.List<String> titles, java.util.List<Integer> images, Activity activity) {
        this.titles = titles;
        this.images = images;
        this.activity = activity;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.img.setImageResource(images.get(position));
        holder.linearLayout.setAlpha(0.8F);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(activity, "Clicked on card", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), List.class);
                intent.putExtra(Constants.HEADER_SMALL, titles.get(position));
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView img;
        LinearLayout linearLayout;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
