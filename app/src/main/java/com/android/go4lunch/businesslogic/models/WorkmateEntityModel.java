package com.android.go4lunch.businesslogic.models;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Workmate;

public class WorkmateEntityModel {

    public Workmate createWorkmate(String id, String name, String email, String urlPhoto) {
        Workmate workmate = new Workmate(name);
        workmate.setId(id);
        workmate.setEmail(email);
        workmate.setUrlPhoto(urlPhoto);
        return workmate;
    }

    public Workmate createWorkmate(String id, String name, String email, String urlPhoto, String phone) {
        Workmate workmate = this.createWorkmate(id, name, email, urlPhoto);
        workmate.setPhone(phone);
        return workmate;
    }

    public Workmate setWorkmateSelectedRestaurant(Workmate workmate, Restaurant restaurant) {
        Workmate workmateCopy = workmate;
        workmateCopy.setSelectedRestaurant(restaurant);
        return workmate;
    }
}
