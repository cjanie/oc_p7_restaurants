package com.android.go4lunch.usecases;

import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class GetRestaurantVisitorsTest {

    class GetRestaurantVisitors {

        private RestaurantVisitorsGateway restaurantVisitorsGateway;

        public GetRestaurantVisitors(RestaurantVisitorsGateway restaurantVisitorsGateway) {
            this.restaurantVisitorsGateway = restaurantVisitorsGateway;
        }

        public List<Workmate> handle() {
            return this.restaurantVisitorsGateway.getVisitors();
        }

    }

    interface RestaurantVisitorsGateway {
        List<Workmate> getVisitors();
    }

    class InMemoryRestaurantVisitorGateway implements RestaurantVisitorsGateway {

        private List<Workmate> visitors;

        public InMemoryRestaurantVisitorGateway() {
            this.visitors = new ArrayList<>();
        }

        public void setVisitors(List<Workmate> visitors) {
            this.visitors = visitors;
        }

        @Override
        public List<Workmate> getVisitors() {
            return this.visitors;
        }
    }

    @Test
    public void returnsVisitorsWhenThereAreSome () {
        InMemoryRestaurantVisitorGateway restaurantVisitorGateway = new InMemoryRestaurantVisitorGateway();
        List<Workmate> visitors = new ArrayList<>();
        visitors.add(new Workmate("Janie"));
        restaurantVisitorGateway.setVisitors(visitors);
        GetRestaurantVisitors getRestaurantVisitors = new GetRestaurantVisitors(restaurantVisitorGateway);
        assertFalse(getRestaurantVisitors.handle().isEmpty());
    }

    @Test
    public void doesNotReturnAnyVisitorWhenThereIsNone () {
        InMemoryRestaurantVisitorGateway restaurantVisitorGateway = new InMemoryRestaurantVisitorGateway();
        GetRestaurantVisitors getRestaurantVisitors = new GetRestaurantVisitors(restaurantVisitorGateway);
        List<Workmate> visitors = getRestaurantVisitors.handle();
        assert(visitors.isEmpty());
    }

}
