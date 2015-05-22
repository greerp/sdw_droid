package com.inqbarna.tablefixheaders.samples;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inqbarna.tablefixheaders.R;
import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.samples.adapters.MatrixTableAdapter;

public class SimpleTable extends Fragment {

    private static final String GRID_DATA = "data";

    // TODO: Rename and change types of parameters
    private String[][] gridData;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param gridData Parameter 1.
     * @return A new instance of fragment ScrollGrid.
     */
    // TODO: Rename and change types and number of parameters
    public static SimpleTable newInstance(String[][] gridData) {
        SimpleTable fragment = new SimpleTable();
        Bundle args = new Bundle();
        args.putSerializable(GRID_DATA, gridData);
        fragment.setArguments(args);
        return fragment;
    }

    public SimpleTable() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.gridData = (String[][]) getArguments().getSerializable(GRID_DATA);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.table, container, false);

        //TableFixHeaders tableFixHeaders = (TableFixHeaders)v.findViewById(R.id.table);
        TableFixHeaders tableFixHeaders = (TableFixHeaders) v;
        MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<String>(this.getActivity(),
                new String[][]{
                        {
                                "Name",
                                "Ladies Mile",
                                "Pollhill",
                                "East Peckham",
                                "Mayfield ",
                                "Wadhurst",
                                "Hartfield",
                                "Winchet Hill",
                                "Tonbridge Bypass",
                                "Ladies Mile",
                                "Pollhill",
                                "East Peckham",
                                "Mayfield ",
                                "Wadhurst",
                                "Hartfield",
                                "Winchet Hill",
                                "Tonbridge Bypass",
                                "Ladies Mile"},
                        {
                                "Paul Newman",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "19"},
                        {
                                "Neil Armstrong",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "19"},
                        {
                                "Phil Jenkins",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "19"},
                        {
                                "Rory Bremner",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "19",
                                "1",
                                "11",
                                "19"},
                        {
                                "Glen Whittington",
                                "15",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "19"},
                        {
                                "Julia Hawksworth",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "11",
                                "19"},
                        {
                                "David Pettitt",
                                "15",
                                "7",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "19"},
                        {
                                "Matt Evans",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "11",
                                "19"},
                        {
                                "Dick Whittington",
                                "15",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "19"},
                        {
                                "Julia Roberts",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "11",
                                "19"},
                        {
                                "David Steel",
                                "15",
                                "7",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "19"},
                        {
                                "Lee Evans",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "15",
                                "7",
                                "5",
                                "2",
                                "8",
                                "19",
                                "1",
                                "11",
                                "11",
                                "19"},

                });
        tableFixHeaders.setAdapter(matrixTableAdapter);


        return v;
    }
}
