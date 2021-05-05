package com.example.animelist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.animelist.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.List;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {

    Context context;
    TextView tvTitle;
    TextView tvOverview;
    TextView tvRating;
    ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvDescription);
        ivPoster = findViewById(R.id.ivPoster);
        tvRating=findViewById(R.id.tvRating);

        Anime anime = Parcels.unwrap(getIntent().getParcelableExtra("anime"));

        tvTitle.setText(anime.getTitle());
        tvOverview.setText(anime.getSynopsis());
        //Log.d("DetailActivity",Double.toString(anime.getScore()));
       // String ratingString = Double.toString(anime.getScore());
        tvRating.setText(anime.getScore());
        Glide.with(this).load(anime.getImageURL()).into(ivPoster);
    }
}
