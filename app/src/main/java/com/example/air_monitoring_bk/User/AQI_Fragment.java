package com.example.air_monitoring_bk.User;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akaita.android.circularseekbar.CircularSeekBar;
import com.example.air_monitoring_bk.Advice.AdviceAdapter;
import com.example.air_monitoring_bk.Advice.AdviceItem;
import com.example.air_monitoring_bk.GetURL;
import com.example.air_monitoring_bk.JsonData;
import com.example.air_monitoring_bk.MainActivity;
import com.example.air_monitoring_bk.Map.MapActivity;
import com.example.air_monitoring_bk.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AQI_Fragment extends Fragment {

    View view;
    public static List<Parameter> ParameterList = new ArrayList<>();
    public static List<AdviceItem> AdviceList = new ArrayList<>();
    public static RecyclerView rcvUser;
    public static RecyclerView rcvAdvice;
    public static float progress = (float) 26;
    public static userAdapter userAdapter;
    public static AdviceAdapter adviceAdapter;
    public static BarChart mpBarChart;
    public static FrameLayout backgroudAQI;

    public static ArrayList<BarEntry> barEntrieGood = new  ArrayList<>();
    public static ArrayList<BarEntry> barEntrieModerate = new  ArrayList<>();
    public static ArrayList<BarEntry> barEntrieSensitive = new  ArrayList<>();
    public static ArrayList<BarEntry> barEntrieUnhealthy = new  ArrayList<>();
    public static ArrayList<BarEntry> barEntrieVeryUn = new  ArrayList<>();
    public static ArrayList<BarEntry> barEntrieHazadous = new  ArrayList<>();

    public static List<Integer>DataAQI = new ArrayList<>();

    public static TextView AQI_index ;
    public static LinearLayout AQI_bg ;
    public static LinearLayout AQI_bgdark;
    public static TextView AQI_affect;
    public static ImageView AQI_face;

    public static TextView texDemo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_aqi, container, false);
        backgroudAQI = view.findViewById(R.id.backgroudAQI);
        DataAQI = getDataJson();
        mpBarChart = view.findViewById(R.id.barchart);
        setBarChart();
        getListUsers();
        getListAdvice();


        //setupBlinkyTimer();
        //setBarChart();

        rcvUser  = (RecyclerView) view.<View>findViewById(R.id.rcv_user);
        userAdapter = new userAdapter(ParameterList);
        //rcvUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvUser.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rcvUser.setAdapter(userAdapter);

        rcvAdvice  = (RecyclerView) view.<View>findViewById(R.id.ListAdvice);
        adviceAdapter = new AdviceAdapter(AdviceList);
        rcvAdvice.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rcvAdvice.setAdapter(adviceAdapter);

        AQI_index = view.findViewById(R.id.AQI_index);
        AQI_bg = view.findViewById(R.id.AQI_bg);
        AQI_bgdark = view.findViewById(R.id.AQI_bgdark);
        AQI_affect = view.findViewById(R.id.AQI_affect);
        AQI_face = view.findViewById(R.id.AQI_face);

        SetAQI_UI();

        /* Hide Show Parameters or Chart*/
        Button AdviceButton = view.findViewById(R.id.AdviceBtn);
        Button ParameterButton = view.findViewById(R.id.ParamenterBtn);
        Button ChartButton = view.findViewById(R.id.ChartBtn);
        Button HourButton = view.findViewById(R.id.HourBtn);
        Button DayButton = view.findViewById(R.id.DayBtn);
        HourButton.setBackgroundResource(R.drawable.item_comer);
        HourButton.setTextColor(Color.BLACK);
        DayButton.setBackgroundResource(R.drawable.bg_gray_comer_16);
        DayButton.setTextColor(Color.WHITE);

        rcvAdvice.setVisibility(View.VISIBLE);
        rcvUser.setVisibility(View.GONE);
        mpBarChart.setVisibility(View.GONE);

        AdviceButton.setBackgroundResource(R.drawable.item_comer);
        AdviceButton.setTextColor(Color.BLACK);
        ParameterButton.setBackgroundResource(R.drawable.bg_gray_comer_16);
        ParameterButton.setTextColor(Color.WHITE);
        ChartButton.setBackgroundResource(R.drawable.bg_gray_comer_16);
        ChartButton.setTextColor(Color.WHITE);
        HourButton.setVisibility(View.GONE);
        DayButton.setVisibility(View.GONE);

        AdviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcvAdvice.setVisibility(View.VISIBLE);
                rcvUser.setVisibility(View.GONE);
                mpBarChart.setVisibility(View.GONE);
                HourButton.setVisibility(View.GONE);
                DayButton.setVisibility(View.GONE);
                AdviceButton.setBackgroundResource(R.drawable.item_comer);
                AdviceButton.setTextColor(Color.BLACK);
                ParameterButton.setBackgroundResource(R.drawable.bg_gray_comer_16);
                ParameterButton.setTextColor(Color.WHITE);
                ChartButton.setBackgroundResource(R.drawable.bg_gray_comer_16);
                ChartButton.setTextColor(Color.WHITE);
            }
        });

        ParameterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAdapter = new userAdapter(ParameterList);
                //rcvUser.setLayoutManager(new LinearLayoutManager(getActivity()));
                rcvUser.setLayoutManager(new GridLayoutManager(getActivity(),2));
                rcvUser.setAdapter(userAdapter);

                rcvAdvice.setVisibility(View.GONE);
                rcvUser.setVisibility(View.VISIBLE);
                mpBarChart.setVisibility(View.GONE);
                HourButton.setVisibility(View.GONE);
                DayButton.setVisibility(View.GONE);
                AdviceButton.setBackgroundResource(R.drawable.bg_gray_comer_16);
                AdviceButton.setTextColor(Color.WHITE);
                ParameterButton.setBackgroundResource(R.drawable.item_comer);
                ParameterButton.setTextColor(Color.BLACK);
                ChartButton.setBackgroundResource(R.drawable.bg_gray_comer_16);
                ChartButton.setTextColor(Color.WHITE);
            }
        });

        ChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcvAdvice.setVisibility(View.GONE);
                rcvUser.setVisibility(View.GONE);
                mpBarChart.setVisibility(View.VISIBLE);
                HourButton.setVisibility(View.VISIBLE);
                DayButton.setVisibility(View.VISIBLE);
                AdviceButton.setBackgroundResource(R.drawable.bg_gray_comer_16);
                AdviceButton.setTextColor(Color.WHITE);
                ParameterButton.setBackgroundResource(R.drawable.bg_gray_comer_16);
                ParameterButton.setTextColor(Color.WHITE);
                ChartButton.setBackgroundResource(R.drawable.item_comer);
                ChartButton.setTextColor(Color.BLACK);

            }
        });

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

        return  view;
    }

    public static void getListUsers() {
        List<Parameter> list  = new ArrayList<>();
        for (int i = 1; i< MainActivity.Parameter_ListChecked.size(); i++){
            String name = MainActivity.Parameter_ListChecked.get(i).getName();
            String value;
            switch (name){
                case "PM2.5":
                    value =MainActivity.PM25.get(MainActivity.PM25.size()-1).toString();
                    break;
                case "PM1.0":
                    value =  MainActivity.PM10.get(MainActivity.PM10.size()-1).toString();
                    break;
                case "CO2":
                    value = MainActivity.CO2.get(MainActivity.CO2.size()-1).toString();
                    break;
                case "SO2":
                    value = MainActivity.SO2.get(MainActivity.SO2.size()-1).toString();
                    break;
                case "NO2":
                    value = MainActivity.NO2.get(MainActivity.NO2.size()-1).toString();
                    break;
                default:
                    value = "0";
                    break;
            }

            list.add(new Parameter( name, value));
        }


//        list.add(new Parameter( "PM2.5", MainActivity.PM25.get(MainActivity.PM25.size()-1).toString()));
//        list.add(new Parameter( "PM1.0", MainActivity.PM10.get(MainActivity.PM10.size()-1).toString()));
//        list.add(new Parameter( "CO2", MainActivity.CO2.get(MainActivity.CO2.size()-1).toString()));
//        list.add(new Parameter( "SO2", MainActivity.SO2.get(MainActivity.SO2.size()-1).toString()));
//        list.add(new Parameter( "NO2", MainActivity.NO2.get(MainActivity.NO2.size()-1).toString()));
        ParameterList = list;
    }

    public static void getListAdvice(){
        List<AdviceItem> list = new ArrayList<>();
        String cycling;
        String open_window;
        String facemask;
        String air_filter;
        Integer AQI = MainActivity.AQI.get(MainActivity.AQI.size()-1);
        if(AQI <51){
            cycling = "Enjoy outdoor activities";
            open_window = "Open your windows to bring clean and fresh air indoor";
            facemask = "Sensitive groups should wear a mask outdoors";
            air_filter = "Run an air purifier";
        }else if(AQI < 101){
            cycling = "Sensitive groups should reduce outdoor exercise";
            open_window = "Close your window to avoid dirty outdoor air";
            facemask = "Sensitive groups should wear a mask outdoors";
            air_filter = "Run an air purifier";
        }else if(AQI < 151) {
            cycling = "Everyone should reduce outdoor exercise";
            open_window ="Close your window to avoid dirty outdoor air";
            facemask = "Sensitive groups should wear a mask outdoors";
            air_filter ="Run an air purifier";
        }else{
            cycling = "Avoid outdoor exercise";
            open_window = "Close your window to avoid dirty outdoor air";
            facemask = "Wear a mask outdoors";
            air_filter = "Run an air purifier";
        }
        list.add(new AdviceItem(R.drawable.cycling,cycling));
        list.add(new AdviceItem(R.drawable.open_window,open_window));
        list.add(new AdviceItem(R.drawable.facemask,facemask));
        list.add(new AdviceItem(R.drawable.air_filter,air_filter));
        AdviceList = list;

    }

    public List<Integer> getDataJson(){
        List<Integer> list = new ArrayList<>();
        list.add(0);
        return list;
    }

    public static void SetAQI_UI(){
        AQI_index.setText((DataAQI.get(DataAQI.size()-1)).toString());
        progress = (float) DataAQI.get(DataAQI.size()-1);
        if(progress<51){
            AQI_bg.setBackgroundResource(R.drawable.bg_green_comer_16);
            AQI_affect.setText("Good");
            AQI_face.setImageResource(R.drawable.green);
            backgroudAQI.setBackgroundResource(R.drawable.background_good);
        } else if(progress <101){
            AQI_bg.setBackgroundResource(R.drawable.bg_yellow_comer_16);
            AQI_affect.setText("Medium");
            AQI_face.setImageResource(R.drawable.yellow);
            backgroudAQI.setBackgroundResource(R.drawable.background_moderate);
        } else if(progress <151){
            AQI_bg.setBackgroundResource(R.drawable.bg_orange_comer_16);
            AQI_affect.setText("Poor");
            AQI_face.setImageResource(R.drawable.orange);
            backgroudAQI.setBackgroundResource(R.drawable.background_unhealthyforsensitiveskin);
        } else if(progress <201){
            AQI_bg.setBackgroundResource(R.drawable.bg_red_comer_16);
            AQI_affect.setText("Bad");
            AQI_face.setImageResource(R.drawable.red);
            backgroudAQI.setBackgroundResource(R.drawable.background_unhealthy);
        }else if(progress <301){
            AQI_bg.setBackgroundResource(R.drawable.bg_violet_comer_16);
            AQI_affect.setText("Very Bad");
            AQI_face.setImageResource(R.drawable.violet);
            backgroudAQI.setBackgroundResource(R.drawable.background_veryunhealthy);
        }else {
            AQI_bg.setBackgroundResource(R.drawable.bg_brown_comer_16);
            AQI_affect.setText("Dangerous");
            AQI_face.setImageResource(R.drawable.brown);
            backgroudAQI.setBackgroundResource(R.drawable.background_hazardous);
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
        DataAQI = MainActivity.AQI;
        getListUsers();
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