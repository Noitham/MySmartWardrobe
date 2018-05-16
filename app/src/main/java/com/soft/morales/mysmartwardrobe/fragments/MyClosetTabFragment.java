package com.soft.morales.mysmartwardrobe.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.morales.mysmartwardrobe.CardActivity;
import com.soft.morales.mysmartwardrobe.R;
import com.soft.morales.mysmartwardrobe.model.Garment;
import com.soft.morales.mysmartwardrobe.model.persist.APIService;
import com.soft.morales.mysmartwardrobe.model.persist.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class MyClosetTabFragment extends Fragment {

    private APIService mAPIService;

    private int mPosition;

    private ListView listView;

    Garment[] Garments;

    Garment myGarments[];

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

        listView = (ListView) container.findViewById(R.id.listview);

        new loadDataWebServiceTask().execute();

        return rootView;
    }

    public void fillListView(Garment Garments[]) {
        if (Garments != null) {
            String sList[] = new String[Garments.length];
            for (int i = 0; i < Garments.length; i++) {
                sList[i] = Garments[i].name;
            }
            listView = (ListView) getView().findViewById(R.id.listview);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), // context
                    R.layout.listitem, // layout description for each list item
                    sList);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(listener);
        }
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            startCardActivity(Garments[position]);
        }
    };

    public void startCardActivity(Garment garment) {
        Intent intent = new Intent(getActivity(), CardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("description", garment.category + " : " + garment.brand);
        bundle.putString("imagename", garment.name + ".jpg");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public Garment[] getGarments() {


        mAPIService = ApiUtils.getAPIService();

        Call<List<Garment>> call = mAPIService.getGarment();

        call.enqueue(new Callback<List<Garment>>() {
            @Override
            public void onResponse(Call<List<Garment>> call, Response<List<Garment>> response) {

                List<Garment> garments = response.body();

                myGarments = new Garment[garments.size()];

                for (int i = 0; i < garments.size(); i++) {

                    myGarments[i] = garments.get(i);

                }

            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return myGarments;
    }

    private class loadDataWebServiceTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Garments = getGarments();
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            fillListView(Garments);
        }

    }

}
