package com.example.air_monitoring_bk.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.air_monitoring_bk.MainActivity;
import com.example.air_monitoring_bk.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    ImageView mapBackbtn;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapBackbtn = findViewById(R.id.map_back_button);
        mapBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Login.this, WelcomeScreen.class));
                onBackPressed();
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.AirMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng TpHCM = new LatLng(10.773585086847559, 106.66064075544728);
        LatLng HCM = new LatLng(10.778981377457296, 106.6573791894972);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(TpHCM,17));
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.addMarker(new MarkerOptions()
                .title("AQI: " +  MainActivity.AQI.get(MainActivity.AQI.size()-1).toString())
                .snippet("Trường Đại học Bách khoa - Đại học Quốc gia TP.HCM")
                .icon(bitmapDescriptorFromVector(getApplicationContext()))
                .position(TpHCM));

//        map.addMarker(new MarkerOptions()
//                .title("AQI: " +  MainActivity.AQI.get(MainActivity.AQI.size()-1).toString())
//                .snippet("Trung Tâm Văn Hóa Hòa Bình Quận 10")
//                .icon(bitmapDescriptorFromVector(getApplicationContext()))
//                .position(HCM));
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context){
        int vectorResId;
        Integer progress = MainActivity.AQI.get(MainActivity.AQI.size()-1);
        if(progress<51){
            vectorResId = R.drawable.marker_green;
        } else if(progress <101){
            vectorResId = R.drawable.marker_yellow;
        } else if(progress <151){
            vectorResId = R.drawable.marker_orange;
        } else if(progress <201){
            vectorResId = R.drawable.marker_red;
        }else if(progress <301){
            vectorResId = R.drawable.marker_violet;
        }else {
            vectorResId = R.drawable.marker_brown;
        }
        Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}