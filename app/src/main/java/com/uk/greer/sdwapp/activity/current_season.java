package com.uk.greer.sdwapp.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;
import com.uk.greer.sdwapp.OnDataReady;
import com.uk.greer.sdwapp.OnFragmentInteractionListener;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.activity.completed.CompletedListFragment;
import com.uk.greer.sdwapp.activity.standing.SeasonStandingListFragment;
import com.uk.greer.sdwapp.activity.upcoming.UpcomingListFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link current_season#newInstance} factory method to
 * create an instance of this fragment.
 */
public class current_season extends Fragment implements OnDataReady {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment current_season.
     */
    // TODO: Rename and change types and number of parameters
    public static current_season newInstance(String param1, String param2) {
        current_season fragment = new current_season();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_current_season, container, false);
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.pager);
        viewPager.setAdapter(new AppSectionsPagerAdapter(getFragmentManager()));
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) v.findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void Refresh(){

        List<Fragment> fl = getFragmentManager().getFragments();

        for (android.support.v4.app.Fragment f : fl) {
            if ( f instanceof OnDataReady ){
                ((OnDataReady) f).Notify();
            }

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void Notify() {
        //TODO: Retrieve update display based on data readiness
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class AppSectionsPagerAdapter extends FragmentStatePagerAdapter {

        private String tabtitles[] = new String[]{
                getResources().getString(R.string.page_upcoming),
                getResources().getString(R.string.page_completed),
                getResources().getString(R.string.page_standings)
        };

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return UpcomingListFragment.newInstance(i, tabtitles[i]);
                case 1:
                    return CompletedListFragment.newInstance(i, tabtitles[i]);
                case 2:
                    return SeasonStandingListFragment.newInstance(i, tabtitles[i], 3);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabtitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabtitles[position];
        }
    }
}
