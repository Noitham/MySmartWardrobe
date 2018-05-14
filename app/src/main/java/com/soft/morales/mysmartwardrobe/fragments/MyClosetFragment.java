package com.soft.morales.mysmartwardrobe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soft.morales.mysmartwardrobe.R;
import com.soft.morales.mysmartwardrobe.adapters.TabsAdapter;

public class MyClosetFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);

        TabsAdapter tabsAdapter = new TabsAdapter(getChildFragmentManager());
        tabsAdapter.addFragment(new MyClosetTabFragment(1), "T-Shirts");
        tabsAdapter.addFragment(new MyClosetTabFragment(2), "Jumpers");
        tabsAdapter.addFragment(new MyClosetTabFragment(3), "Jackets");
        tabsAdapter.addFragment(new MyClosetTabFragment(4), "Jeans");
        tabsAdapter.addFragment(new MyClosetTabFragment(5), "Shoes");
        tabsAdapter.addFragment(new MyClosetTabFragment(6), "Accesories");

        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;

    }
}