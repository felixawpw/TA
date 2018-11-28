package com.felixawpw.navigation.ServerCommunication;

import java.util.HashMap;
import java.util.Map;
public class ServerPath {
    private final String SERVER_IP = "http://192.168.0.22:8000/";
    public final Map<String, String> routes = new HashMap<>();

    private static final ServerPath ourInstance = new ServerPath();


    public static ServerPath getInstance() {
        return ourInstance;
    }

    public static String MAP_BY_ID = "map-by-id";
    public static String MAP_BY_TENANT = "map-by-tenant";
    public static String TENANT_ALL = "tenant-all";
    public static String TENANT_BY_ID = "tenant-by-id";
    public static String TENANT_BY_PLACES_ID = "tenant-by-places-id";

    public static String LOGIN = "login";
    public static String STORAGE_MAP = "storage-map";
    public static String MAP_INFOS = "map-infos";
    private ServerPath() {
        //GET
        routes.put("map-by-id", SERVER_IP + "external/map/"); //map_id
        routes.put("map-by-tenant", SERVER_IP + "external/tenant/map/"); //tenant_id
        routes.put("tenant-all", SERVER_IP + "external/tenant/all");
        routes.put("tenant-by-id", SERVER_IP + "external/tenant/by_id/"); //tenant_id
        routes.put("tenant-by-places-id", SERVER_IP + "external/tenant/by_places_id/");

        //POST
        routes.put("login", SERVER_IP + "external/login");
        routes.put("storage-map", SERVER_IP + "external/storage/map/original/");
        routes.put("map-infos", SERVER_IP + "external/image_infos/"); //Map ID

    }

    public static String getURL(String routeName) {
        return ServerPath.getInstance().routes.get(routeName);
    }
}
