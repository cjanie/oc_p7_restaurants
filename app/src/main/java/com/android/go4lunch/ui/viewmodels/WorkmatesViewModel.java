package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.gateways_impl.Mock;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.GetRestaurantByIdUseCase;
import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;
import com.android.go4lunch.usecases.GetWorkmatesUseCase;
import com.android.go4lunch.usecases.exceptions.NotFoundException;
import com.android.go4lunch.usecases.models.WorkmateModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class WorkmatesViewModel extends ViewModel {

    private final GetWorkmatesUseCase getWorkmatesUseCase;

    private final GetWorkmateSelectionUseCase getWorkmateSelectionUseCase;

    private final GetRestaurantByIdUseCase getRestaurantByIdUseCase;

    private final MutableLiveData<List<WorkmateModel>> workmates;

    public WorkmatesViewModel(
            GetWorkmatesUseCase getWorkmatesUseCase,
            GetWorkmateSelectionUseCase getWorkmateSelectionUseCase,
            GetRestaurantByIdUseCase getRestaurantByIdUseCase

    ) {
        this.getWorkmatesUseCase = getWorkmatesUseCase;
        this.getWorkmateSelectionUseCase = getWorkmateSelectionUseCase;
        this.getRestaurantByIdUseCase = getRestaurantByIdUseCase;

        this.workmates = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<WorkmateModel>> list() throws NotFoundException {
        this.fetchWorkmates();
        return this.workmates;
    }

    private void fetchWorkmates() {
        List<Workmate> workmatesResults = new ArrayList<>();
        this.getWorkmatesUseCase.handle().subscribe(workmatesResults::addAll);

        List<WorkmateModel> workmatesModels = new ArrayList<>();
        if(!workmatesResults.isEmpty()) {
            for(Workmate w: workmatesResults) {
                WorkmateModel workmateModel = this.decorWorkmate(w);
                workmatesModels.add(workmateModel);
            }
        }
        this.workmates.postValue(workmatesModels);

    }


    private WorkmateModel decorWorkmate(Workmate workmate) {
        WorkmateModel workmateModel = new WorkmateModel(workmate);
        List<Selection> selectionResults = new ArrayList<>();
        this.getWorkmateSelectionUseCase.handle(workmate.getId()).subscribe(selectionResults::add);
        if(!selectionResults.isEmpty()) {
            String restaurantId = selectionResults.get(0).getRestaurantId();
            try {
                List<Restaurant> restaurantResults = new ArrayList<>();
                this.getRestaurantByIdUseCase.handle(restaurantId).subscribe(restaurantResults::add);
                workmateModel.setSelection(restaurantResults.get(0));
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        return workmateModel;

    }
}
