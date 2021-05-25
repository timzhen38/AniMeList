package com.example.animelist.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.animelist.R;
import com.example.animelist.adapters.AnimeAdapter;
import com.example.animelist.model.Anime;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Headers;

public class SubscriptionsFragment extends Fragment {

    public String NOW_PLAYING_URL;
    public static final String TAG = "SubscriptionsFragment";
    List<Anime> animes;
    protected SwipeRefreshLayout swipeContainer;

    public SubscriptionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_subscriptions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSeason();

        RecyclerView rvSubscribedAnimes = view.findViewById(R.id.rvSubscribedAnimes);
        animes = new ArrayList<>();

        // Create the adapter
        AnimeAdapter animeAdapter = new AnimeAdapter(getContext(), animes);

        // Set the adapter on the recycler side
        rvSubscribedAnimes.setAdapter(animeAdapter);

        // Set a Layout Manager
        rvSubscribedAnimes.setLayoutManager(new LinearLayoutManager(getContext()));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.e(TAG, "anSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    animeAdapter.clear();
                    JSONArray results = jsonObject.getJSONArray("anime");
                    Log.i(TAG, "Results: " + results.toString());
                    List<Anime> temp = Anime.fromJsonArray(results);

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("subscribed");
                    query.whereEqualTo("user", ParseUser.getCurrentUser());
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            List<String> subscribed_anime = new ArrayList<String>();
                            subscribed_anime = object.getList("subscribedAnimes");
                            for (int i = 0; i < temp.size(); i++)
                            {
                                if (subscribed_anime.contains(temp.get(i).getTitle()))
                                {
                                    animes.add(temp.get(i));
                                }
                            }
                            Log.i(TAG, "Anime: " + animes.size());
                            animeAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG, "anFailure");
            }
        });

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Headers headers, JSON json) {
                        Log.e(TAG, "anSuccess");
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            animeAdapter.clear();
                            JSONArray results = jsonObject.getJSONArray("anime");
                            Log.i(TAG, "Results: " + results.toString());
                            List<Anime> temp = Anime.fromJsonArray(results);

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("subscribed");
                            query.whereEqualTo("user", ParseUser.getCurrentUser());
                            query.getFirstInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject object, ParseException e) {
                                    List<String> subscribed_anime = new ArrayList<String>();
                                    subscribed_anime = object.getList("subscribedAnimes");
                                    for (int i = 0; i < temp.size(); i++)
                                    {
                                        if (subscribed_anime.contains(temp.get(i).getTitle()))
                                        {
                                            animes.add(temp.get(i));
                                        }
                                    }
                                    Log.i(TAG, "Anime: " + animes.size());
                                    animeAdapter.notifyDataSetChanged();
                                    swipeContainer.setRefreshing(false);
                                }
                            });
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