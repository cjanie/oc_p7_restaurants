package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import java.util.Arrays;
import java.util.List;

public class Mock {

    private List<Workmate> workmates;

    public Mock() {
        Workmate janie = new Workmate("Janie");
        janie.setId("1");
        janie.setPhone("06 59 12 12 12");
        janie.setEmail("janie.chun@hotmail.fr");
        janie.setUrlPhoto("https://i.pravatar.cc/150?u=a042581f4e29026704d");

        Workmate cyril = new Workmate("Cyril");
        cyril.setId("2");
        cyril.setPhone("06 59 13 13 13");
        cyril.setEmail("cyril@hotmail.fr");
        cyril.setUrlPhoto("https://i.pravatar.cc/150?u=a042581f4e29026704e");

        Workmate sylvaine = new Workmate("Sylvaine");
        sylvaine.setId("3");
        sylvaine.setEmail("sylvaine@gmail.com");
        sylvaine.setUrlPhoto("https://i.pravatar.cc/150?u=a042581f4e29026704f");

        this.workmates = Arrays.asList(janie, cyril, sylvaine);
    }

    public Workmate session() {
        return this.workmates.get(0);
    }

    public List<Workmate> workmates() {
        return this.workmates;
    }

    public List<Selection> selections() {
        Selection cyril = new Selection("1", "Chez Lol", "2", "Cyril");

        return Arrays.asList(cyril);
    }

}
