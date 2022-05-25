package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.usecases.SaveWorkmateUseCase;

public class SignInViewModel extends ViewModel {

    private SaveWorkmateUseCase saveWorkmateUseCase;

    public SignInViewModel(SaveWorkmateUseCase saveWorkmateUseCase) {
        this.saveWorkmateUseCase = saveWorkmateUseCase;
    }

    public void saveWorkmate(String id, String name, String email, String phone, String urlPhoto) {
        Workmate workmate = new Workmate(name);
        workmate.setId(id);
        workmate.setEmail(email);
        workmate.setPhone(phone);
        workmate.setUrlPhoto(urlPhoto);
        this.saveWorkmateUseCase.handle(workmate);
    }
}
