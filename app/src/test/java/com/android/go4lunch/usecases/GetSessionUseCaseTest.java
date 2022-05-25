package com.android.go4lunch.usecases;

import static org.junit.Assert.assertNull;

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
        Workmate session = new Workmate("Janie");
        InMemorySessionGateway inMemorySessionGateway = new InMemorySessionGateway();
        inMemorySessionGateway.setSession(session);
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(inMemorySessionGateway);
        getSessionUseCase.handle().subscribe(sessionResult -> {
            assert(sessionResult.getName().equals("Janie"));
        });
    }

    @Test
    public void handlesErrorWhenNoWorkmateForSession() throws NoWorkmateForSessionException {
        InMemorySessionGateway inMemorySessionGateway = new InMemorySessionGateway();
        // Dont set repository with workmate
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(inMemorySessionGateway);
        getSessionUseCase.handle().subscribe(sessionResult -> {
            assertNull(sessionResult);
        });
    }

}
