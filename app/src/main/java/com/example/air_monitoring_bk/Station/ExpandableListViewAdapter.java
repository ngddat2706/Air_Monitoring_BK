package com.example.air_monitoring_bk.Station;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.air_monitoring_bk.Admin.Login;
import com.example.air_monitoring_bk.Admin.Station;
import com.example.air_monitoring_bk.Admin0.AdminItem;
import com.example.air_monitoring_bk.MainActivity;
import com.example.air_monitoring_bk.Map.MapActivity;
import com.example.air_monitoring_bk.R;
import com.example.air_monitoring_bk.User.Parameter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private List<StationItem> mListStation;
    private Map<StationItem, List<ParameterItem>> mListItems;
    public CheckBox checkBoxStation;
    public ImageView EditStation;
    public ImageView DelStation;
    public CheckBox checkBoxParameter;
    public ImageView EditParameter;
    public ImageView DelParameter;
    public ImageView AddParameter;

    public ExpandableListViewAdapter(List<StationItem> mListStation, Map<StationItem, List<ParameterItem>> mListItems) {
        this.mListStation = mListStation;
        this.mListItems = mListItems;
    }

    @Override
    public int getGroupCount() {
        if(mListStation != null){
            return mListStation.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(mListStation.size() > 0 && mListItems.size() > 0){
            return mListItems.get(mListStation.get(groupPosition)).size();
        }
        return 0;
//        return MainActivity.list_parameterItemlists.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListStation.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListItems.get(mListStation.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        StationItem stationItem = mListStation.get(groupPosition);
        return stationItem.getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        ParameterItem parameterItem = mListItems.get(mListStation.get(groupPosition)).get(childPosition);
        return parameterItem.getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_station,parent,false);
        }
        checkBoxStation = convertView.findViewById(R.id.checkbox_station);
        EditStation = convertView.findViewById(R.id.EditStation);
        DelStation = convertView.findViewById(R.id.DeleteStation);
        AddParameter = convertView.findViewById(R.id.AddParameter);
        TextView tvStationName = convertView.findViewById(R.id.tv_station_name);

        StationItem stationItem = mListStation.get(groupPosition);

        tvStationName.setText(stationItem.getName().toUpperCase());
        checkBoxStation.setTag(groupPosition);

        if(stationItem.isCheckbox()){
            checkBoxStation.setChecked(true);
        }else {
            checkBoxStation.setChecked(false);
        }

        if(Station.editStationCheck){
            checkBoxStation.setVisibility(View.VISIBLE);
            EditStation.setVisibility(View.VISIBLE);
            DelStation.setVisibility(View.VISIBLE);
            AddParameter.setVisibility(View.VISIBLE);
        }else {
            checkBoxStation.setVisibility(View.GONE);
            EditStation.setVisibility(View.GONE);
            DelStation.setVisibility(View.GONE);
            AddParameter.setVisibility(View.GONE);
        }

        checkBoxStation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int)buttonView.getTag();
                MainActivity.mListStation.get(position).setCheckbox(isChecked);
            }
        });

        DelStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this station")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.mListStation.remove(groupPosition);
                                Station.expandableListViewAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        EditStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder stationDialog = new AlertDialog.Builder(v.getContext());
//                stationDialog.setTitle("Edit the name of the station");
//                stationDialog.setMessage("New name of the station:");
//                final EditText stationName = new EditText(v.getContext());
//                stationName.setInputType(InputType.TYPE_CLASS_TEXT);
//                stationName.setText(MainActivity.mListStation.get(groupPosition).getName());
//                stationDialog.setView(stationName);
//                stationDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String NameStation = stationName.getText().toString();
//                        if(TextUtils.isEmpty(NameStation)){
//                            Toast.makeText(v.getContext(), "The name of the station is empty", Toast.LENGTH_SHORT).show();
//                        }else {
//                            if (Station.ContainStation(NameStation)){
//                                Toast.makeText(v.getContext(), "Station Already Exists", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                MainActivity.mListStation.get(groupPosition).setName(NameStation);
//                                Station.expandableListViewAdapter.notifyDataSetChanged();
//                                Toast.makeText(v.getContext(), "Successfully", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//                stationDialog.setNegativeButton("Cancel", null);
//                stationDialog.show();
                DialogEditStation(v,groupPosition);
            }
        });

        AddParameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder stationDialog = new AlertDialog.Builder(v.getContext());
