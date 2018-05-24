package com.soft.morales.mysmartwardrobe.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.soft.morales.mysmartwardrobe.NewLookActivity;
import com.soft.morales.mysmartwardrobe.R;

import java.util.Calendar;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    String dayString;
    int dayNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 4, 23);

        CalendarView calendarView = (CalendarView) rootView.findViewById(R.id.calendarView);

        try {
            calendarView.setDate(calendar);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        calendarView.setOnDayClickListener(new OnDayClickListener() {

            @Override
            public void onDayClick(EventDay eventDay) {
                dayString = eventDay.getCalendar().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                dayNumber = eventDay.getCalendar().get(Calendar.DAY_OF_MONTH);

                Log.d("CALENDAR: ", dayString + " " + String.valueOf(dayNumber));

                AlertDialog diaBox = AskOption();
                diaBox.show();

            }


        });

        return rootView;

    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getContext())
                //set message, title, and icon
                .setTitle(dayString.toUpperCase() + " " + dayNumber)
                .setMessage("Qué le gustaría hacer?")
                .setPositiveButton("Añadir un look", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        Intent intent = new Intent(getContext(), NewLookActivity.class);
                        startActivity(intent);

                        dialog.dismiss();
                    }

                })

                .setNegativeButton("Consultar looks", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;

    }


}
