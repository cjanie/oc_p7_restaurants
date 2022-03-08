package com.android.go4lunch.apis.apiGoogleMaps.deserializers.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Period {

    @SerializedName("close")
    @Expose
    private OpenClose close;

    @SerializedName("open")
    @Expose
    private OpenClose open;

    public OpenClose getClose() {
        return close;
    }

    public void setClose(OpenClose close) {
        this.close = close;
    }

    public OpenClose getOpen() {
        return open;
    }

    public void setOpen(OpenClose open) {
        this.open = open;
    }
}

