package com.aitec.appbeber.init.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aitec.appbeber.MyApplication;
import com.aitec.appbeber.R;
import com.aitec.appbeber.init.login.LoginPresenter;
import com.aitec.appbeber.init.login.events.LoginEvent;
import com.aitec.appbeber.init.registrerUser.ui.RegistrerUserActivity;
import com.aitec.appbeber.main.ui.MainActivity;
import com.aitec.appbeber.models.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginActivity";
    private static final int SIGN_IN_GOOGLE = 1;
    MyApplication application;
    @Inject
    LoginPresenter presenter;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rv_chip_login)
    RelativeLayout rvChipLoginFb;
    @BindView(R.id.rv_chip_login_gm)
    RelativeLayout rvChipLoginGm;
    private User user;
    // login con google
    private GoogleApiClient googleApiClient;

    //login con facebook
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        application = (MyApplication) getApplication();
        setupInjection();
        inicializate_google_singin();
        inicializate_facebook_singin();
    }


    private void inicializate_facebook_singin() {
        callbackManager = CallbackManager.Factory.create();
        //btnLoginFb.setReadPermissions("email", "public_profile");
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                if (profile.getFirstName() != null && profile.getLastName() != null) {
                    User user = new User();

                    user.setName(profile.getFirstName());
                    user.setLastname(profile.getLastName());
                    user.setUrl_photo(profile.getProfilePictureUri(200, 200).toString());
                    user.setToken(application.getSharePreferences().getString("token", ""));

                    presenter.singInCredential(loginResult.getAccessToken().getToken(), LoginEvent.login_facebook, user);
                } else {
                    showMessage("Tenemos problemas al obtener su información de Facebook, intentelo nuevamente");
                }
            }

            @Override
            public void onCancel() {
                Log.w(TAG, "Fb login cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG, "Fb error");
                //error.printStackTrace();
                showMessage("Tenemos Problemas para conectarnos con Facebook, intentelo nuevamente");
            }
        });
    }

    private void setupInjection() {
        application.getLoginComponent(this).inject(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
        presenter.subscribe();
    }

    @Override
    public void navigateToRegisterUser() {
        Intent intent = new Intent(this, RegistrerUserActivity.class);
        intent.putExtra("user", user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    public void inicializate_google_singin() {
        /*Socitilamos el token y email*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToMainScreen() {
        // googleApiClient.clearDefaultAccountAndReconnect();
        //googleApiClient.disconnect();
        Log.e("LOGIN", "vamos a iniciar session");
        Intent intentMain = new Intent(this, MainActivity.class);
        intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentMain);
    }


    @OnClick({R.id.rv_chip_login, R.id.rv_chip_login_gm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rv_chip_login:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                break;
            case R.id.rv_chip_login_gm:
                Auth.GoogleSignInApi.signOut(googleApiClient);
                showProgress(true);
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_GOOGLE);
                break;
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        showProgress(false);
        switch (requestCode) {
            case SIGN_IN_GOOGLE:
                GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                signInGoogleFirebase(googleSignInResult);
                break;
            default:
                //callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
        }

        //2680114

    }

    private void signInGoogleFirebase(GoogleSignInResult googleSignInResult) {
        if (googleSignInResult.isSuccess()) {
            GoogleSignInAccount acct = googleSignInResult.getSignInAccount();
            User user = new User();
            user.setName(acct.getGivenName());
            user.setLastname(acct.getFamilyName());
            user.setEmail(acct.getEmail());
            user.setUrl_photo(acct.getPhotoUrl().toString());
            user.setToken(application.getSharePreferences().getString("token", ""));
            presenter.singInCredential(acct.getIdToken(), LoginEvent.login_google, user);
        } else {
            showMessage("No se puedo iniciar session con google");
            //Log.e(TAG, "mal" + googleSignInResult.getStatus().toString());
        }
    }

    @Override
    public void cleanAccount() {
        //Auth.GoogleSignInApi.signOut(googleApiClient);
        /*
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        // Hide the sign out buttons, show the sign in button.
                    }
                });
                */
    }
}
