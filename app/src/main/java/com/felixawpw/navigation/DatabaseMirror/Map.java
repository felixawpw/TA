package com.felixawpw.navigation.DatabaseMirror;

import org.json.JSONException;
import org.json.JSONObject;

public class Map {

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

    /**
     * @return the deskripsi
     */
    public String getDeskripsi() {
        return deskripsi;
    }

    /**
     * @param deskripsi the deskripsi to set
     */
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    /**
     * @return the processedPath
     */
    public String getProcessedPath() {
        return processedPath;
    }

    /**
     * @param processedPath the processedPath to set
     */
    public void setProcessedPath(String processedPath) {
        this.processedPath = processedPath;
    }

    /**
     * @return the originalPath
     */
    public String getOriginalPath() {
        return originalPath;
    }

    /**
     * @param originalPath the originalPath to set
     */
    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    /**
     * @return the tenant
     */
    public Tenant getTenant() {
        return tenant;
    }

    /**
     * @param tenant the tenant to set
     */
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
    //</editor-fold>

    private int id;
    private String nama;
    private String deskripsi;
    private String processedPath;
    private String originalPath;
    private Tenant tenant;

    public Map() {
        this.id = 1;
    }

    public Map(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.nama = json.getString("nama");
        this.deskripsi = json.getString("deskripsi");
        this.processedPath = json.isNull("processed_path") ? "" : json.getString("processed_path");
        this.originalPath = json.getString("original_path");
        this.tenant = new Tenant(json.getJSONObject("tenant"));
    }

    public Map(JSONObject json, Tenant tenant) throws JSONException {
        this.id = json.getInt("id");
        this.nama = json.getString("nama");
        this.deskripsi = json.getString("deskripsi");
        this.processedPath = json.isNull("processed_path") ? "" : json.getString("processed_path");
        this.originalPath = json.getString("original_path");
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return String.format("%s : %s", this.nama, this.deskripsi);
    }
}