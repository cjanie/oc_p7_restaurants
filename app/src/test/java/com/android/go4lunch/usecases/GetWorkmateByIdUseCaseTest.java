package com.android.go4lunch.usecases;

import com.android.go4lunch.in_memory_repositories.InMemoryWorkmateGateway;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;

public class GetWorkmateByIdUseCaseTest {

    @Test
    public void returnsWorkmateIfExists() throws NotFoundException {
        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        Workmate janie = new Workmate("Janie");
        janie.setId("1");
        workmateGateway.setWorkmates(Arrays.asList(janie));

        GetWorkmateByIdUseCase getWorkmateByIdUsecase = new GetWorkmateByIdUseCase(workmateGateway);
        List<Workmate> workmateResults = new ArrayList<>();
        getWorkmateByIdUsecase.handle("1").subscribe(workmateResults::add);
        assert(workmateResults.get(0).getId() == "1");
    }

    @Test
    public void returnsNothingIfNotExits() throws NotFoundException {
        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        GetWorkmateByIdUseCase getWorkmateByIdUsecase = new GetWorkmateByIdUseCase(workmateGateway);
        List<Workmate> workmateResults = new ArrayList<>();
        assertThrows(NotFoundException.class, () -> {
            getWorkmateByIdUsecase.handle("3");
        });
    }
}
