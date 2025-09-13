package com.example.cestovnidenicek.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cestovnidenicek.Constants.Constants;
import com.example.cestovnidenicek.Database.RoomDB;
import com.example.cestovnidenicek.Models.Items;
import com.example.cestovnidenicek.R;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListViewHolder> {

    Context context;
    List<Items> itemsList;
    RoomDB database;
    String show;

    public CheckListAdapter(){
    }

    public CheckListAdapter(Context context, List<Items> itemsList, RoomDB database, String show) {
        this.context = context;
        this.itemsList = itemsList;
        this.database = database;
        this.show = show;
        if(itemsList.size() == 0)
            Toast.makeText(context.getApplicationContext(), "Zde nic není", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public CheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListViewHolder holder, int position) {
        holder.checkBox.setText(itemsList.get(position).getItemname());
        holder.checkBox.setChecked(itemsList.get(position).getChecked());

        if(Constants.FALSE_STRING.equals(show)){
            holder.dltButton.setVisibility(View.GONE);
            holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.intro_black_screen));
        }else {
            if(itemsList.get(position).getChecked()){
                holder.layout.setBackgroundColor(Color.parseColor("#317743"));
            } else
                holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.intro_black_screen));
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean check = holder.checkBox.isChecked();
                database.mainDao().checkUncheck(itemsList.get(position).getID(), check);
                if (Constants.FALSE_STRING.equals(show)){
                    itemsList = database.mainDao().getAllSelected(true);
                    notifyDataSetChanged();
                }
                else {
                    itemsList.get(position).setChecked(check);
                    notifyDataSetChanged();
                    Toast toastMessage = null;
                    if(toastMessage!=null){
                        toastMessage.cancel();
                    }
                    if(itemsList.get(position).getChecked()){
                        toastMessage = Toast.makeText(context, "("+holder.checkBox.getText()+")"+" je označeno", Toast.LENGTH_SHORT);
                    }else {
                        toastMessage = Toast.makeText(context, "("+holder.checkBox.getText()+")"+" je odznačeno", Toast.LENGTH_SHORT);
                    }
                    toastMessage.show();

                }
            }
        });
        holder.dltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Vymazání položky ("+itemsList.get(position).getItemname()+")")
                        .setMessage("Opravdu chcete vymazat položku?")
                        .setPositiveButton("Ano", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.mainDao().delete(itemsList.get(position));
                                itemsList.remove(itemsList.get(position));
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("Zrušit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        }).setIcon(R.drawable.baseline_clear_24_cross_navzdy)
                        .show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}

class CheckListViewHolder extends RecyclerView.ViewHolder {

    LinearLayout layout;
    CheckBox checkBox;
    Button dltButton;

    public CheckListViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.linearLayout);
        checkBox = itemView.findViewById(R.id.checkbox);
        dltButton = itemView.findViewById(R.id.dltButton);

    }
}