package com.felixawpw.navigation.ServerCommunication;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.felixawpw.navigation.MainActivity;
import com.felixawpw.navigation.NavigationFragment;
import com.felixawpw.navigation.PlacesFragment;
import com.felixawpw.navigation.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class HttpClient {
    private static final HttpClient ourInstance = new HttpClient();

    public static HttpClient getInstance() {
        return ourInstance;
    }

    private HttpClient() {
    }

    public static String TAG = "HttpClientSide::class";

    public void downloadMap(String url, ImageView imageView) {
        Log.d(TAG, "Downloading map at " + url);
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_menu_camera)
                .error(R.drawable.ic_menu_gallery)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                });
    }

//    public File downloadMap(com.felixawpw.navigation.DatabaseMirror.Map map) throws MalformedURLException, IOException {
//        InputStream is = new URL(ServerPath.getInstance().routes.get("storage-map") + map.getId()).openStream();
//        Files.copy(is, Paths.get("temp/" + map.getOriginalPath()), StandardCopyOption.REPLACE_EXISTING);
//        return new File("temp/" + map.getOriginalPath());
//    }

    public void sendGetRequest(RequestQueue queue, String url, final Activity activity, final String requestName) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (activity instanceof MainActivity)
                        {
                            Fragment parentFragment = ((MainActivity)activity).getCurrentFragment();
                            Class fragmentClass = ((MainActivity)activity).getFragmentClass();
                            switch (requestName) {
                                case NavigationFragment.REQUEST_GET_MAP_INFOS:
                                    if (parentFragment instanceof NavigationFragment)
                                        ((NavigationFragment)parentFragment).handleResponse(response);
                                    break;
                                case NavigationFragment.REQUEST_TENANT_BY_PLACES_ID:
                                    if (parentFragment instanceof NavigationFragment)
                                        ((NavigationFragment)parentFragment).handleTenantMap(response);
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Response is " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }
}
