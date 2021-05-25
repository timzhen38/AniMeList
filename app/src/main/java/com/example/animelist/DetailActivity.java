package com.example.animelist;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.animelist.model.Anime;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    Context context;
    TextView tvTitle;
    TextView tvOverview;
    TextView tvGenre;
    TextView tvStudio;
    ImageView ivPoster;
    TextView tvScore;
    TextView tvEpisodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvDescription);
        ivPoster = findViewById(R.id.ivPoster);
        tvGenre=findViewById(R.id.tvGenre);
        tvStudio=findViewById(R.id.tvStudio);
        tvScore=findViewById(R.id.tvScore);
        tvEpisodes=findViewById(R.id.tvEpisodes);

        Anime anime = Parcels.unwrap(getIntent().getParcelableExtra("anime"));

        tvTitle.setText(anime.getTitle());
        tvOverview.setText(anime.getSynopsis());
        tvGenre.setText(anime.getGenre());
        tvStudio.setText(anime.getStudio());
        tvScore.setText("Score: "+anime.getScore());
        tvEpisodes.setText("Totals Episodes: "+anime.getEpisodes());
        Glide.with(this).load(anime.getImageURL()).into(ivPoster);
    }
}
