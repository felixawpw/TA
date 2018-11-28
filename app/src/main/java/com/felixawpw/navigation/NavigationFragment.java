package com.felixawpw.navigation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.felixawpw.navigation.DatabaseMirror.Map;
import com.felixawpw.navigation.DatabaseMirror.Tenant;
import com.felixawpw.navigation.GoogleAPI.Place;
import com.felixawpw.navigation.ImageProcesser.CustomImage;
import com.felixawpw.navigation.Navigation.Agent;
import com.felixawpw.navigation.Navigation.GridMap;
import com.felixawpw.navigation.ServerCommunication.HttpClient;
import com.felixawpw.navigation.ServerCommunication.ServerPath;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavigationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationFragment extends Fragment {
    public static final String TAG = "NavigationFragment::class";

    public static final String REQUEST_TENANT_BY_PLACES_ID = "getTenantByPlacesId";
    public static final String REQUEST_GET_MAP_INFOS = "getMapInfos";

    private OnFragmentInteractionListener mListener;

    public NavigationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavigationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavigationFragment newInstance(String param1, String param2) {
        NavigationFragment fragment = new NavigationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        imageMap = (ImageView) view.findViewById(R.id.fragment_navigation_image_view);
        comboMaps = (Spinner) view.findViewById(R.id.fragment_navigation_combo_maps);

        Bundle extras = getArguments();
        String placesId = extras.getString("places_id");

        Log.d(TAG, "Places id = " + placesId);

        RequestQueue rq = Volley.newRequestQueue(getActivity());
        HttpClient.getInstance().sendGetRequest(rq, ServerPath.getURL(ServerPath.TENANT_BY_PLACES_ID) + placesId, getActivity(), NavigationFragment.REQUEST_TENANT_BY_PLACES_ID);


        return view;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    Agent agent;
    ImageView imageMap;
    Spinner comboMaps;
    Tenant tenant;
    public void handleTenantMap(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);

            JSONObject jsonTenant = jsonResponse.getJSONObject("data");
            tenant = new Tenant(jsonTenant);
            Log.d(TAG, tenant.toString());

            final ArrayAdapter<Map> adapter = new ArrayAdapter<Map>(getActivity(), R.layout.support_simple_spinner_dropdown_item, tenant.getMaps());
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            comboMaps.setAdapter(adapter);

            comboMaps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    RequestQueue rq = Volley.newRequestQueue(getActivity());
                    HttpClient.getInstance().downloadMap(ServerPath.getInstance().routes.get("storage-map") + tenant.getMaps().get(position).getId(), imageMap);
                    HttpClient.getInstance().sendGetRequest(rq, ServerPath.getInstance().routes.get("map-infos") + tenant.getMaps().get(position).getId(), getActivity(), ServerPath.MAP_INFOS);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void handleResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            String imageData = jsonResponse.getString("image_data");
            String[] imageDataToken = imageData.split("\r\n");

            CustomImage mapData = new CustomImage(imageMap, jsonResponse);
            agent = new Agent(new GridMap(mapData), 1, 1);
            agent.GoTo(1,4);
            Log.d(TAG, "Map Data : " + mapData.toString());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
