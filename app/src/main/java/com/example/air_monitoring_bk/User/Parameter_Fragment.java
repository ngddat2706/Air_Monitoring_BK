package com.example.air_monitoring_bk.User;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.akaita.android.circularseekbar.CircularSeekBar;
import com.example.air_monitoring_bk.GetURL;
import com.example.air_monitoring_bk.MainActivity;
import com.example.air_monitoring_bk.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Parameter_Fragment extends Fragment {

    private static final String ARG_PAGE = "arg_page";
    public static CircularSeekBar seekBar;
    public static float progress = (float) 26;
    public static BarChart mpBarChart;
    public static ArrayList<BarEntry> barEntrieGood = new  ArrayList<>();
    public static ArrayList<BarEntry> barEntrieModerate = new  ArrayList<>();
    public static ArrayList<BarEntry> barEntrieSensitive = new  ArrayList<>();
    public static ArrayList<BarEntry> barEntrieUnhealthy = new  ArrayList<>();
    public static ArrayList<BarEntry> barEntrieVeryUn = new  ArrayList<>();
    public static ArrayList<BarEntry> barEntrieHazadous = new  ArrayList<>();
    public static FrameLayout bgParameter;

    public static List<Integer>DataAQI = new ArrayList<>();

    public static  int index =0;

    public Parameter_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Parameter_Fragment newInstance(int pageNumber) {
        Parameter_Fragment fragment = new Parameter_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_parameter, container, false);
        bgParameter = view.findViewById(R.id.bgParameter);
        TextView ParameterName = (TextView)view.findViewById(R.id.ParamenterName);
        ParameterName.setVisibility(View.GONE);
        if (getArguments() != null) {
            ParameterName.setText(MainActivity.Parameter_ListChecked.get(getArguments().getInt(ARG_PAGE)).getName());
            index = getArguments().getInt(ARG_PAGE);
        }

        Button HourButton = view.findViewById(R.id.HourBtn);
        Button DayButton = view.findViewById(R.id.DayBtn);

        HourButton.setBackgroundResource(R.drawable.item_comer);
        HourButton.setTextColor(Color.BLACK);
        DayButton.setBackgroundResource(R.drawable.bg_gray_comer_16);
        DayButton.setTextColor(Color.WHITE);

        mpBarChart = view.findViewById(R.id.barchart);

        setBarChart();

        seekBar = (CircularSeekBar)view.findViewById(R.id.seekbar_paramenter);
        //seekBar.setProgressTextFormat(new DecimalFormat("###,###,##0.00"));
        SetSeekbar();

        HourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpBarChart.animateY(1500);
                HourButton.setBackgroundResource(R.drawable.item_comer);
                HourButton.setTextColor(Color.BLACK);
                DayButton.setBackgroundResource(R.drawable.bg_gray_comer_16);
                DayButton.setTextColor(Color.WHITE);
            }
        });

        DayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpBarChart.animateY(1500);
                DayButton.setBackgroundResource(R.drawable.item_comer);
                DayButton.setTextColor(Color.BLACK);
                HourButton.setBackgroundResource(R.drawable.bg_gray_comer_16);
                HourButton.setTextColor(Color.WHITE);
            }
        });

        return view;
    }

    public static List<Integer> getDataJson(){
        List<Integer> list = new ArrayList<>();
        list.add(0);

        return list;
    }

    public static void SetSeekbar(){
        progress = DataAQI.get(DataAQI.size()-1);
        seekBar.setProgress(progress);
        if(progress<51){
            seekBar.setRingColor(Color.GREEN);
            bgParameter.setBackgroundResource(R.drawable.background_good);
        }
        else if(progress <101) {
            seekBar.setRingColor(Color.YELLOW);
            bgParameter.setBackgroundResource(R.drawable.background_moderate);
        }
        else if(progress <151){
            seekBar.setRingColor(Color.rgb(255,126,0));
            bgParameter.setBackgroundResource(R.drawable.background_unhealthyforsensitiveskin);
        }
        else if(progress <201){
            seekBar.setRingColor(Color.RED);
            bgParameter.setBackgroundResource(R.drawable.background_unhealthy);
        }
        else if(progress <301){
            seekBar.setRingColor(Color.rgb(143,63,151));
            bgParameter.setBackgroundResource(R.drawable.background_veryunhealthy);
        }
        else if(progress <501){
            seekBar.setRingColor(Color.rgb(126,0,35));
            bgParameter.setBackgroundResource(R.drawable.background_hazardous);
        }
    }

    public static void getDataBarEntrie(){
        barEntrieGood = new  ArrayList<>();
        barEntrieModerate = new  ArrayList<>();
        barEntrieSensitive = new  ArrayList<>();
        barEntrieUnhealthy = new  ArrayList<>();
        barEntrieVeryUn = new  ArrayList<>();
        barEntrieHazadous = new  ArrayList<>();
        for (int i =0; i< DataAQI.size(); i++){
            if(DataAQI.get(i) <= 50){
                barEntrieGood.add(new BarEntry(i, DataAQI.get(i)));
            } else if(DataAQI.get(i) <= 100){
                barEntrieModerate.add(new BarEntry(i, DataAQI.get(i)));
            }else if(DataAQI.get(i) <= 150){
                barEntrieSensitive.add(new BarEntry(i, DataAQI.get(i)));
            }else if(DataAQI.get(i) <= 200){
                barEntrieUnhealthy.add(new BarEntry(i, DataAQI.get(i)));
            }else if(DataAQI.get(i) <= 300){
                barEntrieVeryUn.add(new BarEntry(i, DataAQI.get(i)));
            }else if(DataAQI.get(i) <= 500){
                barEntrieHazadous.add(new BarEntry(i, DataAQI.get(i)));
            }
        }
    }

    public static void setBarChart(){

        String name = MainActivity.Parameter_ListChecked.get(index).getName();
        //String value;
        switch (name){
            case "PM2.5":
                DataAQI = MainActivity.PM25;
                progress = DataAQI.get(DataAQI.size()-1);
                break;
            case "PM1.0":
                DataAQI = MainActivity.PM10;
                progress = DataAQI.get(DataAQI.size()-1);
                break;
            case "CO2":
                DataAQI = MainActivity.CO2;
                progress = DataAQI.get(DataAQI.size()-1);
                break;
            case "SO2":
                DataAQI = MainActivity.SO2;
                progress = DataAQI.get(DataAQI.size()-1);
                break;
            case "NO2":
                DataAQI = MainActivity.NO2;
                progress = DataAQI.get(DataAQI.size()-1);
                break;
            default:
                DataAQI = getDataJson();
                progress = 0;
                break;
        }

//        switch (index){
//            case 1:
//                DataAQI = MainActivity.PM25;
//                progress = DataAQI.get(DataAQI.size()-1);
//                break;
//            case 2:
//                DataAQI = MainActivity.PM10;
//                progress = DataAQI.get(DataAQI.size()-1);
//                break;
//            case 3:
//                DataAQI = MainActivity.CO2;
//                progress = DataAQI.get(DataAQI.size()-1);
//                break;
//            case 4:
//                DataAQI = MainActivity.SO2;
//                progress = DataAQI.get(DataAQI.size()-1);
//                break;
//            case 5:
//                DataAQI = MainActivity.NO2;
//                progress = DataAQI.get(DataAQI.size()-1);
//                break;
//            default:
//                break;
//        }

        getDataBarEntrie();

        BarDataSet barDataSetGood = new BarDataSet(barEntrieGood, "Good");
        barDataSetGood.setColors(Color.GREEN);

        BarDataSet barDataSetModerate = new BarDataSet(barEntrieModerate, "Moderate");
        barDataSetModerate.setColors(Color.YELLOW);

        BarDataSet barDataSetSensitive = new BarDataSet(barEntrieSensitive, "Unhealthy For Sensitive Groups");
        barDataSetSensitive.setColors(Color.rgb(255,126,0));

        BarDataSet barDataSetUnhealthy = new BarDataSet(barEntrieUnhealthy, "Unhealthy");
        barDataSetUnhealthy.setColors(Color.RED);

        BarDataSet barDataSetVeryUn = new BarDataSet(barEntrieVeryUn, "Very Unhealthy");
        barDataSetVeryUn.setColors(Color.rgb(143,63,151));

        BarDataSet barDataSetHazadous = new BarDataSet(barEntrieHazadous, "Hazardous");
        barDataSetHazadous.setColors(Color.rgb(126,0,35));

        BarData barData = new BarData(barDataSetGood, barDataSetModerate, barDataSetSensitive,
                barDataSetUnhealthy, barDataSetVeryUn, barDataSetHazadous);
        barData.setBarWidth(0.9f);
        barData.setValueTextSize(10f);

        mpBarChart.setData(barData);
        mpBarChart.setFitBars(false);
        mpBarChart.getDescription().setEnabled(false);
        mpBarChart.getAxisRight().setEnabled(false);
        mpBarChart.animateY(2000);

        XAxis xAxis = mpBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }



}