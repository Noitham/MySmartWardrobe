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

    List<Garment> myGarments, myShirts, myJerseys, myJackets, myJeans, myShoes, myAccessories;

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
            case 2:
                getAllJeans();
                break;
            case 3:
                getAllJerseys();
                break;
            case 4:
                getAllJackets();
                break;
            case 5:
                getAllShoes();
                break;
            case 6:
                getAllAccessories();
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

            switch (mPosition){
                case 1:
                    startCardActivity(myShirts.get(position));
                    break;
                case 2:
                    startCardActivity(myJeans.get(position));
                    break;
                case 3:
                    startCardActivity(myJerseys.get(position));
                    break;
                case 4:
                    startCardActivity(myJackets.get(position));
                    break;
                case 5:
                    startCardActivity(myShoes.get(position));
                    break;
                case 6:
                    startCardActivity(myAccessories.get(position));
                    break;
                default:
                    break;
            }

        }
    };

    public void startCardActivity(Garment garment) {
        Intent intent = new Intent(getActivity(), CardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Nombre", garment.name);
        bundle.putString("Foto", garment.photo);
        bundle.putString("Categoria", garment.category);
        bundle.putString("Temporada", garment.season);
        bundle.putString("Precio", garment.price);
        bundle.putString("Color", garment.color);
        bundle.putString("Talla", garment.size);
        bundle.putString("Marca", garment.brand);
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
                for (int i = 0; i < garments.size(); i++) {

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
                for (int i = 0; i < garments.size(); i++) {

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

    public void getAllJeans(){

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

                myJeans = new ArrayList<>();
                for (int i = 0; i < garments.size(); i++) {

                    if(myGarments.get(i).getCategory().equalsIgnoreCase("pantalón")){

                        myJeans.add(new Garment(myGarments.get(i)));

                    }
                }

                fillListView(myJeans);
            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }



    public void getAllJerseys() {

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

                myJerseys = new ArrayList<>();
                for (int i = 0; i < garments.size(); i++) {

                    if(myGarments.get(i).getCategory().equalsIgnoreCase("jersey")){

                        myJerseys.add(new Garment(myGarments.get(i)));

                    }
                }

                fillListView(myJerseys);
            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getAllAccessories(){

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

                myAccessories = new ArrayList<>();
                for (int i = 0; i < garments.size(); i++) {

                    if(myGarments.get(i).getCategory().equalsIgnoreCase("accesorio")){

                        myAccessories.add(new Garment(myGarments.get(i)));

                    }
                }

                fillListView(myAccessories);
            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getAllShoes() {

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

                myShoes = new ArrayList<>();
                for (int i = 0; i < garments.size(); i++) {

                    if(myGarments.get(i).getCategory().equalsIgnoreCase("calzado")){

                        myShoes.add(new Garment(myGarments.get(i)));

                    }
                }

                fillListView(myShoes);
            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


}
