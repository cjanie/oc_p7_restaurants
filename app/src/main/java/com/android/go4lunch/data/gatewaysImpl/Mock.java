package com.android.go4lunch.data.gatewaysImpl;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.entities.Workmate;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mock {

    private List<Restaurant> restaurants;

    private List<Workmate> workmates;

    public Mock() {
        Restaurant lol = new Restaurant("Chez Lol", "2 rue des lilas");
        lol.setId("1");
        // Prepare planning for a restaurant
        Map<Integer, Map<String, LocalTime>> planning = new HashMap<>();
        // Prepare the times map of the planning
        Map<String, LocalTime> times = new HashMap<>();
        times.put("open", LocalTime.of(8, 30));
        times.put("close", LocalTime.of(20, 0));
        // Set the planning
        while (planning.size() < 7) {
            planning.put(planning.size(), times);
        }
        lol.setGeolocation(new Geolocation(43.568325, 3.906514));
        lol.setPlanning(planning);
        lol.setPhone("0658156166");
        lol.setWebSite("https://devstory.net/12667/android-phone-call");

        Restaurant bon = new Restaurant("Chez Bon", "2, rue des presses");
        bon.setId("2");
        bon.setGeolocation(new Geolocation(43.610769, 3.876716));

        Map<Integer, Map<String, LocalTime>> planningNight = new HashMap<>();
        // Prepare the times map of the planning
        Map<String, LocalTime> nightTimes = new HashMap<>();
        nightTimes.put("open", LocalTime.of(01, 30));
        nightTimes.put("close", LocalTime.of(8, 0));
        // Set the planning
        while (planningNight.size() < 7) {
            planningNight.put(planningNight.size(), nightTimes);
        }
        bon.setPlanning(planningNight);
        bon.setPhone("0658156166");
        bon.setWebSite("https://devstory.net/12667/android-phone-call");

        this.restaurants = Arrays.asList(lol, bon);

        Workmate janie = new Workmate("Janie");
        janie.setId("janie.chun@hotmail.fr");
        janie.setPhone("06 59 12 12 12");
        janie.setEmail("janie.chun@hotmail.fr");
        janie.setUrlPhoto("https://i.pravatar.cc/150?u=a042581f4e29026704d");

        Workmate cyril = new Workmate("Cyril");
        cyril.setId("cyril@hotmail.fr");
        cyril.setPhone("06 59 13 13 13");
        cyril.setEmail("cyril@hotmail.fr");
        cyril.setUrlPhoto("https://i.pravatar.cc/150?u=a042581f4e29026704e");

        Workmate sylvaine = new Workmate("Sylvaine");
        sylvaine.setId("sylvaine@gmail.com");
        sylvaine.setEmail("sylvaine@gmail.com");
        sylvaine.setUrlPhoto("https://i.pravatar.cc/150?u=a042581f4e29026704f");

        this.workmates = Arrays.asList(janie, cyril, sylvaine);
    }

    public List<Restaurant> restaurants() {
        return this.restaurants;
    }

    public Workmate session() {
        return this.workmates.get(0);
    }

    public List<Workmate> workmates() {
        return this.workmates;
    }

    public List<Selection> selections() {
        Restaurant restaurant = restaurants.get(0);

        Selection cyril = new Selection(restaurant.getId(),  "cyril@hotmail.fr");
        Selection sylvaine = new Selection(restaurant.getId(),  "sylvaine@gmail.com");
        return Arrays.asList(cyril, sylvaine);
    }

}
