package com.example.air_monitoring_bk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.air_monitoring_bk.Admin.Admin;
import com.example.air_monitoring_bk.Admin.Home;
import com.example.air_monitoring_bk.Admin.Information;
import com.example.air_monitoring_bk.Admin.Login;
import com.example.air_monitoring_bk.Admin.Report;
import com.example.air_monitoring_bk.Admin.Station;
import com.example.air_monitoring_bk.Admin0.AdminItem;
import com.example.air_monitoring_bk.Map.MapActivity;
import com.example.air_monitoring_bk.Station.ParameterItem;
import com.example.air_monitoring_bk.Station.StationItem;
import com.example.air_monitoring_bk.User.AQI_Fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    LinearLayout locationSearch;
    Button buttonMap;
    ProgressDialog progressDialog;
    private TextView Adminname;
    private TextView Adminemail;
    private ImageView Admin_image;
    public static FirebaseAuth mAuth;
    public AutoCompleteTextView autoCompleteTextView;
    public ArrayAdapter arrayAdapter;

    /*Khai báo các trạng thái của fragment xuất hiện trên activity_main.xml*/
    private  static  final int FRAGMENT_HOME= 0;
    private  static  final int FRAGMENT_REPORT= 1;
    private  static  final int FRAGMENT_STATION= 2;
    private  static  final int FRAGMENT_ADMIN= 3;
    private  static  final int FRAGMENT_LOGOUT= 4;
    private  static  final int FRAGMENT_LOGIN= 5;
    private  static  final int FRAGMENT_INFO= 6;
    /*Khai báo Fragment hiện tại là Fragment Home sẽ xuất hiện đầu tiên khi app khởi động*/
    private  int mCurrentFragment = FRAGMENT_HOME;

    /* Khai báo biến lưu trữ danh sách tên các địa chỉ*/
    public static List<String> Address_List = new ArrayList<>();
    /* Khai báo biến lưu trữ danh sách tên các thông số*/
    public static List<String> Parameter_List = new ArrayList<>();

    public static List<ParameterItem> mParameterItems = new ArrayList<>();
    public static List<ParameterItem> Parameter_ListChecked = new ArrayList<>();
    public static List<List<ParameterItem>> list_parameterItemlists = new ArrayList<>();

    public static List<StationItem> mListStation = new ArrayList<>();
    public static List<StationItem> mListStationChecked = new ArrayList<>();

    public static Map<StationItem, List<ParameterItem>> mListItems = new HashMap<>();
    public static Map<StationItem, List<ParameterItem>> mListItemsChecked = new HashMap<>();

    //public static List<String> Address_ListChecked = new ArrayList<>();

    /* Khai báo biến lưu trữ danh sách các tài khoản*/
    public static List<AdminItem> Admin_List = new ArrayList<>();

    // Khai báo MQTT
    public static MQTTHelper mqttHelper;

    public static List<Integer> AQI = new ArrayList<>();
    public static List<Integer> PM25 = new ArrayList<>();
    public static List<Integer> PM10 = new ArrayList<>();
    public static List<Integer> CO2 = new ArrayList<>();
    public static List<Integer> NO2 = new ArrayList<>();
    public static List<Integer> SO2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawe_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        autoCompleteTextView = findViewById(R.id.autoCompleteText);
        locationSearch = findViewById(R.id.autoComplete);
        buttonMap = findViewById(R.id.map_btn);
        mAuth = FirebaseAuth.getInstance();
        Adminname = navigationView.getHeaderView(0).findViewById(R.id.Admin_name);
        Adminemail = navigationView.getHeaderView(0).findViewById(R.id.Admin_email);
        Admin_image = navigationView.getHeaderView(0).findViewById(R.id.imageAdmin);
        progressDialog = new ProgressDialog(this);

        /* Gắn địa chỉ vào danh sách địa chỉ và thực hiện các hàm hỗ trợ để hiện thị trên danh sách dropdown menu*/
        //Address_List = getAddress_List();
        //Parameter_List = getParameter_List();
        //mListItems = getListItems();

