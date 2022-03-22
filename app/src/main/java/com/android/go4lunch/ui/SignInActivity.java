package com.android.go4lunch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;

import com.android.go4lunch.R;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

public class SignInActivity extends BaseActivity {

    // https://firebaseopensource.com/projects/firebase/firebaseui-android/auth/readme/
    // 1. Build a sign in Intent
    // 2. Launch the intent using ResultActivityLauncher
    // 3. Handle success or failure

    private final String TAG = "SIGN IN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.startActivityForResult();
    }

    private void startActivityForResult() {

        ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                result -> {
                    // Handle the Firebase AuthUI Authentication Result returned from launching the Intent;
                    if(result.getResultCode() == this.RESULT_OK) {
                        // Successfully signed in: Go to Main Activity
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Sign in failed
                        IdpResponse response = result.getIdpResponse();
                        if (response == null) {
                            // User pressed back button for cancel
                            Log.e(this.TAG, this.getResources().getString(R.string.sign_in_cancelled));
                            Snackbar.make(getWindow().getDecorView().getRootView(), R.string.sign_in_cancelled, Snackbar.LENGTH_LONG).show();
                            return;
                        }
                        if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                            Log.e(this.TAG, "Activity result error: " + this.getResources().getString(R.string.no_internet_connection));
                            Snackbar.make(getWindow().getDecorView().getRootView(), R.string.no_internet_connection, Snackbar.LENGTH_LONG).show();
                            return;
                        }
                        // Default error
                        Log.e(this.TAG, "Activity result error: ", response.getError());
                        Snackbar.make(getWindow().getDecorView().getRootView(), R.string.sign_in_failure, Snackbar.LENGTH_LONG).show();
                    }

                });

        Intent signInIntent = this.buildSignInIntent();
        signInLauncher.launch(signInIntent);

    }

    private Intent buildSignInIntent() {
        // Choose authentication providers Google, Facebook
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        // Intent to return
        return AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.ic_logo)
                        .build();
    }
}
