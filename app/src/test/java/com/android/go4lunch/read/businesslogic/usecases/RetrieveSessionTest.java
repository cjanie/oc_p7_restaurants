package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.adapter.InMemorySessionQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

import org.junit.Test;

import static org.junit.Assert.assertNull;

public class RetrieveSessionTest {

    @Test
    public void shouldReturnWorkmateInSession() {
        Workmate workmate = new Workmate("Janie");
        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        assertNull(new RetrieveSession(sessionQuery).handle());

        sessionQuery.setWorkmate(workmate);
        assert(new RetrieveSession(sessionQuery).handle().equals(workmate));
    }

}
