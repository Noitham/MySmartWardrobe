package com.soft.morales.mysmartwardrobe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soft.morales.mysmartwardrobe.NewGarmentActivity;
import com.soft.morales.mysmartwardrobe.R;

public class NewGarmentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Intent intent = new Intent(getActivity(), NewGarmentActivity.class);
        startActivity(intent);

        return rootView;

    }
}
