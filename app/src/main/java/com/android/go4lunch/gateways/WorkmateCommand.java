package com.android.go4lunch.gateways;

import com.android.go4lunch.models.Workmate;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public interface WorkmateCommand extends WorkMateQuery {

    void setWorkmates(@NonNull Observable<List<Workmate>> workmates);

}
