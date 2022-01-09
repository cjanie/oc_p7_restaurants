package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import com.android.go4lunch.apiFirebase.entities.UserService;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.repositories.WorkmateRepository;
import com.android.go4lunch.usecases.AddWorkmate;

public class SignInViewModel extends ViewModel {

    private AddWorkmate addWorkmate;

    public SignInViewModel() {
        this.addWorkmate = new AddWorkmate(new WorkmateRepository());
    }

    public void createSession() {
        UserService userService = new UserService();
        userService.createUser();
    }
}
