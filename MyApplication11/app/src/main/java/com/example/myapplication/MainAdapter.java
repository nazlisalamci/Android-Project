package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Upload;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;

    public MainAdapter(Context Context, List<Upload> Uploads) {
        this.mContext = Context;
        this.mUploads = Uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(mContext).inflate(R.layout.item_view,parent,false);
        return new ImageViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Upload uploadCurrent=mUploads.get(position);
        holder.title.setText(uploadCurrent.getmFromWho());
        holder.description.setText(uploadCurrent.getmDescription());

        Picasso.with(mContext)
                .load(uploadCurrent.getmImageUri())
                .fit()
                .centerCrop()
                .into(holder.imagePost);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class  ImageViewHolder extends RecyclerView.ViewHolder{

        public TextView description,title;
        public ImageView imageProfile,imagePost;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            description=itemView.findViewById(R.id.description_text);
            title=itemView.findViewById(R.id.title);
         //   imageProfile=itemView.findViewById(R.id.ivProfile);
            imagePost=itemView.findViewById(R.id.post);

        }
    }

}
