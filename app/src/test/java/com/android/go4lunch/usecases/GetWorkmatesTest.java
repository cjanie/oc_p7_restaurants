package com.android.go4lunch.usecases;

import com.android.go4lunch.in_memory_repositories.InMemoryWorkmateRepository;
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
        InMemoryWorkmateRepository inMemoryWorkmateRepository = new InMemoryWorkmateRepository();
        inMemoryWorkmateRepository.setWorkmates(Arrays.asList(new Workmate("Janie")));
        GetWorkmates getWorkmates = new GetWorkmates(inMemoryWorkmateRepository);

        Observable<List<WorkmateVO>> observableWormates = getWorkmates.list();
        List<WorkmateVO> results = new ArrayList<>();
        observableWormates.subscribe(results::addAll);
        assert(results.size() == 1);
    }

    @Test
    public void listShouldHave2WorkmateWhenThereIs2Available() {
        InMemoryWorkmateRepository inMemoryWorkmateRepository = new InMemoryWorkmateRepository();
        inMemoryWorkmateRepository.setWorkmates(Arrays.asList(
                new Workmate("Janie"),
                new Workmate("Cyril")
        ));
        GetWorkmates getWorkmates = new GetWorkmates(inMemoryWorkmateRepository);
        Observable<List<WorkmateVO>> observableWormates = getWorkmates.list();
        List<WorkmateVO> results = new ArrayList<>();
        observableWormates.subscribe(results::addAll);
        assert(results.size() == 2);
    }

    @Test
    public void listShouldBeEmptyWhenNoAvailableWorkmate() {
        InMemoryWorkmateRepository inMemoryWorkmateRepository = new InMemoryWorkmateRepository();
        // dont set any workmate in the repository
        GetWorkmates getWorkmates = new GetWorkmates(inMemoryWorkmateRepository);
        Observable<List<WorkmateVO>> observableWormates = getWorkmates.list();
        List<WorkmateVO> results = new ArrayList<>();
        observableWormates.subscribe(results::addAll);
        assert(results.isEmpty());
    }

}
