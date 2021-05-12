package com.example.animelist.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animelist.DetailActivity;
import com.example.animelist.R;
import com.example.animelist.model.Anime;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.ViewHolder>{

    Context context;
    List<Anime> animes;

    public AnimeAdapter(Context context, List<Anime> animes){
        this.context = context;
        this.animes = animes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("AnimeAdapter", "onCreateViewHolder");
        View animeView = LayoutInflater.from(context).inflate(R.layout.item_anime, parent, false);
        return new ViewHolder(animeView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("AnimeAdapter", "onBindViewHolder " + position);
        //Get the movie at the passed in position
        Anime anime = animes.get(position);
        //Bind the movie data into the VH
        holder.bind(anime);
    }

    @Override
    public int getItemCount() {
        return animes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        Button subscribeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            subscribeBtn = itemView.findViewById(R.id.subscribeBtn);
        }

        public void bind(Anime anime) {
            tvTitle.setText(anime.getTitle());
            tvOverview.setText(anime.getSynopsis());
            String imageUrl = anime.getImageURL();
            Glide.with(context).load(imageUrl).into(ivPoster);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("anime",Parcels.wrap(anime));
                    context.startActivity(i);
                }
            });
        }
    }
}


