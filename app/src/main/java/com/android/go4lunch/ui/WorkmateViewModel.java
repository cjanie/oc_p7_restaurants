package com.android.go4lunch.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.read.adapter.InMemorySelectionQuery;
import com.android.go4lunch.read.adapter.InMemoryWorkmateQuery;
import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveWorkmates;
import com.android.go4lunch.read.businesslogic.usecases.WorkmateVO;
import com.android.go4lunch.read.businesslogic.usecases.decorators.SelectionInfoDecoratorForWorkMate;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkmateViewModel extends ViewModel {

    private final RetrieveWorkmates retrieveWorkmates;

    private final InMemorySelectionQuery selectionQuery;

    public WorkmateViewModel() {
        InMemoryWorkmateQuery workmateQuery = new InMemoryWorkmateQuery();
        Workmate janie = new Workmate("Janie");
        Workmate cyril = new Workmate("Cyril");
        workmateQuery.setWorkmates(Arrays.asList(new Workmate[]{janie, cyril}));
        this.retrieveWorkmates = new RetrieveWorkmates(workmateQuery);

        this.selectionQuery = new InMemorySelectionQuery();
        Selection selection = new Selection(new Restaurant("AIOU", "loc"), janie);
        selectionQuery.setSelections(Arrays.asList(new Selection[] {selection}));
    }

    public LiveData<List<WorkmateVO>> list() {
        MutableLiveData<List<WorkmateVO>> mWorkmates = new MutableLiveData<>();
        List<WorkmateVO> workmates = new ArrayList<>();
        for(WorkmateVO w: this.retrieveWorkmates.handle()) {
            w = new SelectionInfoDecoratorForWorkMate(this.selectionQuery, w).decor();
            workmates.add(w);
        }
        mWorkmates.setValue(workmates);
        return mWorkmates;
    }
}
