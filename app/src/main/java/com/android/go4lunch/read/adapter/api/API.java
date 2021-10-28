package com.android.go4lunch.read.adapter.api;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("")
    Call<List<Restaurant>> findAll();
}
