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


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.icon_shirt,
            R.drawable.icon_pants,
            R.drawable.icon_blouse,
            R.drawable.icon_jacket,
            R.drawable.icon_boot,
            R.drawable.icon_accesories
    };

    int myInt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);


        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


        return rootView;

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) tab.setCustomView(R.layout.view_home_tab);
        }

    }

    private void setupViewPager(ViewPager viewPager) {

        TabsAdapter tabsAdapter = new TabsAdapter(getChildFragmentManager());
        tabsAdapter.addFragment(new MyClosetTabFragment(1));
        tabsAdapter.addFragment(new MyClosetTabFragment(2));
        tabsAdapter.addFragment(new MyClosetTabFragment(3));
        tabsAdapter.addFragment(new MyClosetTabFragment(4));
        tabsAdapter.addFragment(new MyClosetTabFragment(5));
        tabsAdapter.addFragment(new MyClosetTabFragment(6));

        viewPager.setAdapter(tabsAdapter);

    }

}