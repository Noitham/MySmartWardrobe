package com.soft.morales.mysmartwardrobe.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class MyClosetTabFragment extends Fragment {

    private APIService mAPIService;

    private int mPosition;

    private ListView listView;

    List<Garment> myGarments;
    List<Garment>  myShirts;
    List<Garment>  myJackets;

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



        switch (mPosition){
            case 1:
                getAllShirts();
                break;
            case 4:
                getAllJackets();
                break;
            default:
                break;
        }

        return rootView;
    }

    public void fillListView(List<Garment> Garments) {

        if (Garments != null) {
            String sList[] = new String[Garments.size()];
            for (int i = 0; i < Garments.size(); i++) {
                sList[i] = Garments.get(i).name;
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
            startCardActivity(myGarments.get(position));
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

    public void getAllShirts(){

        mAPIService = ApiUtils.getAPIService();

        Call<List<Garment>> call = mAPIService.getGarment();

        call.enqueue(new Callback<List<Garment>>() {
            @Override
            public void onResponse(Call<List<Garment>> call, Response<List<Garment>> response) {

                List<Garment> garments = response.body();

                myGarments = new ArrayList<>();

                for (int i = 0; i < garments.size(); i++) {

                    myGarments.add(new Garment(garments.get(i)));

                }

                myShirts = new ArrayList<>();
                for (int i = 0; i < myGarments.size(); i++) {

                    if(myGarments.get(i).getCategory().equalsIgnoreCase("camiseta")){

                        myShirts.add(new Garment(myGarments.get(i)));

                    }
                }

                fillListView(myShirts);
            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getAllJackets(){

        mAPIService = ApiUtils.getAPIService();

        Call<List<Garment>> call = mAPIService.getGarment();

        call.enqueue(new Callback<List<Garment>>() {
            @Override
            public void onResponse(Call<List<Garment>> call, Response<List<Garment>> response) {

                List<Garment> garments = response.body();

                myGarments = new ArrayList<>();

                for (int i = 0; i < garments.size(); i++) {

                    myGarments.add(new Garment(garments.get(i)));

                }

                myJackets = new ArrayList<>();
                for (int i = 0; i < myGarments.size(); i++) {

                    if(myGarments.get(i).getCategory().equalsIgnoreCase("chaqueta")){

                        myJackets.add(new Garment(myGarments.get(i)));

                    }
                }

                fillListView(myJackets);
            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    public void getAllGarments() {

        mAPIService = ApiUtils.getAPIService();

        Call<List<Garment>> call = mAPIService.getGarment();

        call.enqueue(new Callback<List<Garment>>() {
            @Override
            public void onResponse(Call<List<Garment>> call, Response<List<Garment>> response) {

                List<Garment> garments = response.body();

                myGarments = new ArrayList<>();

                for (int i = 0; i < garments.size(); i++) {

                    myGarments.add(new Garment(garments.get(i)));

                }
            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
