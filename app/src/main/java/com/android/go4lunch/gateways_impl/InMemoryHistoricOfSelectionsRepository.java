package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.HistoricOfSelectionsRepository;
import com.android.go4lunch.models.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class InMemoryHistoricOfSelectionsRepository implements HistoricOfSelectionsRepository {

    List<Map<Restaurant, Integer>> list;

    public InMemoryHistoricOfSelectionsRepository() {
        this.list = new ArrayList<>();
    }

    public void setList(List<Map<Restaurant, Integer>> list) {
        this.list = list;
    }

    @Override
    public Observable<List<Map<Restaurant, Integer>>> findAll() {
        return Observable.just(this.list);
    }

    @Override
    public Observable<Integer> getCount(Restaurant restaurant) {
        int count = 0;
        for(Map map: this.list) {

            if(map.keySet().contains(restaurant)) {
                count = (int) map.get(restaurant);
            }
        }
        return Observable.just(count);
    }

    /*
    public void increment(Restaurant restaurant) {

        Map<Restaurant, Integer> found = null;
        Integer index = null;

        for(int i=0; i<this.list.size(); i++) {
            if(list.get(i).keySet().contains(restaurant)) {
                found = list.get(i);
                index = i;
                break;
            }
        }

        if(found == null) {
            Map<Restaurant, Integer> map = new HashMap<>();
            map.put(restaurant, 1);
            this.list.add(map);
        } else if(found != null){
            Map<Restaurant, Integer> map = new HashMap<>();
            map.put(restaurant, found.get(restaurant) + 1);
            this.list.set(index, map);
        } else if(this.list.isEmpty()) {
            Map<Restaurant, Integer> map = new HashMap<>();
            map.put(restaurant, 1);
            this.list.add(map);
        }
    }

     */

    @Override
    public void add(Map<Restaurant, Integer> map) {
        this.list.add(map);
    }
}
