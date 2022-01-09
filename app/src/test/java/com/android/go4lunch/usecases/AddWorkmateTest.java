package com.android.go4lunch.usecases;

import com.android.go4lunch.in_memory_repositories.InMemoryWorkmateRepository;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.repositories.WorkmateRepository;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class AddWorkmateTest {

    @Test
    public void shouldIncrementListWhenNewWorkmate() {
        InMemoryWorkmateRepository workmateRepository = new InMemoryWorkmateRepository();
        // SUT
        Workmate workmate = new Workmate("janie");
        workmate.setEmail("janie@gmail.com");
        new AddWorkmate(workmateRepository).save(workmate);
        // Check Result in repository
        Observable<List<Workmate>> observableWorkmates = workmateRepository.getWorkmates();
        List<Workmate> results = new ArrayList<>();
        observableWorkmates.subscribe(results::addAll);
        assert(results.size() == 1);
    }

    @Test
    public void shouldBe2WorkmatesWhenThoAreAdded() {
        InMemoryWorkmateRepository workmateRepository = new InMemoryWorkmateRepository();
        // SUT
        Workmate workmate1 = new Workmate("janie");
        workmate1.setEmail("janie@gmail.com");
        new AddWorkmate(workmateRepository).save(workmate1);

        Workmate workmate2 = new Workmate("cyril");
        workmate2.setEmail("cyril@gmail.com");
        new AddWorkmate(workmateRepository).save(workmate2);
        // Check Result in repository
        Observable<List<Workmate>> observableWorkmates = workmateRepository.getWorkmates();
        List<Workmate> results = new ArrayList<>();
        observableWorkmates.subscribe(results::addAll);
        assert(results.size() == 2);
    }

    @Test
    public void shouldNotBeAddedWhenAlreadyInTheList() {
        InMemoryWorkmateRepository workmateRepository = new InMemoryWorkmateRepository();
        // SUT
        Workmate workmateFirstTime = new Workmate("janie");
        workmateFirstTime.setEmail("janie@gmail.com");
        new AddWorkmate(workmateRepository).save(workmateFirstTime);

        Workmate workmateSecondTime = new Workmate("janie");
        workmateSecondTime.setEmail("janie@gmail.com");
        new AddWorkmate(workmateRepository).save(workmateSecondTime);

        Workmate workmate2 = new Workmate("cyril");
        workmate2.setEmail("cyril@gmail.com");
        new AddWorkmate(workmateRepository).save(workmate2);
        // Check Result in repository
        Observable<List<Workmate>> observableWorkmates = workmateRepository.getWorkmates();
        List<Workmate> results = new ArrayList<>();
        observableWorkmates.subscribe(results::addAll);
        assert(results.size() == 2);
    }

    @Test
    public void workmakeNameShouldBeUpdatedIfChanged() {
        InMemoryWorkmateRepository workmateRepository = new InMemoryWorkmateRepository();
        // register as Janie first time
        Workmate workmateFirstTime = new Workmate("janie");
        workmateFirstTime.setEmail("janie@gmail.com");
        new AddWorkmate(workmateRepository).save(workmateFirstTime);
        // SUT
        // register as Jenny second time
        Workmate workmateSecondTime = new Workmate("jenny");
        workmateSecondTime.setEmail("janie@gmail.com");
        new AddWorkmate(workmateRepository).save(workmateSecondTime);

        // Check Result in repository
        Observable<List<Workmate>> observableWorkmates = workmateRepository.getWorkmates();
        List<Workmate> results = new ArrayList<>();
        observableWorkmates.subscribe(results::addAll);
        assert(results.size() == 1);
        assert(results.get(0).getName().equals("jenny"));
    }
}
