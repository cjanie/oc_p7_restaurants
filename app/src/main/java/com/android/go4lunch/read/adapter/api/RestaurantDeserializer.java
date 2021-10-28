package com.android.go4lunch.read.adapter.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantDeserializer {

    private final Parcelable.Creator<RestaurantDeserializer> creator;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("addr_street")
    @Expose
    private String address;




    public RestaurantDeserializer(Parcel source) {
        this.creator = new Parcelable.Creator<RestaurantDeserializer>() {
            @Override
            public RestaurantDeserializer createFromParcel(Parcel source) {
                return new RestaurantDeserializer(source);
            }

            @Override
            public RestaurantDeserializer[] newArray(int size) {
                return new RestaurantDeserializer[size];
            }
        };
    }
}
