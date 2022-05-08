package com.android.go4lunch.usecases;

import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.in_memory_gateways.InMemorySessionGateway;
import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetSessionUseCaseTest {

    @Test
    public void returnsTheWorkmateOfTheCurrentSession() throws NoWorkmateForSessionException {
        Workmate workmate = new Workmate("Janie");
        InMemorySessionGateway inMemorySessionGateway = new InMemorySessionGateway();
        inMemorySessionGateway.setWorkmate(workmate);
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(inMemorySessionGateway);
        Observable<Workmate> observableWorkmate = getSessionUseCase.handle();
        List<Workmate> results = new ArrayList<>();
        observableWorkmate.subscribe(results::add);
        assert(results.get(0).getName().equals("Janie"));
    }

    @Test(expected = NoWorkmateForSessionException.class)
    public void handlesErrorWhenNoWorkmateForSession() throws NoWorkmateForSessionException {
        InMemorySessionGateway inMemorySessionGateway = new InMemorySessionGateway();
        // Dont set repository with workmate
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(inMemorySessionGateway);
        getSessionUseCase.handle();
    }

}