//        mParameterItems = new ArrayList<>();
//        mListStation = new ArrayList<>();
//        onClickGetDataStation();
//        mListItems = new HashMap<>();
//        mListItems = onClickGetDataListItems();
//        mListItemsChecked = mListItems;
//        mListStationChecked= mListStation;
//        list_parameterItemlists = new ArrayList<>();
//        onClickGetDataParameterList();

        Parameter_List = getParameter_List();
        Address_List = getAddress_List();
        mListItems = getListItems();
        mListItemsChecked = mListItems;
        mListStation = new  ArrayList<>(mListItems.keySet());
        mListStationChecked = mListStation;
        Parameter_ListChecked = (List<ParameterItem>) mListItems.get(mListStation.get(0));

        Admin_List = new ArrayList<>();
        onClickGetDataAdminList();


        //Address_ListChecked = getAddress_ListChecked();
        Address_List = getAddress_ListChecked();
        arrayAdapter = new ArrayAdapter(this, R.layout.option_item, Address_List);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Parameter_ListChecked = (List<ParameterItem>) mListItemsChecked.get(mListStationChecked.get(position));
                replaceFragment(new Home());
            }
        });

//        Parameter_List = getParameter_List();
//        Address_List = getAddress_List();
//        mListItems = getListItems();
//        mListItemsChecked = mListItems;
//        mListStation = new  ArrayList<>(mListItems.keySet());
//        mListStationChecked = mListStation;
//        Parameter_ListChecked = (List<ParameterItem>) mListItems.get(mListStation.get(0));
//        Admin_List = new ArrayList<>();
//        onClickGetDataAdminList();

        new GetURL().execute();
        startMQTT();
        AQI = getDataJson();
        PM25 = getDataJson();
        PM10 = getDataJson();
        CO2 = getDataJson();
        SO2 = getDataJson();
        NO2 = getDataJson();

        //setupBlinkyTimer();


        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int AQI =  10 + (int)( Math.random() * (300-10));
