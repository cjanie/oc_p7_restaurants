package com.android.go4lunch.httpclient.deserialized_entities.distance;

import com.android.go4lunch.httpclient.deserialized_entities.distance.Row;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistanceResponseRoot {
    @SerializedName("destination_addresses")
    @Expose
    private List<String> destinationAddresses;

    @SerializedName("origin_addresses")
    @Expose
    private List<String> origin_addresses;

    @SerializedName("rows")
    @Expose
    private List<Row> rows;

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public List<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(List<String> origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
}
