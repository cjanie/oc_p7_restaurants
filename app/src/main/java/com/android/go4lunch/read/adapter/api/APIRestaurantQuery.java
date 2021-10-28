package com.android.go4lunch.read.adapter.api;

import com.android.go4lunch.read.adapter.api.API;
import com.android.go4lunch.read.businesslogic.gateways.RestaurantQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRestaurantQuery implements RestaurantQuery {

    // Or https://occitanie.opendatasoft.com/map/+abb27becbb27403b/edit/
    private static final String BASE_URL = "https://data.montpellier3m.fr/sites/default/files/ressources/OSM_Metropole_restauration_bar.json";

    private final Gson gson;

    private final OkHttpClient httpClient;

    private final Retrofit retrofit;

    private final API api;

    private List<Restaurant> list;

    public APIRestaurantQuery() {
        this.gson = new Gson();
        this.httpClient = new OkHttpClient();
        this.retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.api = this.retrofit.create(API.class);
        this.list = new ArrayList<>();
    }



    @Override
    public List<Restaurant> findAll() {
        Call<List<Restaurant>> restaurants = this.api.findAll();
        restaurants.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                list = response.body();
            }
            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
            }
        });
        return this.list;
    }

}
