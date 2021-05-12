package com.example.animelist;

import com.example.animelist.model.Anime;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("subscribed")
public class SubscribedAnime extends ParseObject {
    public static final String KEY_SUBSCRIBED = "subscribedAnimes";
    public static final String KEY_USER = "User";

    public List<Anime> getSubscribed() {return getList(KEY_SUBSCRIBED); }

    public void setSubscribed(List<Anime> subscribed) {put (KEY_SUBSCRIBED, subscribed); }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }
}
