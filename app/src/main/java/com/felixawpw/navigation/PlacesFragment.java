package com.felixawpw.navigation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.felixawpw.navigation.GoogleAPI.Place;
import com.felixawpw.navigation.GoogleAPI.PlacesAPI;

import java.util.List;


public class PlacesFragment extends Fragment {
    public static final String TAG = "PlacesFragment::class";

    private OnFragmentInteractionListener mListener;

    public PlacesFragment() {
        // Required empty public constructor
    }

    public static PlacesFragment newInstance(String param1, String param2) {
        PlacesFragment fragment = new PlacesFragment();
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    PlacesAPI placesApi;
    Button buttonSelectPlace;
    Button buttonFirstSelection, buttonSecondSelection, buttonThirdSelection, buttonFourthSelection;
    Button[] selectionButtons;
    int currentSelectedPlacesIndex = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places_auto, container, false);
        buttonSelectPlace = (Button)view.findViewById(R.id.fragment_places_button_select_place);
        buttonFirstSelection = (Button)view.findViewById(R.id.fragment_places_button_first_selection);
        buttonSecondSelection = (Button)view.findViewById(R.id.fragment_places_button_second_selection);
        buttonThirdSelection = (Button)view.findViewById(R.id.fragment_places_button_third_selection);
        buttonFourthSelection = (Button)view.findViewById(R.id.fragment_places_button_fourth_selection);

        buttonFirstSelection.setOnClickListener(buttonSelectionClick);
        buttonSecondSelection.setOnClickListener(buttonSelectionClick);
        buttonThirdSelection.setOnClickListener(buttonSelectionClick);
        buttonFourthSelection.setOnClickListener(buttonSelectionClick);

        selectionButtons = new Button[]{buttonFirstSelection, buttonSecondSelection, buttonThirdSelection, buttonFourthSelection};


        placesApi = new PlacesAPI(getActivity(), getActivity());
        placesApi.getCurrentPlaces("populateSpinner");



        return view;
    }

    View.OnClickListener buttonSelectionClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fragment_places_button_first_selection:
                    resetButtonBackground();
                    selectionButtons[0].setBackgroundResource(R.drawable.button_bg_selected);
                    currentSelectedPlacesIndex = 0;
                    break;
                case R.id.fragment_places_button_second_selection:
                    resetButtonBackground();
                    selectionButtons[1].setBackgroundResource(R.drawable.button_bg_selected);
                    currentSelectedPlacesIndex = 1;
                    break;
                case R.id.fragment_places_button_third_selection:
                    resetButtonBackground();
                    selectionButtons[2].setBackgroundResource(R.drawable.button_bg_selected);
                    currentSelectedPlacesIndex = 2;
                    break;
                case R.id.fragment_places_button_fourth_selection:
                    resetButtonBackground();
                    selectionButtons[3].setBackgroundResource(R.drawable.button_bg_selected);
                    currentSelectedPlacesIndex = 3;
                    break;
            }
        }
    };

    public void resetButtonBackground() {
        for (Button button : selectionButtons) {
            button.setBackgroundResource(R.drawable.button_bg);
        }
    }

    public void handlePlacesLikelihood(final List<Place> places) {
        for (int i = 0; i < places.size(); i++) {
            if (i > 3) break;

            selectionButtons[i].setText(places.get(i).getName());
        }

        buttonSelectPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Place place = places.get(currentSelectedPlacesIndex);
                Bundle bundle = new Bundle();
                bundle.putString("places_id", place.getId());

                ((MainActivity)getActivity()).changeFragment(NavigationFragment.class, bundle);
            }
        });

        Log.d(TAG,"Places found = " + places.get(0).getId());
    }
}
