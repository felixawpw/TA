package com.felixawpw.navigation.DatabaseMirror;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tenant {

    //<editor-fold defaultstate="collapsed" desc="ENCAPSULATED FIELDS">

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<Map> getMaps() {
        return maps;
    }

    public void setMaps(List<Map> maps) {
        this.maps = maps;
    }
    //</editor-fold>

    private int id;
    private int userId;
    private String nama;
    private List<Map> maps;

    public Tenant() {

    }

    public Tenant(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.nama = json.getString("nama");
        this.userId = json.getInt("user_id");
        this.setMaps(new ArrayList<Map>());

        JSONArray mapsArray = json.getJSONArray("maps");

        for (int i = 0;i < mapsArray.length(); i++) {
            getMaps().add(new Map(mapsArray.getJSONObject(i), this));
        }
    }

    @Override
    public String toString() {
        return "Total maps : " + this.getMaps().size();
    }

}
