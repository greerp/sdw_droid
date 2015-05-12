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
    private String [][] gridData;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param gridData Parameter 1.
     * @return A new instance of fragment ScrollGrid.
     */
    // TODO: Rename and change types and number of parameters
    public static SimpleTable newInstance(String [][] gridData) {
        SimpleTable fragment = new SimpleTable();
        Bundle args = new Bundle();
        args.putSerializable(GRID_DATA,gridData);
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
        TableFixHeaders tableFixHeaders = (TableFixHeaders)v;
        MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<String>(this.getActivity(),
                new String[][] {
                {
                        "Header 1",
                        "Header 2",
                        "Header 3",
                        "Header 4",
                        "Header 5",
                        "Header 6" },
                {
                        "Lorem",
                        "sed",
                        "do",
                        "eiusmod",
                        "tempor",
                        "incididunt" },
                {
                        "ipsum",
                        "irure",
                        "occaecat",
                        "enim",
                        "laborum",
                        "reprehenderit" },
                {
                        "dolor",
                        "fugiat",
                        "nulla",
                        "reprehenderit",
                        "laborum",
                        "consequat" },
                {
                        "sit",
                        "consequat",
                        "laborum",
                        "fugiat",
                        "eiusmod",
                        "enim" },
                {
                        "amet",
                        "nulla",
                        "Excepteur",
                        "voluptate",
                        "occaecat",
                        "et" },
                {
                        "consectetur",
                        "occaecat",
                        "fugiat",
                        "dolore",
                        "consequat",
                        "eiusmod" },
                {
                        "adipisicing",
                        "fugiat",
                        "Excepteur",
                        "occaecat",
                        "fugiat",
                        "laborum" },
                {
                        "elit",
                        "voluptate",
                        "reprehenderit",
                        "Excepteur",
                        "fugiat",
                        "nulla" },
        });
        tableFixHeaders.setAdapter(matrixTableAdapter);


        return v;
    }
}
