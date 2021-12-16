package com.example.air_monitoring_bk.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.air_monitoring_bk.JsonData;
import com.example.air_monitoring_bk.MainActivity;
import com.example.air_monitoring_bk.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Report#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Report extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Report() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Report.
     */
    // TODO: Rename and change types and number of parameters
    public static Report newInstance(String param1, String param2) {
        Report fragment = new Report();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_report, container, false);
        Button sendData = view.findViewById(R.id.send_button);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int AQI =  10 + (int)( Math.random() * (300-10));
                int PM25 = 10 + (int)( Math.random() * (300-10));
                int PM10 = 10 + (int)( Math.random() * (300-10));
                int CO2 = 10 + (int)( Math.random() * (300-10));
                int SO2 = 10 + (int)( Math.random() * (300-10));
                int NO2 = 10 + (int)( Math.random() * (300-10));
                JsonData data = new JsonData("TP.HCM", AQI, PM25, PM10, CO2, SO2, NO2);
                MainActivity.sendDataToMQTT(data.toString());
            }
        });
        return view;
    }
}