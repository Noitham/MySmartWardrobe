package com.soft.morales.mysmartwardrobe.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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

import com.soft.morales.mysmartwardrobe.CardActivity;
import com.soft.morales.mysmartwardrobe.NewLookActivity;
import com.soft.morales.mysmartwardrobe.R;
import com.soft.morales.mysmartwardrobe.adapters.CustomAdapter;
import com.soft.morales.mysmartwardrobe.model.Garment;
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

    List<Garment> myGarments, myShirts, myJerseys, myJackets, myJeans, myShoes, myAccessories;

    public MyClosetTabFragment(int position) {
        mPosition = position;
    }

    public MyClosetTabFragment(int position, int mode) {
        mPosition = position;
        value = mode;
    }


    public MyClosetTabFragment(int position, int mode, String foto1, String foto2, String foto3) {
        mPosition = position;
        value = mode;

        if (foto1 != null) {
            this.foto1 = foto1;
            Log.d("Happy", "Happy");

        } else {
            Log.d("NONONO", "NONONO");
        }

        if (foto2 != null) {
            this.foto2 = foto2;
            Log.d("Happy", "Happy");

        } else {
            Log.d("NONONO", "NONONO");

        }
        if (foto3 != null) {
            this.foto3 = foto3;
            Log.d("Happy", "Happy");

        } else {
            Log.d("NONONO", "NONONO");

        }
    }


    public MyClosetTabFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

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

            bundle.putString("Foto", garment.photo);

            if (pos == 1) {
                bundle.putString("Part", "Camiseta");

            } else if (pos == 2) {
                bundle.putString("Part", "Pantalones");

            } else if (pos == 5) {
                bundle.putString("Part", "Bambas");

            }


            intent.putExtras(bundle);
            if (getActivity() != null) {
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }


        } else {
            Intent intent = new Intent(getActivity(), CardActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", garment.id);
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
    }

    public void getAllShirts() {

        mAPIService = ApiUtils.getAPIService();

        HashMap query = new HashMap();
        query.put("category", "Camiseta");

        Call<List<Garment>> call = mAPIService.getGarment(query);

        call.enqueue(new Callback<List<Garment>>() {
            @Override
            public void onResponse(Call<List<Garment>> call, Response<List<Garment>> response) {

                myShirts = response.body();
                fillListView(myShirts);
            }

            @Override
            public void onFailure(Call<List<Garment>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getAllJackets() {

        mAPIService = ApiUtils.getAPIService();

        HashMap query = new HashMap();
        query.put("category", "chaqueta");

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

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

}
