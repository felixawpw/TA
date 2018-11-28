package com.felixawpw.navigation.DatabaseMirror;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public String username;
    public String roles;

    public User() {

    }

    public User(String username, String roles) {
        this.username = username;
        this.roles = roles;
    }

    public User(JSONObject json) throws JSONException {
        this.username = json.getString("username");
        this.roles = json.getString("roles");
    }
}
