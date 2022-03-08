package com.android.go4lunch.usecases;

import com.android.go4lunch.in_memory_repositories.InMemoryWorkmateGateway;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.models_vo.WorkmateVO;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class GetWorkmatesTest {

    @Test
    public void listShouldHave1WorkmateWhenThereIs1Available() {
        InMemoryWorkmateGateway inMemoryWorkmateGateway = new InMemoryWorkmateGateway();
        inMemoryWorkmateGateway.setWorkmates(Arrays.asList(new Workmate("Janie")));
        GetWorkmates getWorkmates = new GetWorkmates(inMemoryWorkmateGateway);

        Observable<List<WorkmateVO>> observableWormates = getWorkmates.list();
        List<WorkmateVO> results = new ArrayList<>();
        observableWormates.subscribe(results::addAll);
        assert(results.size() == 1);
    }

    @Test
    public void listShouldHave2WorkmateWhenThereIs2Available() {
        InMemoryWorkmateGateway inMemoryWorkmateGateway = new InMemoryWorkmateGateway();
        inMemoryWorkmateGateway.setWorkmates(Arrays.asList(
                new Workmate("Janie"),
                new Workmate("Cyril")
        ));
        GetWorkmates getWorkmates = new GetWorkmates(inMemoryWorkmateGateway);
        Observable<List<WorkmateVO>> observableWormates = getWorkmates.list();
        List<WorkmateVO> results = new ArrayList<>();
        observableWormates.subscribe(results::addAll);
        assert(results.size() == 2);
    }

    @Test
    public void listShouldBeEmptyWhenNoAvailableWorkmate() {
        InMemoryWorkmateGateway inMemoryWorkmateGateway = new InMemoryWorkmateGateway();
        // dont set any workmate in the repository
        GetWorkmates getWorkmates = new GetWorkmates(inMemoryWorkmateGateway);
        Observable<List<WorkmateVO>> observableWormates = getWorkmates.list();
        List<WorkmateVO> results = new ArrayList<>();
        observableWormates.subscribe(results::addAll);
        assert(results.isEmpty());
    }

}
