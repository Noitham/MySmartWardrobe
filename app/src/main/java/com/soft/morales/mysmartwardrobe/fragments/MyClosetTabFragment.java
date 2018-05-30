package com.soft.morales.mysmartwardrobe.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.soft.morales.mysmartwardrobe.CardActivity;
import com.soft.morales.mysmartwardrobe.NewLookActivity;
import com.soft.morales.mysmartwardrobe.R;
import com.soft.morales.mysmartwardrobe.adapters.CustomAdapter;
import com.soft.morales.mysmartwardrobe.model.Garment;
import com.soft.morales.mysmartwardrobe.model.User;
import com.soft.morales.mysmartwardrobe.model.persist.APIService;
import com.soft.morales.mysmartwardrobe.model.persist.ApiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class MyClosetTabFragment extends Fragment {

    private APIService mAPIService;
    private int mPosition;
    private ListView listView;

    int value;

    String foto1;
    String foto2;
    String foto3;

    List<Garment> myShirts, myJerseys, myJackets, myJeans, myShoes, myAccessories;
    private User mUser;

    public MyClosetTabFragment(int position) {
        mPosition = position;
    }

    public MyClosetTabFragment(int position, int mode, String foto1, String foto2, String foto3) {
        mPosition = position;
        value = mode;

        if (foto1 != null) {
            this.foto1 = foto1;
        }
        if (foto2 != null) {
            this.foto2 = foto2;
        }
        if (foto3 != null) {
            this.foto3 = foto3;
        }

    }


    public MyClosetTabFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        Gson gson = new Gson();
        SharedPreferences shared = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mUser = gson.fromJson(shared.getString("user", ""), User.class);

        listView = (ListView) container.findViewById(R.id.listview);

        switch (mPosition) {
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

        List<Garment> rowItems = new ArrayList<Garment>();

        if (Garments != null) {

            for (int i = 0; i < Garments.size(); i++) {

                Garment item = new Garment(Garments.get(i).getName(),
                        Garments.get(i).getPhoto(), Garments.get(i).getCategory(),
                        Garments.get(i).getBrand());
                rowItems.add(item);

            }

            listView = (ListView) getView().findViewById(R.id.listview);

            CustomAdapter adapter = new CustomAdapter(getContext(), rowItems);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(listener);

        }
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


            switch (mPosition) {
                case 1:
                    startCardActivity(myShirts.get(position), mPosition);
                    break;
                case 2:
                    startCardActivity(myJeans.get(position), mPosition);
                    break;
                case 3:
                    startCardActivity(myJerseys.get(position), mPosition);
                    break;
                case 4:
                    startCardActivity(myJackets.get(position), mPosition);
                    break;
                case 5:
                    startCardActivity(myShoes.get(position), mPosition);
                    break;
                case 6:
                    startCardActivity(myAccessories.get(position), mPosition);
                    break;
                default:
                    break;
            }

        }
    };

    public void startCardActivity(Garment garment, int pos) {

        if (value == 1) {
            Intent intent = new Intent(getActivity(), NewLookActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("Foto", garment.getPhoto());

            if (pos == 1 || pos == 3 || pos == 4) {
                bundle.putString("garmentType", "Shirt");
                SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                sharedPref.edit().putString("idShirt", garment.getId()).apply();
            } else if (pos == 2) {
                bundle.putString("garmentType", "Legs");
                SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                sharedPref.edit().putString("idLegs", garment.getId()).apply();
            } else if (pos == 5) {
                bundle.putString("garmentType", "Feet");
                SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                sharedPref.edit().putString("idFeet", garment.getId()).apply();
            }

            intent.putExtras(bundle);
            if (getActivity() != null) {
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }


        } else {
            Intent intent = new Intent(getActivity(), CardActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", garment.getId());
            bundle.putString("Nombre", garment.getName());
            bundle.putString("Foto", garment.getPhoto());
            bundle.putString("Categoria", garment.getCategory());
            bundle.putString("Temporada", garment.getSeason());
            bundle.putString("Precio", garment.getPrice());
            bundle.putString("Color", garment.getColor());
            bundle.putString("Talla", garment.getSize());
            bundle.putString("Marca", garment.getBrand());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void getAllShirts() {

        mAPIService = ApiUtils.getAPIService();

        HashMap query = new HashMap();

        query.put("category", "Camiseta");
        query.put("username", mUser.getEmail());

        Call<List<Garment>> call = mAPIService.getGarment(query);

        call.enqueue(new Callback<List<Garment>>() {
            @Override
            public void onResponse(Call<List<Garment>> call, Response<List<Garment>> response) {

                myShirts = response.body();
                fillListView(myShirts);
            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Log.d("ERROR:", "NO SHIRTS");
            }
        });

    }

    public void getAllJackets() {

        mAPIService = ApiUtils.getAPIService();

        HashMap query = new HashMap();
        query.put("category", "chaqueta");
        query.put("username", mUser.getEmail());

        Call<List<Garment>> call = mAPIService.getGarment(query);

        call.enqueue(new Callback<List<Garment>>() {
            @Override
            public void onResponse(Call<List<Garment>> call, Response<List<Garment>> response) {

                myJackets = response.body();
                fillListView(myJackets);
            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getAllJeans() {

        mAPIService = ApiUtils.getAPIService();

        HashMap query = new HashMap();
        query.put("category", "pantalón");
        query.put("username", mUser.getEmail());

        Call<List<Garment>> call = mAPIService.getGarment(query);

        call.enqueue(new Callback<List<Garment>>() {
            @Override
            public void onResponse(Call<List<Garment>> call, Response<List<Garment>> response) {

                myJeans = response.body();
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

        HashMap query = new HashMap();
        query.put("category", "jersey");
        query.put("username", mUser.getEmail());

        Call<List<Garment>> call = mAPIService.getGarment(query);

        call.enqueue(new Callback<List<Garment>>() {
            @Override
            public void onResponse(Call<List<Garment>> call, Response<List<Garment>> response) {

                myJerseys = response.body();
                fillListView(myJerseys);
            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAllAccessories() {

        mAPIService = ApiUtils.getAPIService();

        HashMap query = new HashMap();
        query.put("category", "Accesorio");
        query.put("username", mUser.getEmail());

        Call<List<Garment>> call = mAPIService.getGarment(query);

        call.enqueue(new Callback<List<Garment>>() {
            @Override
            public void onResponse(Call<List<Garment>> call, Response<List<Garment>> response) {

                myAccessories = response.body();
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

        HashMap query = new HashMap();
        query.put("category", "calzado");
        query.put("username", mUser.getEmail());

        Call<List<Garment>> call = mAPIService.getGarment(query);

        call.enqueue(new Callback<List<Garment>>() {
            @Override
            public void onResponse(Call<List<Garment>> call, Response<List<Garment>> response) {

                myShoes = response.body();
                fillListView(myShoes);
            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
