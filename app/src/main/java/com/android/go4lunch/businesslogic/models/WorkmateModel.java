package com.android.go4lunch.businesslogic.models;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.valueobjects.WorkmateValueObject;

import java.util.ArrayList;
import java.util.List;

public class WorkmateModel {

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

    private Workmate createVisitor(String id, String name, String urlPhoto) {
        Workmate visitor = new Workmate(name);
        visitor.setId(id);
        visitor.setName(name);
        visitor.setUrlPhoto(urlPhoto);
        return visitor;
    }

    public List<Workmate> extractVisitorsByRestaurantId(List<Selection> selections, String restaurantId) {
        List<Workmate> visitors = new ArrayList<>();
        if (!selections.isEmpty()) {
            for (Selection selection : selections) {
                if (selection.getRestaurantId().equals(restaurantId)) {
                    Workmate visitor = this.createVisitor(
                            selection.getWorkmateId(),
                            selection.getWorkmateName(),
                            selection.getWorkmateUrlPhoto()
                    );
                    visitors.add(visitor);
                }
            }
        }
        return visitors;
    }

    public List<WorkmateValueObject> formatWorkmatesToWorkmateVOs(List<Workmate>workmates) {
        List<WorkmateValueObject> workmateVOs = new ArrayList<>();
        if(!workmates.isEmpty()) {
            for(Workmate workmate: workmates) {
                WorkmateValueObject workmateVO = new WorkmateValueObject(workmate);
                workmateVOs.add(workmateVO);
            }
        }
        return workmateVOs;
    }


}
