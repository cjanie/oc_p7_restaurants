package com.android.go4lunch.write.businesslogic.usecases;

import com.android.go4lunch.HistoricOfSelectionsRepository;
import com.android.go4lunch.models.Restaurant;

import java.util.HashMap;
import java.util.Map;

public class UpdateHistoric {

    private HistoricOfSelectionsRepository historicRepository;

    private Restaurant restaurant;

    public UpdateHistoric(HistoricOfSelectionsRepository historicRepository, Restaurant restaurant) {
        this.historicRepository = historicRepository;
        this.restaurant = restaurant;
    }

    public void handle() {
        this.increment(restaurant);
    }

    private void increment(Restaurant restaurant) {

        Map<Restaurant, Integer> found = null;
        Integer index = null;
/*
        for(int i=0; i<this.historicRepository.findAll().size(); i++) {
            if(this.historicRepository.findAll().get(i).keySet().contains(restaurant)) {
                found = this.historicRepository.findAll().get(i);
                index = i;
                break;
            }
        }

        if(found == null) {
            Map<Restaurant, Integer> map = new HashMap<>();
            map.put(restaurant, 1);
            this.historicRepository.add(map);
        } else if(found != null){
            Map<Restaurant, Integer> map = new HashMap<>();
            map.put(restaurant, found.get(restaurant) + 1);
            this.historicRepository.findAll().set(index, map);
        } else if(this.historicRepository.findAll().isEmpty()) {
            Map<Restaurant, Integer> map = new HashMap<>();
            map.put(restaurant, 1);
            this.historicRepository.add(map);
        }

 */
    }


}
