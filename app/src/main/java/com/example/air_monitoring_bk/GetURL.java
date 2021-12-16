package com.example.air_monitoring_bk;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.air_monitoring_bk.User.AQI_Fragment;
import com.example.air_monitoring_bk.User.Parameter;
import com.example.air_monitoring_bk.User.Parameter_Fragment;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetURL extends AsyncTask<String,String,String> {

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();

    Context c;

    ArrayList<JsonData> listHCM;
    public int startPoint = 0;
    public int numberOfHistoricData = 12;

    @Override
    protected String doInBackground(String... strings) {
        Request.Builder builder = new Request.Builder();
        builder.url("https://io.adafruit.com/api/v2/NDDIoT/feeds/air-monitoring/data");
        Request request = builder.build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Toast.makeText(c,"Connect failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if(!s.equals("")){
            listHCM = new ArrayList<JsonData>();
            try {
                JSONArray JA = new JSONArray(s);
                for (int i = 0; i<JA.length(); i++){
                    // Handle JsonObject
                    JSONObject JO = JA.getJSONObject(i);
                    String val = JO.getString("value");

                    while (val.indexOf('/')>0){
                        val = val.substring(0,val.indexOf('/'))
                                + val.substring(val.indexOf('/') + 1);
                    }

                    JSONObject _JO = new JSONObject(val);
                    JsonData obj = new JsonData();
                    obj.place = _JO.getString("place");
                    obj.AQI = _JO.getInt("AQI");
                    obj.PM25 = _JO.getInt("PM2.5");
                    obj.PM10 = _JO.getInt("PM1.0");
                    obj.CO2 = _JO.getInt("CO2");
                    obj.NO2 = _JO.getInt("NO2");
                    obj.SO2 = _JO.getInt("SO2");

                    if (obj.place.contains("TP.HCM")){
                        listHCM.add(obj);
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            startPoint = listHCM.size() - numberOfHistoricData - 1;
            if (startPoint < 0) startPoint = 0;

            List<Integer> AQI = new ArrayList<>();
            List<Integer> PM25 = new ArrayList<>();
            List<Integer> PM10 = new ArrayList<>();
            List<Integer> CO2 = new ArrayList<>();
            List<Integer> NO2 = new ArrayList<>();
            List<Integer> SO2 = new ArrayList<>();
            for (int k = listHCM.size()-1; k >= 0; k--) {
                AQI.add(listHCM.get(k).AQI);
                PM25.add(listHCM.get(k).PM25);
                PM10.add(listHCM.get(k).PM10);
                CO2.add(listHCM.get(k).CO2);
                NO2.add(listHCM.get(k).NO2);
                SO2.add(listHCM.get(k).SO2);
            }
            MainActivity.AQI = AQI;
            MainActivity.PM25 = PM25;
            MainActivity.PM10 = PM10;
            MainActivity.CO2 = CO2;
            MainActivity.NO2 = NO2;
            MainActivity.SO2 = SO2;


            AQI_Fragment.getListUsers();
            AQI_Fragment.setBarChart();
            AQI_Fragment.SetAQI_UI();
            AQI_Fragment.getListAdvice();
            AQI_Fragment.adviceAdapter.notifyDataSetChanged();
            AQI_Fragment.userAdapter.notifyDataSetChanged();

            Parameter_Fragment.setBarChart();
            Parameter_Fragment.SetSeekbar();


        }
        super.onPostExecute(s);
    }
}
