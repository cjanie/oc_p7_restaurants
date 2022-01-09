package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import com.android.go4lunch.repositories.InMemoryCurrentSelectionsRepository;

import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;


public class WorkmateViewModel extends ViewModel {

    //private final RetrieveWorkmates retrieveWorkmates;

    private final InMemoryCurrentSelectionsRepository selectionQuery;

    public WorkmateViewModel() {
        //InMemoryWorkmateQuery workmateQuery = new InMemoryWorkmateQuery();
        Workmate janie = new Workmate("Janie");
        Workmate cyril = new Workmate("Cyril");
        //workmateQuery.setWorkmates(Arrays.asList(new Workmate[]{janie, cyril}));
        //this.retrieveWorkmates = new RetrieveWorkmates(workmateQuery);
        this.selectionQuery = new InMemoryCurrentSelectionsRepository();
        Selection selection = new Selection(new Restaurant("AIOU", "loc"), janie);
        //selectionQuery.setSelections(Arrays.asList()new Selection[] {selection}));
    }
/*
    public LiveData<List<WorkmateVO>> list() {
        MutableLiveData<List<WorkmateVO>> mWorkmates = new MutableLiveData<>();
        List<WorkmateVO> workmates = new ArrayList<>();
        for(WorkmateVO w: this.retrieveWorkmates.handle()) {
            w = new SelectionInfoDecoratorForWorkMate(this.selectionQuery).decor(w);
            workmates.add(w);
        }
        mWorkmates.setValue(workmates);
        return mWorkmates;
    }

 */
}
