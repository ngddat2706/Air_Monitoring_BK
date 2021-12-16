package com.example.air_monitoring_bk.Admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.air_monitoring_bk.Admin0.AdminItem;
import com.example.air_monitoring_bk.MainActivity;
import com.example.air_monitoring_bk.R;
import com.example.air_monitoring_bk.Station.ExpandableListViewAdapter;
import com.example.air_monitoring_bk.Station.ParameterItem;
import com.example.air_monitoring_bk.Station.StationItem;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Station extends Fragment {

    private View view;
    public static List<StationItem> mListStation;
    public static Map<StationItem, List<ParameterItem>> mListItems;
    private ImageView editStation;
    public ImageView addStation;

    private ExpandableListView expandableListView;
    public static ExpandableListViewAdapter expandableListViewAdapter;

    public static Boolean editStationCheck = false;

    public Station() {
        // Required empty public constructor
    }

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_station, container, false);
        expandableListView = view.findViewById(R.id.expandableListView);
        addStation = view.findViewById(R.id.add_station_btn);
        editStation = view.findViewById(R.id.edit_station_btn);

        mListItems = MainActivity.mListItemsChecked;
        mListStation = MainActivity.mListStationChecked;
        expandableListViewAdapter = new ExpandableListViewAdapter(mListStation,mListItems);
        expandableListView.setAdapter(expandableListViewAdapter);

        editStationCheck = false;
        addStation.setVisibility(View.GONE);
        editStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStationCheck = !editStationCheck;
                if(editStationCheck){
                    editStation.setImageResource(R.drawable.done_icon);
                    addStation.setVisibility(View.VISIBLE);
                    expandableListViewAdapter = new ExpandableListViewAdapter(MainActivity.mListStation,MainActivity.mListItems);
                    expandableListView.setAdapter(expandableListViewAdapter);
                }else {
                    editStation.setImageResource(R.drawable.edit_icon);
                    addStation.setVisibility(View.GONE);
                    mListItems = getmListItemsChecked();
                    mListStation = new  ArrayList<>(mListItems.keySet());

                    MainActivity.mListItemsChecked = mListItems;
                    MainActivity.mListStationChecked = mListStation;
                    MainActivity.Address_List = MainActivity.getAddress_ListChecked();

                    expandableListViewAdapter = new ExpandableListViewAdapter(mListStation,mListItems);
                    expandableListView.setAdapter(expandableListViewAdapter);
                }
            }
        });

        addStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder stationDialog = new AlertDialog.Builder(v.getContext());
//                stationDialog.setTitle("Create a new station");
//                stationDialog.setMessage("Name of the new station:");
//                final EditText stationName = new EditText(v.getContext());
//                stationName.setInputType(InputType.TYPE_CLASS_TEXT);
//                stationDialog.setView(stationName);
//                stationDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String NameStation = stationName.getText().toString();
//                        if(TextUtils.isEmpty(NameStation)){
//                            Toast.makeText(getActivity(), "The name of the station is empty", Toast.LENGTH_SHORT).show();
//                        }else {
//                            if (ContainStation(NameStation)){
//                                Toast.makeText(getActivity(), "Station Already Exists", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                //AdminItem adminItem = new AdminItem(MainActivity.Admin_List.size(), email,password);
//                                AddStation(NameStation);
//                                Toast.makeText(getActivity(), "Successfully", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//                stationDialog.setNegativeButton("Cancel", null);
//                stationDialog.show();
                openFeedbackDialog();
            }
        });
        return view;
    }


    public Map<StationItem, List<ParameterItem>> getmListItemsChecked(){
        Map<StationItem, List<ParameterItem>> listMap = new HashMap<>();
        for (int i = 0; i< MainActivity.mListStation.size(); i++){
            if(MainActivity.mListStation.get(i).isCheckbox()){
                StationItem stationItem = MainActivity.mListStation.get(i);
                List<ParameterItem> parameterItem = new ArrayList<>();
                for(int j = 0; j < expandableListViewAdapter.getChildrenCount(i); j++){
                    if(MainActivity.mListItems.get(MainActivity.mListStation.get(i)).get(j).isCheckbox()){
                        parameterItem.add(MainActivity.mListItems.get(MainActivity.mListStation.get(i)).get(j));
                    }
                }
                listMap.put(stationItem,parameterItem);
            }
        }
        return listMap;
    }

    public void onClickPushDataStation(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("my_station");

        myRef.setValue(MainActivity.mListStation, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(view.getContext(),"Push data success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickPushDataParameter(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        for(int i = 0; i<mListStation.size();i++){
            List<ParameterItem> Parameter_List = (List<ParameterItem>) MainActivity.mListItems.get(MainActivity.mListStation.get(0));;
            DatabaseReference myRef = database.getReference("my_parameter/" + i);
            myRef.setValue(Parameter_List, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(view.getContext(),"Push data success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static boolean ContainStation(String name){
        for(int i=0; i<MainActivity.mListStation.size(); i++){
            if(MainActivity.mListStation.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public static void AddStation(String nameStation){
        StationItem stationItem = new StationItem(MainActivity.mListStation.size(), nameStation,true);
        List<ParameterItem> parameterItem = new ArrayList<>();
        for(int j = 0; j< MainActivity.Parameter_List.size(); j++){
            parameterItem.add(new ParameterItem(j, MainActivity.Parameter_List.get(j), true));
        }
        MainActivity.mListItems.put(stationItem,parameterItem);
        MainActivity.mListStation.add(stationItem);
        expandableListViewAdapter.notifyDataSetChanged();
    }

    private void openFeedbackDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_feeback_station);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        TextView NameDialog = dialog.findViewById(R.id.nameDialog);
        NameDialog.setText("Create New Station");
        TextInputEditText stationName = dialog.findViewById(R.id.nameStation);
        Button Cancelbtn = dialog.findViewById(R.id.cancel_button);
        Button Sendbtn = dialog.findViewById(R.id.send_button);

        Cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NameStation = stationName.getText().toString();
                if(TextUtils.isEmpty(NameStation)){
                    Toast.makeText(getActivity(), "The name of the station is empty", Toast.LENGTH_SHORT).show();
                }else {
                    if (ContainStation(NameStation)){
                        Toast.makeText(getActivity(), "Station Already Exists", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //AdminItem adminItem = new AdminItem(MainActivity.Admin_List.size(), email,password);
                        AddStation(NameStation);
                        Toast.makeText(getActivity(), "Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}