package com.soft.morales.mysmartwardrobe.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soft.morales.mysmartwardrobe.R;

@SuppressLint("ValidFragment")
public class MyClosetTabFragment extends Fragment {
    private int mPosition;

    public MyClosetTabFragment(int position) {
        mPosition = position;
    }

    public MyClosetTabFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.fav_number);
        textView.setText("Type " + mPosition);

        return rootView;
    }
}
