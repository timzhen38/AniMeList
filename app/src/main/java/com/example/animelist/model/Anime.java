package com.example.animelist.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Anime {

    int animeID;
    String title;
    String image_url;
    String type;
    int episodes;
    double score;
    JSONArray genre;

    public Anime(){}

    public Anime(JSONObject jsonObject) throws JSONException{
        animeID = jsonObject.getInt("mal_id");
        title = jsonObject.getString("title");
        image_url = jsonObject.getString("image_url");
        type = jsonObject.getString("type");
        episodes = jsonObject.getInt("episodes");
        score = jsonObject.getDouble("score");
        genre = jsonObject.getJSONArray("genres");
    }

    public static List<Anime> fromJsonArray(JSONArray animeJsonArray) throws JSONException {
        List<Anime> animes = new ArrayList<>();
        for (int i = 0; i < animeJsonArray.length(); i++)
        {
            animes.add(new Anime(animeJsonArray.getJSONObject(i)));
        }
        return animes;
    }

    public int getAnimeID() {
        return animeID;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getType() {
        return type;
    }

    public int getEpisodes() {
        return episodes;
    }

    public double getScore() {
        return score;
    }

    public JSONArray getGenre() {
        return genre;
    }
}
