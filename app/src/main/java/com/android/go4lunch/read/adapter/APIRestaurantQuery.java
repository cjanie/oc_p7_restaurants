package com.android.go4lunch.read.adapter;

import com.android.go4lunch.read.businesslogic.gateways.RestaurantQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class APIRestaurantQuery implements RestaurantQuery {

    // Get open source // + https://occitanie.opendatasoft.com/map/+abb27becbb27403b/edit/
    private final String URL = "https://data.montpellier3m.fr/sites/default/files/ressources/OSM_Metropole_restauration_bar.json";

    @Override
    public List<Restaurant> findAll() {
        return new ArrayList<>(); // TODO
    }
}