//                stationDialog.setTitle("Create a new parameter");
//                stationDialog.setMessage("Name of the new parameter:");
//                final EditText parameterName = new EditText(v.getContext());
//                parameterName.setInputType(InputType.TYPE_CLASS_TEXT);
//                stationDialog.setView(parameterName);
//                stationDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String NameParameter = parameterName.getText().toString();
//                        if(TextUtils.isEmpty(NameParameter)){
//                            Toast.makeText(v.getContext(), "The name of the parameter is empty", Toast.LENGTH_SHORT).show();
//                        }else {
//                            if (ContainParameter(NameParameter,groupPosition)){
//                                Toast.makeText(v.getContext(), "Parameter Already Exists", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                int size =  MainActivity.mListItems.get(mListStation.get(groupPosition)).size();
//                                ParameterItem parameterItem = new ParameterItem(size, NameParameter, true);
//                                MainActivity.mListItems.get(mListStation.get(groupPosition)).add(parameterItem);
//                                Station.expandableListViewAdapter.notifyDataSetChanged();
//                                Toast.makeText(v.getContext(), "Successfully", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//                stationDialog.setNegativeButton("Cancel", null);
//                stationDialog.show();
                DialogAddParameter(v,groupPosition);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_parameter,parent,false);
        }
        checkBoxParameter = convertView.findViewById(R.id.checkbox_parameter);
        EditParameter = convertView.findViewById(R.id.EditParameter);
        DelParameter = convertView.findViewById(R.id.DeleteParameter);
        TextView tvParameterName = convertView.findViewById(R.id.tv_parameter_name);

        ParameterItem parameterItem = mListItems.get(mListStation.get(groupPosition)).get(childPosition);
        //ParameterItem parameterItem = MainActivity.list_parameterItemlists.get(groupPosition).get(childPosition);
        tvParameterName.setText(parameterItem.getName());
        checkBoxParameter.setTag(childPosition);

        if(parameterItem.isCheckbox()){
            checkBoxParameter.setChecked(true);
        }else {
            checkBoxParameter.setChecked(false);
        }

        if(childPosition != 0){
            if(Station.editStationCheck){
                checkBoxParameter.setVisibility(View.VISIBLE);
                EditParameter.setVisibility(View.VISIBLE);
                DelParameter.setVisibility(View.VISIBLE);
            }else {
                checkBoxParameter.setVisibility(View.GONE);
                EditParameter.setVisibility(View.GONE);
                DelParameter.setVisibility(View.GONE);
            }
        }else {
            checkBoxParameter.setVisibility(View.GONE);
            EditParameter.setVisibility(View.GONE);
            DelParameter.setVisibility(View.GONE);
        }

        checkBoxParameter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int)buttonView.getTag();
                MainActivity.mListItems.get(mListStation.get(groupPosition)).get(position).setCheckbox(isChecked);

            }
        });

        EditParameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder stationDialog = new AlertDialog.Builder(v.getContext());
