package com.example.animelist.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Anime {

    int animeID;
    String title;
    String synopsis;
    String image_url;
    String type;
    //int episodes;
    String score;
    String genre;

    public Anime(){}

    public Anime(JSONObject jsonObject) throws JSONException{
        animeID = jsonObject.getInt("mal_id");
        title = jsonObject.getString("title");
        synopsis = jsonObject.getString("synopsis");
        image_url = jsonObject.getString("image_url");
        type = jsonObject.getString("type");
        //episodes = jsonObject.getInt("episodes");
        score = jsonObject.getString("score");
        genre = allGenres(jsonObject);
        //genre = new String[]{"test1", "test2"};
    }

    public static List<Anime> fromJsonArray(JSONArray animeJsonArray) throws JSONException {
        List<Anime> animes = new ArrayList<>();
        for (int i = 0; i < animeJsonArray.length(); i++)
        {
            animes.add(new Anime(animeJsonArray.getJSONObject(i)));
        }
        return animes;
    }

    public String allGenres(JSONObject jsonObject) throws JSONException {
        JSONArray genreList = jsonObject.getJSONArray("genres");
        String allGenreString = "";
        for(int i=0;i<genreList.length();i++)
        {
            if(i==genreList.length()-1)
                allGenreString+=genreList.getJSONObject(i).getString("name");
            else
                allGenreString+=genreList.getJSONObject(i).getString("name")+", ";
        }
        return allGenreString;
    }

    public int getAnimeID() {
        return animeID;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getImageURL() {
        return image_url;
    }

    public String getType() { return type; }

    public String getScore() { return score; }

    public String getGenre() { return genre; }

    /*public int getEpisodes() {
        return episodes;
    }


    }*/
}
