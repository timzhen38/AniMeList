package com.example.animelist;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.animelist.adapters.AnimeAdapter;
import com.example.animelist.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public static final String NOW_PLAYING_URL = "https://api.jikan.moe/v3/season/2021/spring";
    public static final String TAG = "MainActivity";
    List<Anime> animes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvAnime = findViewById(R.id.rvAnimes);
        animes = new ArrayList<>();

        // Create the adapter
        AnimeAdapter animeAdapter = new AnimeAdapter(this, animes);

        // Set the adapter on the recycler side
        rvAnime.setAdapter(animeAdapter);

        // Set a Layout Manager
        rvAnime.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.e(TAG, "anSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("anime");
                    Log.i(TAG, "Results: " + results.toString());
                    animes.addAll(Anime.fromJsonArray(results));
                    animeAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Anime: " + animes.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG, "anFailure");
            }
        });

    }
}

