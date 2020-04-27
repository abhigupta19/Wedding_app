package com.example.wedding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    ArrayList<String> name;
    ArrayList<String> email;
    ArrayList<String> phone;
    ArrayList<String> Id;
    ArrayList<String> typeOf;
    ArrayList<String> date;
     Context context;

    public ProfileAdapter(Context context, ArrayList<String> name, ArrayList<String> email, ArrayList<String> phone, ArrayList<String> projectId, ArrayList<String> typeOf, ArrayList<String> date) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.Id = projectId;
        this.typeOf = typeOf;
        this.date = date;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.project,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        holder.name.setText("Name-> "+name.get(position));
        holder.projectid.setText("ProjectId-> "+Id.get(position));
        holder.Date.setText("Date-> "+date.get(position));
        holder.typeOf.setText("Type-> "+typeOf.get(position));

    }

       @Override
    public int getItemCount() {
        return Id.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView projectid;
        TextView name;
        TextView Date;
        TextView typeOf;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             projectid = itemView.findViewById(R.id.projectId);
            name= itemView.findViewById(R.id.name);
            Date = itemView.findViewById(R.id.Date);
            typeOf= itemView.findViewById(R.id.type);

        }
    }
}
