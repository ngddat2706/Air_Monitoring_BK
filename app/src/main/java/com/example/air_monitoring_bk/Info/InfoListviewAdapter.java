package com.example.air_monitoring_bk.Info;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.air_monitoring_bk.Admin.Information;
import com.example.air_monitoring_bk.R;

import java.util.List;

public class InfoListviewAdapter extends BaseAdapter {

    List<InfoItem> InfoItemListview;
    Information mycontent;

    public InfoListviewAdapter(Information mycontent,List<InfoItem> infoItemListview) {
        InfoItemListview = infoItemListview;
        this.mycontent = mycontent;
    }

    @Override
    public int getCount() {
        return InfoItemListview.size();
    }

    @Override
    public Object getItem(int position) {
        return InfoItemListview.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mycontent.getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_info,null);

        ImageView AQI_image = convertView.findViewById(R.id.AQI_image);
        TextView AQI_index = convertView.findViewById(R.id.AQI_index);
        TextView AQI_affect = convertView.findViewById(R.id.AQI_affect);
        TextView AQI_recommendations = convertView.findViewById(R.id.AQI_recommendations);

        AQI_image.setBackgroundResource(InfoItemListview.get(position).getAQI_image());
        AQI_image.setImageResource(InfoItemListview.get(position).getAQI_face());
        AQI_index.setText(InfoItemListview.get(position).getAQI_index());
        AQI_affect.setText(InfoItemListview.get(position).getAQI_affect());
        AQI_recommendations.setText(InfoItemListview.get(position).getAQI_recommendations());

        return convertView;
    }
}
