package com.android.go4lunch.usecases;

import com.android.go4lunch.exceptions.NullDistanceResponseException;
import com.android.go4lunch.in_memory_repositories.InMemoryDistanceRepository;
import com.android.go4lunch.usecases.decorators.DistanceInfoDecorator;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DistanceInfoDecoratorTest {

    @Test
    public void shouldReturnANumberWhenInformationIsAvailable() throws NullDistanceResponseException {
        InMemoryDistanceRepository inMemoryDistanceRepository = new InMemoryDistanceRepository(Observable.just(10L));
        DistanceInfoDecorator distanceInfoDecorator = new DistanceInfoDecorator(inMemoryDistanceRepository);
        Geolocation myposition = new Geolocation(11.111,999.897);
        Restaurant restaurant = new Restaurant("A la crême", "2 allée des Mimosas");
        restaurant.setGeolocation(new Geolocation(1222.111, 111.11));
        Observable<RestaurantVO> restaurantVO = distanceInfoDecorator.decor(myposition, new RestaurantVO(restaurant));
        List<RestaurantVO> results = new ArrayList<>();
        restaurantVO.subscribe(results::add);
        assertThat(results, notNullValue());
        assert(results.size() == 1);
        assert(results.get(0).getDistanceInfo() == 10L);
    }

    @Test
    public void shouldReturn20WhenDistanceIs20() throws NullDistanceResponseException {
        InMemoryDistanceRepository inMemoryDistanceRepository = new InMemoryDistanceRepository(Observable.just(20L));
        DistanceInfoDecorator distanceInfoDecorator = new DistanceInfoDecorator(inMemoryDistanceRepository);
        Geolocation myposition = new Geolocation(11.111,999.897);
        Restaurant restaurant = new Restaurant("A la crême", "2 allée des Mimosas");
        restaurant.setGeolocation(new Geolocation(1222.111, 111.11));
        Observable<RestaurantVO> restaurantVO = distanceInfoDecorator.decor(myposition, new RestaurantVO(restaurant));
        List<RestaurantVO> results = new ArrayList<>();
        restaurantVO.subscribe(results::add);
        assertThat(results, notNullValue());
        assert(results.size() == 1);
        assert(results.get(0).getDistanceInfo() == 20L);
    }

    @Test(expected = NullDistanceResponseException.class)
    public void shouldReturnNullWhenDistanceIsUnAvailable() throws NullDistanceResponseException {
        InMemoryDistanceRepository inMemoryDistanceRepository = new InMemoryDistanceRepository(null);
        DistanceInfoDecorator distanceInfoDecorator = new DistanceInfoDecorator(inMemoryDistanceRepository);
        Geolocation myposition = new Geolocation(11.111,999.897);
        Restaurant restaurant = new Restaurant("A la crême", "2 allée des Mimosas");
        restaurant.setGeolocation(new Geolocation(1222.111, 111.11));
        Observable<RestaurantVO> restaurantVO = distanceInfoDecorator.decor(myposition, new RestaurantVO(restaurant));
    }

    @Test
    public void shouldReturnNullWhenRestaurantGeolocationIsNotAvailable() throws NullDistanceResponseException {
        InMemoryDistanceRepository inMemoryDistanceRepository = new InMemoryDistanceRepository(Observable.just(12L));
        DistanceInfoDecorator distanceInfoDecorator = new DistanceInfoDecorator(inMemoryDistanceRepository);
        Geolocation myposition = new Geolocation(11.111,999.897);
        Restaurant restaurant = new Restaurant("A la crême", "2 allée des Mimosas");
        // Geolocation is not set
        Observable<RestaurantVO> restaurantVO = distanceInfoDecorator.decor(myposition, new RestaurantVO(restaurant));
        List<RestaurantVO> results = new ArrayList<>();
        restaurantVO.subscribe(results::add);
        assertThat(results, notNullValue());
        assert(results.size() == 1);
        assert(results.get(0).getDistanceInfo() == null);
    }

}
