package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.adapter.InMemoryWorkmateQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

import org.junit.Test;

import java.util.Arrays;

public class RetrieveWorkmatesTest {

    @Test
    public void shouldReturn1WhenOneWorkmateIsAvailable() {
        InMemoryWorkmateQuery workmateQuery = new InMemoryWorkmateQuery();
        workmateQuery.setWorkmates(Arrays.asList(new Workmate[]{new Workmate()}));
        assert(new RetrieveWorkmates(workmateQuery).handle().size() == 1);
    }

    @Test
    public void shouldReturn2WhenAreWorkmateIsAvailable() {
        InMemoryWorkmateQuery workmateQuery = new InMemoryWorkmateQuery();
        workmateQuery.setWorkmates(Arrays.asList(new Workmate[]{new Workmate(), new Workmate()}));
        assert(new RetrieveWorkmates(workmateQuery).handle().size() == 2);
    }

    @Test
    public void shouldBeEmptyWhenThereIsNoWorkmate() {
        InMemoryWorkmateQuery workmateQuery = new InMemoryWorkmateQuery();
        assert(new RetrieveWorkmates(workmateQuery).handle().isEmpty());
    }
}
