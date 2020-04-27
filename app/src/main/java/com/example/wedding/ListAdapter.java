package com.example.wedding;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    Context context;
    ArrayList<String> photos;

    public ListAdapter(Context context, ArrayList<String> photos) {
        this.context = context;
        this.photos = photos;
        Log.d("kfkfk", String.valueOf(photos.size()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.photos,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        Log.i("kll",photos.get(position));
       Picasso.get().load(photos.get(position)).into(holder.imageView);

//           holder.imageView.setImageResource(R.color.primary_dark);
    }



    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
              imageView=(ImageView)itemView.findViewById(R.id.photos);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {


        }
    }
}
