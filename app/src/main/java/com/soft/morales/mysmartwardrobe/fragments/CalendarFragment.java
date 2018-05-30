package com.soft.morales.mysmartwardrobe.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.gson.Gson;
import com.soft.morales.mysmartwardrobe.CheckLookActivity;
import com.soft.morales.mysmartwardrobe.R;
import com.soft.morales.mysmartwardrobe.model.Look;
import com.soft.morales.mysmartwardrobe.model.User;
import com.soft.morales.mysmartwardrobe.model.persist.APIService;
import com.soft.morales.mysmartwardrobe.model.persist.ApiUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static com.soft.morales.mysmartwardrobe.CheckLookActivity.CHECK_RESULT_ACTIVITY;

public class CalendarFragment extends Fragment {

    String dayString, monthString;

    int dayNumber, monthNumber, yearNumber;
    private CalendarView mCalendarView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 05, 1);

        mCalendarView = (CalendarView) rootView.findViewById(R.id.calendarView);

        try {
            mCalendarView.setDate(calendar);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {

            @Override
            public void onDayClick(EventDay eventDay) {

                dayString = eventDay.getCalendar().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                dayNumber = eventDay.getCalendar().get(Calendar.DAY_OF_MONTH);
                monthNumber = eventDay.getCalendar().get(Calendar.MONTH);
                yearNumber = eventDay.getCalendar().get(Calendar.YEAR);

                monthNumber++;

                monthString = String.format("%02d", monthNumber);

                AlertDialog diaBox = AskOption();
                diaBox.show();

            }

        });

        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Gson gson = new Gson();
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), getActivity().MODE_PRIVATE);
        User mUser = gson.fromJson(sharedPref.getString("user", ""), User.class);

        HashMap query = new HashMap();
        query.put("username", mUser.getEmail());

        APIService api = ApiUtils.getAPIService();
        Call<List<Look>> call = api.getLooks(query);

        call.enqueue(new Callback<List<Look>>() {
            @Override
            public void onResponse(Call<List<Look>> call, Response<List<Look>> response) {

                List<Look> looks = response.body();
                List<EventDay> events = new ArrayList<>();

                DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);


                if (looks != null && looks.size() > 0) {
                    for (int i = 0; i < looks.size(); i++) {
                        Calendar calendar = Calendar.getInstance();
                        try {
                            Date date = format.parse(looks.get(i).getDate());
                            calendar.setTime(date);
                            events.add(new EventDay(calendar, R.drawable.icon_hanger));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }

                mCalendarView.setEvents(events);

            }

            @Override
            public void onFailure(Call<List<Look>> call, Throwable t) {
            }
        });


    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getContext())
                //set message, title, and icon
                .setTitle(dayString.toUpperCase() + " " + dayNumber)
                .setMessage("Qué le gustaría hacer?")
                .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        dialog.dismiss();
                    }

                })

                .setPositiveButton("Consultar look", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String date = String.valueOf(dayNumber).trim() + "-" + String.valueOf(monthString).trim() + "-" + String.valueOf(yearNumber).trim();

                        Bundle bundle = new Bundle();
                        bundle.putString("date", date);
                        Intent intent = new Intent(getContext(), CheckLookActivity.class);
                        intent.putExtras(bundle);

                        try {
                            startActivityForResult(intent, CHECK_RESULT_ACTIVITY);
                        } catch (Exception e) {
                            Log.d("Exception:", String.valueOf(e));
                        }

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHECK_RESULT_ACTIVITY && resultCode == RESULT_CANCELED) {
            Toast.makeText(getContext(), "No existe look para ese día", Toast.LENGTH_SHORT).show();
        }
    }
}
