package com.example.air_monitoring_bk.Admin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.air_monitoring_bk.MainActivity;
import com.example.air_monitoring_bk.R;
import com.example.air_monitoring_bk.User.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;


public class Home extends Fragment {

    private TabLayout mTabLayout;
    public static ViewPager mViewPager;

    public static Home getInstance(){
        return new Home();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mTabLayout = (TabLayout) view.<View>findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.<View>findViewById(R.id.view_pager);


        return view;
    }

    // Call onActivity Create method


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPage(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void  setUpViewPage(ViewPager viewPage){
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getChildFragmentManager());
        mViewPager.setAdapter(viewPageAdapter);
    }


}