package com.android.go4lunch.usecases;

import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.gateways_impl.InMemorySessionGateway;
import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetSessionTest {

    @Test
    public void shouldGetWorkmateOfCurrentSession() throws NoWorkmateForSessionException {
        Workmate workmate = new Workmate("Janie");
        InMemorySessionGateway inMemorySessionGateway = new InMemorySessionGateway();
        inMemorySessionGateway.setWorkmate(workmate);
        GetSession getSession = new GetSession(inMemorySessionGateway);
        Observable<Workmate> observableWorkmate = getSession.getWorkmate();
        List<Workmate> results = new ArrayList<>();
        observableWorkmate.subscribe(results::add);
        assert(results.get(0).getName().equals("Janie"));
    }

    @Test(expected = NoWorkmateForSessionException.class)
    public void shouldSendExceptionWhenNoWorkmateForSession() throws NoWorkmateForSessionException {
        InMemorySessionGateway inMemorySessionGateway = new InMemorySessionGateway();
        // Dont set repository with workmate
        GetSession getSession = new GetSession(inMemorySessionGateway);
        Observable<Workmate> observableWorkmate = getSession.getWorkmate();
    }
}