//                int PM25 = 10 + (int)( Math.random() * (300-10));
//                int PM10 = 10 + (int)( Math.random() * (300-10));
//                int CO2 = 10 + (int)( Math.random() * (300-10));
//                int SO2 = 10 + (int)( Math.random() * (300-10));
//                int NO2 = 10 + (int)( Math.random() * (300-10));
//                JsonData data = new JsonData("TP.HCM", AQI, PM25, PM10, CO2, SO2, NO2);
//                sendDataToMQTT(data.toString());
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });

        /* Các hỗ trợ cho bar menu*/
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new Home());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        showUserInformaiton();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mUser;
        mUser = mAuth.getCurrentUser();
        if(mUser == null){
            HideBarMenu();
            if(!Login.checkLogin){
                HideBarMenu();
            }else{
                ShowBarMenu();
            }
        }else{
            ShowBarMenu();
        }


    }

    /* Thực hiện hàm lựa chọn ra Fragment mỗi khi click vào các item trên bar menu*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            locationSearch.setVisibility(View.VISIBLE);
            buttonMap.setVisibility(View.VISIBLE);
            arrayAdapter = new ArrayAdapter(this, R.layout.option_item, Address_List);
            autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
            autoCompleteTextView.setAdapter(arrayAdapter);
            Parameter_ListChecked = (List<ParameterItem>) mListItemsChecked.get(mListStationChecked.get(0));
            if (mCurrentFragment != FRAGMENT_HOME){
                arrayAdapter.notifyDataSetChanged();
                replaceFragment(new Home());
                mCurrentFragment = FRAGMENT_HOME;
            }
        }else if (id == R.id.nav_report){
            locationSearch.setVisibility(View.GONE);
            buttonMap.setVisibility(View.GONE);
            if (mCurrentFragment != FRAGMENT_REPORT){
                replaceFragment(new Report());
                mCurrentFragment = FRAGMENT_REPORT;
            }
        }else if (id == R.id.nav_Station){
            locationSearch.setVisibility(View.GONE);
            buttonMap.setVisibility(View.GONE);
            if (mCurrentFragment != FRAGMENT_STATION){
                replaceFragment(new Station());
                mCurrentFragment = FRAGMENT_STATION;
            }
        }else if (id == R.id.nav_Admin){
            locationSearch.setVisibility(View.GONE);
            buttonMap.setVisibility(View.GONE);
            if (mCurrentFragment != FRAGMENT_ADMIN){
                replaceFragment(new Admin());
                mCurrentFragment = FRAGMENT_ADMIN;
            }
        }else if (id == R.id.nav_logout){
            locationSearch.setVisibility(View.VISIBLE);
            buttonMap.setVisibility(View.VISIBLE);
            Login.checkLogin =false;
            mAuth.signOut();
            showUserInformaiton();
            HideBarMenu();
            replaceFragment(new Home());
            mCurrentFragment = FRAGMENT_HOME;

        }
        else if (id == R.id.nav_login){
            if (mCurrentFragment != FRAGMENT_LOGIN){
                replaceFragment(new Home());
                mCurrentFragment = FRAGMENT_HOME;
                startActivity(new Intent(MainActivity.this, Login.class));
            }

        }else if (id == R.id.nav_infor){
            if (mCurrentFragment != FRAGMENT_INFO){
                locationSearch.setVisibility(View.GONE);
                buttonMap.setVisibility(View.GONE);
                if (mCurrentFragment != FRAGMENT_INFO){
                    replaceFragment(new Information());
                    mCurrentFragment = FRAGMENT_INFO;
                }
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /* Xử lý đóng thanh bar menu mỗi khi nhấn nút back*/
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    /* Hàm xử lý mỗi khi Fragment được chọn */
    private  void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    /* Hàm xử lý ẩn menu khi đăng nhập bằng user*/
    public void HideBarMenu(){
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_Admin).setVisible(false);
        menu.findItem(R.id.nav_report).setVisible(false);
        menu.findItem(R.id.nav_Station).setVisible(false);
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_login).setVisible(true);
    }

    /* Hàm xử lý hiện menu khi đăng nhập bằng admin*/
    public void ShowBarMenu(){
        Menu menu = navigationView.getMenu();
        if(!Login.checkLogin) {
            menu.findItem(R.id.nav_Admin).setVisible(true);
        }
        menu.findItem(R.id.nav_report).setVisible(true);
        menu.findItem(R.id.nav_Station).setVisible(true);
        menu.findItem(R.id.nav_logout).setVisible(true);
        menu.findItem(R.id.nav_login).setVisible(false);
    }

    /* Hàm để trả địa chỉ về cho danh sách địa chỉ*/
    private List<String> getAddress_List(){
        List<String> List = new ArrayList<>();
        List.add("Nghe An");
        List.add("Ha Noi");
        List.add("Da Nang");
        List.add("TP. HCM");
        return List;
    }

    /* Hàm để trả các thông số về cho danh sách thông số*/
    private List<String> getParameter_List(){
        List<String> List = new ArrayList<>();
        List.add("AQI");
        List.add("PM2.5");
        List.add("PM1.0");
        List.add("CO2");
        List.add("SO2");
        List.add("NO2");
        return List;
    }

    private Map<StationItem, List<ParameterItem>> getListItems(){
        Map<StationItem, List<ParameterItem>> listMap = new HashMap<>();

        for (int i = 0; i< MainActivity.Address_List.size(); i++){
            StationItem stationItem = new StationItem(i, MainActivity.Address_List.get(i),true);
            List<ParameterItem> parameterItem = new ArrayList<>();
            for(int j = 0; j< MainActivity.Parameter_List.size(); j++){
                parameterItem.add(new ParameterItem(j, MainActivity.Parameter_List.get(j), true));
            }
            listMap.put(stationItem,parameterItem);
//            if(mListStationdemo.get(i) != null)
//                listMap.put(mListStationdemo.get(i),parameterItem);
        }

        return listMap;
    }

    public static String getNameAdmin(String emailAdmin){
        String nameAdmin;
        int i;
        if (emailAdmin.indexOf(".") < emailAdmin.indexOf("@")){
             i = emailAdmin.indexOf('.');
        }
        else i = emailAdmin.indexOf("@");

        nameAdmin = emailAdmin.substring(0,i);
        return nameAdmin;
    }

    private void showUserInformaiton(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            if(Login.checkLogin){
                //String email = Admin_List.get(Login.indexAdmin).getEmail();
                String email = Login.LoginEmail.getText().toString();
                Adminname.setText(getNameAdmin(email));
                Adminemail.setText(email);
                Admin_image.setImageResource(R.drawable.admin);
            }
            else{
                Adminname.setText("User");
                Adminemail.setVisibility(View.GONE);
                Admin_image.setImageResource(R.drawable.user);
            }
        }
        else {
            String email = user.getEmail();
            Adminname.setText(getNameAdmin(email));
            Adminemail.setText(email);
            Admin_image.setImageResource(R.drawable.admin_icon);
        }
    }

    public void onClickGetDataAdminList(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User_Account");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AdminItem adminItem = snapshot.getValue(AdminItem.class);
                if(adminItem != null){
                    Admin_List.add(adminItem);
                    //Admin.adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AdminItem adminItem = snapshot.getValue(AdminItem.class);
                if(adminItem == null || Admin_List == null|| Admin_List.isEmpty()){
                    return;
                }
                for(int i = 0; i< Admin_List.size(); i++){
                    if(adminItem.getId() == Admin_List.get(i).getId()){
                        Admin_List.set(i, adminItem);
                        break;
                    }
                }
                Admin.adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                AdminItem adminItem = snapshot.getValue(AdminItem.class);
                if(adminItem == null || Admin_List == null|| Admin_List.isEmpty()){
                    return;
                }
                for(int i = 0; i< Admin_List.size(); i++){
                    if(adminItem.getId() == Admin_List.get(i).getId()){
                        Admin_List.remove(Admin_List.get(i));
                        break;
                    }
                }
                Admin.adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClickGetDataStation(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("my_station");
        List<StationItem> List = new ArrayList<>();
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    StationItem stationItem = dataSnapshot.getValue(StationItem.class);
//                    List.add(stationItem);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this,"Faild", Toast.LENGTH_SHORT).show();
//            }
//        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                StationItem stationItem = snapshot.getValue(StationItem.class);
                if(stationItem != null){
                    mListStation.add(stationItem);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Faild", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public  void onClickGetDataParameter(int position){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("my_parameter/" + position);
//        List<ParameterItem> List = new ArrayList<>();
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    ParameterItem parameterItem = dataSnapshot.getValue(ParameterItem.class);
//                    List.add(parameterItem);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this,"Faild", Toast.LENGTH_SHORT).show();
//            }
//        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ParameterItem parameterItem = snapshot.getValue(ParameterItem.class);
                if(parameterItem != null){
                    mParameterItems.add(parameterItem);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Faild", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public Map<StationItem, List<ParameterItem>> onClickGetDataListItems(){
        Map<StationItem, List<ParameterItem>> listMap = new HashMap<>();
        for (int i = 0; i< mListStation.size(); i++){
            mParameterItems = new ArrayList<>();
            onClickGetDataParameter(i);
            listMap.put(mListStation.get(i),mParameterItems);
        }
        return listMap;
    }

    public static List<String> getAddress_ListChecked(){
        List<String> List = new ArrayList<>();

        if(mListStationChecked.size() > 0){
            for (int i =0; i< mListStationChecked.size(); i++){
                List.add(mListStationChecked.get(i).getName());
            }
        }else {
            List.add("Finding ...");
        }

        return List;
    }

    public void onClickPushDataParameter(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        for(int i = 0; i<mListStation.size();i++){
            List<ParameterItem> parameterItem = new ArrayList<>();
            for(int j = 0; j< MainActivity.Parameter_List.size(); j++){
                parameterItem.add(new ParameterItem(j, MainActivity.Parameter_List.get(j), true));
            }
            DatabaseReference myRef = database.getReference("my_parameter/" + i);
            myRef.setValue(parameterItem, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(MainActivity.this,"Push data success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onClickGetDataParameterList(){
        for(int i = 0; i<mListStation.size(); i++){
            mParameterItems = new ArrayList<>();
            onClickGetDataParameter(i);
            list_parameterItemlists.add(mParameterItems);

        }
    }


    // Send DATA to server

    private void startMQTT(){
        mqttHelper = new MQTTHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                // Receive data here !!
                new GetURL().execute();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    public static void sendDataToMQTT(String value) {

        MqttMessage msg = new MqttMessage();
        // Id của message, có thể trùng nhau
        msg.setId(1234);
        // QoS = 0 là nhanh nhất, nhưng tỉ lệ lỗi cao, lỗi thì gửi lại
        // Có các mức từ 0 -> 4 (gần như là HTTP và chậm)
        msg.setQos(0);
        //
        msg.setRetained(true);

        String data = value;

        byte[] b = data.getBytes(StandardCharsets.UTF_8);
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish("NDDIoT/feeds/air-monitoring", msg);
        }
        catch (MqttException e) {
            // Do nothing
        }
    }



    public List<Integer> getDataJson(){
        List<Integer> list = new ArrayList<>();
        list.add(0);
        return list;
    }

    private void setupBlinkyTimer(){

        Timer mTimer = new Timer();
        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                new GetURL().execute();

            }
        };
        mTimer.schedule(mTask, 2000, 5*1000);
    }
}