//                stationDialog.setTitle("Edit the name of the parameter");
//                stationDialog.setMessage("New name of the parameter:");
//                final EditText parameterName = new EditText(v.getContext());
//                parameterName.setInputType(InputType.TYPE_CLASS_TEXT);
//                parameterName.setText(MainActivity.mListItems.get(mListStation.get(groupPosition)).get(childPosition).getName());
//                stationDialog.setView(parameterName);
//                stationDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String NameParameter = parameterName.getText().toString();
//                        if(TextUtils.isEmpty(NameParameter)){
//                            Toast.makeText(v.getContext(), "The name of the parameter is empty", Toast.LENGTH_SHORT).show();
//                        }else {
//                            if (ContainParameter(NameParameter,groupPosition)){
//                                Toast.makeText(v.getContext(), "Parameter Already Exists", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                MainActivity.mListItems.get(mListStation.get(groupPosition)).get(childPosition).setName(NameParameter);
//                                Station.expandableListViewAdapter.notifyDataSetChanged();
//                                Toast.makeText(v.getContext(), "Successfully", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//                stationDialog.setNegativeButton("Cancel", null);
//                stationDialog.show();
                DialogEditParameter(v,childPosition,groupPosition);
            }
        });

        DelParameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this parameter")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.mListItems.get(mListStation.get(groupPosition)).remove(childPosition);
                                Station.expandableListViewAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static boolean ContainParameter(String name, int Position){
        for(int i=0; i<MainActivity.mListItems.get(MainActivity.mListStation.get(Position)).size(); i++){
            if(MainActivity.mListItems.get(MainActivity.mListStation.get(Position)).get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    private void DialogEditStation(View view, int groupPosition) {
        final Dialog dialog = new Dialog(view.getContext());
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
        NameDialog.setText("Edit Station");
        TextInputEditText stationName = dialog.findViewById(R.id.nameStation);
        stationName.setText(MainActivity.mListStation.get(groupPosition).getName());
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
                    Toast.makeText(v.getContext(), "The name of the station is empty", Toast.LENGTH_SHORT).show();
                }else {
                    if (Station.ContainStation(NameStation)){
                        Toast.makeText(v.getContext(), "Station Already Exists", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        MainActivity.mListStation.get(groupPosition).setName(NameStation);
                        Station.expandableListViewAdapter.notifyDataSetChanged();
                        Toast.makeText(v.getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void DialogAddParameter(View view, int groupPosition) {
        final Dialog dialog = new Dialog(view.getContext());
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
        NameDialog.setText("Add New Parameter");
        TextInputEditText parameterName = dialog.findViewById(R.id.nameStation);
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
                String NameParameter = parameterName.getText().toString();
                if(TextUtils.isEmpty(NameParameter)){
                    Toast.makeText(v.getContext(), "The name of the parameter is empty", Toast.LENGTH_SHORT).show();
                }else {
                    if (ContainParameter(NameParameter,groupPosition)){
                        Toast.makeText(v.getContext(), "Parameter Already Exists", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int size =  MainActivity.mListItems.get(mListStation.get(groupPosition)).size();
                        ParameterItem parameterItem = new ParameterItem(size, NameParameter, true);
                        MainActivity.mListItems.get(mListStation.get(groupPosition)).add(parameterItem);
                        Station.expandableListViewAdapter.notifyDataSetChanged();
                        Toast.makeText(v.getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void DialogEditParameter(View view, int childPosition, int groupPosition) {
        final Dialog dialog = new Dialog(view.getContext());
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
        NameDialog.setText("Edit Parameter");
        TextInputEditText parameterName = dialog.findViewById(R.id.nameStation);
        parameterName.setText(MainActivity.mListItems.get(mListStation.get(groupPosition)).get(childPosition).getName());
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
                String NameParameter = parameterName.getText().toString();
                if(TextUtils.isEmpty(NameParameter)){
                    Toast.makeText(v.getContext(), "The name of the parameter is empty", Toast.LENGTH_SHORT).show();
                }else {
                    if (ContainParameter(NameParameter,groupPosition)){
                        Toast.makeText(v.getContext(), "Parameter Already Exists", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        MainActivity.mListItems.get(mListStation.get(groupPosition)).get(childPosition).setName(NameParameter);
                        Station.expandableListViewAdapter.notifyDataSetChanged();
                        Toast.makeText(v.getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
