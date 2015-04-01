package com.uk.greer.sdwapp.activity.upcoming;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uk.greer.sdwapp.R;

import java.net.URI;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpcomingRefreshFragment.OnFragmentInteractionListener} interface
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

        return inflater.inflate(R.layout.fragment_upcoming_refresh, container, false);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // When data is ready call back to MainActivity to swap
        public void onFragmentInteraction(Uri uri);
    }

}
