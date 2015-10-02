package com.uk.greer.sdwapp.activity.upcoming;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uk.greer.sdwapp.OnFragmentInteractionListener;
import com.uk.greer.sdwapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpcomingRefreshFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingRefreshFragment extends Fragment {

    private OnFragmentInteractionListener fragmentInteractionListener;


    public static UpcomingRefreshFragment newInstance() {

        UpcomingRefreshFragment fragment = new UpcomingRefreshFragment();
        return fragment;
    }

    public UpcomingRefreshFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.upcoming_event_refresh, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentInteractionListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void DataReady(){

        //TODO: This method is invoked by a Receiever from a data service it then calls back to the activity
        fragmentInteractionListener.onFragmentInteraction(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteractionListener = null;
    }


}
