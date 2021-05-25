package com.example.animelist.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.animelist.R;
import com.example.animelist.adapters.AnimeAdapter;
import com.example.animelist.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Headers;

public class AnimeFragment extends Fragment {

    public String NOW_PLAYING_URL;
    public static final String TAG = "MainActivity";
    List<Anime> animes;

    public AnimeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getSeason();

        RecyclerView rvAnime = view.findViewById(R.id.rvAnimes);
        animes = new ArrayList<>();

        // Create the adapter
        AnimeAdapter animeAdapter = new AnimeAdapter(getContext(), animes);

        // Set the adapter on the recycler side
        rvAnime.setAdapter(animeAdapter);

        // Set a Layout Manager
        rvAnime.setLayoutManager(new LinearLayoutManager(getContext()));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.e(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("anime");
                    //Log.i(TAG, "Results: " + results.toString());
                    animes.addAll(Anime.fromJsonArray(results));
                    animeAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Anime: " + animes.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG, "onFailure Compose Activity");
            }
        });

    }

    private void getSeason()
    {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        String seasons[] = {"","winter", "winter", "winter", "spring", "spring", "spring", "summer", "summer",
                "summer", "fall", "fall", "fall"};
        String season = seasons[month];
        NOW_PLAYING_URL = "https://api.jikan.moe/v3/season/" + year + "/" + season;
    }
}