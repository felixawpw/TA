package com.felixawpw.navigation.GoogleAPI;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.felixawpw.navigation.MainActivity;
import com.felixawpw.navigation.NavigationFragment;
import com.felixawpw.navigation.PlacesFragment;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlacesOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class PlacesAPI {
    public static int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 111;

    public static String TAG = "PlacesAPI::class";
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private Context mContext;
    private Activity mActivity;

    public PlacesAPI(Context context, Activity activity) {
        mContext = context;
        mActivity = activity;

        mGeoDataClient = Places.getGeoDataClient(mContext);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(mContext);

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    public void getCurrentPlaces(final String requestName) {
        Log.d(TAG, "Permission: " + ContextCompat.checkSelfPermission( mContext, Manifest.permission.ACCESS_FINE_LOCATION ));

        if ( ContextCompat.checkSelfPermission( mContext, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);
            placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                    PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                    List<Place> places = new ArrayList<>();

                    int limit = 5;
                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                        places.add(new Place(placeLikelihood));
                        if (limit-- <= 0)
                            break;
                    }

                    likelyPlaces.release();


                    if (mActivity instanceof MainActivity)
                    {
                        Log.d(TAG, "Come here");
                        Fragment parentFragment = ((MainActivity)mActivity).getCurrentFragment();
                        Class fragmentClass = ((MainActivity)mActivity).getFragmentClass();
                        if (requestName.equals("populateSpinner"))
                            if (parentFragment instanceof PlacesFragment)
                                ((PlacesFragment)parentFragment).handlePlacesLikelihood(places);
                    }
                }
            });
        }
    }
}
