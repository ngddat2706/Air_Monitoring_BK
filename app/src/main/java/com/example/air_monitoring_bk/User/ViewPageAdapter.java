package com.example.air_monitoring_bk.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.air_monitoring_bk.MainActivity;

public class ViewPageAdapter extends FragmentStatePagerAdapter {


    public ViewPageAdapter( FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new AQI_Fragment();
            default:
                return new Parameter_Fragment().newInstance(position);
        }
    }

    @Override
    public int getCount() {
        return MainActivity.Parameter_ListChecked.size(); // number Tab
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        if(position == 0) title = "AQI";
        else title = MainActivity.Parameter_ListChecked.get(position).getName();
        return title;
    }

}
