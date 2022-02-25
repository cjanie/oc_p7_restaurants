package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import com.android.go4lunch.apis.apiFirebase.UserRepository;
import com.android.go4lunch.gateways_impl.WorkmateRepository;
import com.android.go4lunch.usecases.AddWorkmate;

public class SignInViewModel extends ViewModel {

    private AddWorkmate addWorkmate;

    public SignInViewModel() {
        this.addWorkmate = new AddWorkmate(new WorkmateRepository());
    }

    public void createSession() {
        UserRepository userRepository = new UserRepository();
        userRepository.createUser();
    }
}
