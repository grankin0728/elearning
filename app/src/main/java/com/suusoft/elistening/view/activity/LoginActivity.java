package com.suusoft.elistening.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseActivity;
import com.suusoft.elistening.configs.Global;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.listener.IConfirmation;
import com.suusoft.elistening.listener.ModelManagerListener;
import com.suusoft.elistening.model.User;
import com.suusoft.elistening.modelmanager.ModelManager;
import com.suusoft.elistening.network.NetworkUtility;
import com.suusoft.elistening.retrofit.APIService;
import com.suusoft.elistening.retrofit.ApiUtils;
import com.suusoft.elistening.retrofit.respone.ResponeUser;
import com.suusoft.elistening.util.AppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final int RC_GOOGLE_SIGN_IN = 9001;
    private static final int RC_PERMISSIONS = 1;
    private EditText edEmail, edPassword;
    private Button btnFacebook, btnGoogle, btnLogin;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;
    private RequestQueue mRequestQueue;
    private View mClickedView; // Keep button which was just clicked(google, facebook or login)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = Volley.newRequestQueue(self);
        initGoogleApiclien();
    }

    @Override
    protected ToolbarType getToolbarType() {
        return ToolbarType.NONE;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.activity_login;
    }

    @Override
    protected void getExtraData(Intent intent) {

    }

    @Override
    protected void initilize() {

    }

    @Override
    protected void initView() {
        edEmail     = findViewById(R.id.ed_email);
        edPassword  = findViewById(R.id.ed_password);
        btnFacebook = findViewById(R.id.btn_facebook);
        btnGoogle   = findViewById(R.id.btn_google);
        btnLogin    = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);

    }



    @Override
    protected void onViewCreated() {

    }


    private void initGoogleApiclien(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(self).addApi(Auth.GOOGLE_SIGN_IN_API, gso).
                enableAutoManage(LoginActivity.this,this)
                .build();
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            mClickedView = btnLogin;
            if (isValid ()) {
                if (Global.isGranted(this, new String[]{Manifest.permission.READ_PHONE_STATE}, RC_PERMISSIONS, "")) {
                    loginUser();
                }
            }
        }else if (view == btnGoogle) {
            //mClickedView = btnGoogle;
            signInGoogle ();
            // Check permissions
//            if (Global.isGranted (this, new String[]{Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_PERMISSIONS, "")) {
//
//                signInGoogle ();
//            }
        }else if (view == btnFacebook) {
            mClickedView = btnFacebook;
            // Check permissions
            signInFacebook ();
//            if (Global.isGranted (this, new String[]{Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_PERMISSIONS, "")) {
//                signInFacebook ();
//            }
        }

    }

    private boolean isValid() {
        String email = edEmail.getText ().toString ().trim ();
        String password = edPassword.getText ().toString ().trim ();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher (email).matches ()) {
            Toast.makeText (self, R.string.msg_email_is_required, Toast.LENGTH_SHORT).show ();
            edEmail.requestFocus ();
            return false;
        }
        if (password.isEmpty ()) {
            Toast.makeText (self, R.string.msg_password_is_required, Toast.LENGTH_SHORT).show ();
            edPassword.requestFocus ();
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case RC_PERMISSIONS: {
                if (grantResults.length > 0) {
                    if (grantResults.length == 1){
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            showPermissionsReminder(RC_PERMISSIONS, true);
                        } else {
                            if (mClickedView == btnGoogle) {
                                signInGoogle();
                                AppUtil.showToast(self, "google");
                            } else if (mClickedView == btnFacebook) {
                                signInFacebook();
                                //AppUtil.showToast(self, R.string.facebook);
                            } else
                            if (mClickedView == btnLogin) {
                                loginUser();
                            }
                        }
                    }else if (grantResults.length == 2){
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED
                                || grantResults[1] == PackageManager.PERMISSION_DENIED) {
                            showPermissionsReminder(RC_PERMISSIONS, true);
                        } else {
                            if (mClickedView == btnGoogle) {
                                signInGoogle();
                                AppUtil.showToast(self, "google");
                            } else if (mClickedView == btnFacebook) {
                                signInFacebook();
                                //AppUtil.showToast(self, R.string.facebook);
                            } else
                            if (mClickedView == btnLogin) {
                                loginUser();
                            }
                        }
                    }

                }
                break;
            }
            default:
                break;
        }
    }

    public void loginUser(){
        String username =   edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        APIService apiService = ApiUtils.getAPIService();
        apiService.login(username ,username, password, User.NORMAL).enqueue(new Callback<ResponeUser>() {
            @Override
            public void onResponse(Call<ResponeUser> call, Response<ResponeUser> response) {

                String token    = response.body().getToken();

                if (response.body() != null){


                    if (response.body().isSuccess()){
                        DataStoreManager.saveUser(response.body().getData());
                    }
                    if (token != null) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        DataStoreManager.saveToken(response.body().getToken());
                        startActivity(intent);
                        finish();

                    }else {
                        AppUtil.showToast(self, R.string.password_is_incorrect);

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponeUser> call, Throwable t) {
                AppUtil.showToast(self, "Login Error");
                Log.e("Error: ", t.getMessage());
                //dialog.hideDialog();
                return;
            }
        });


    }

    private void login(final String name, String email, String password,final String avatar, final String loginMethod) {
        if (NetworkUtility.isNetworkAvailable()) {
//            ViewDialog dialog = new ViewDialog(getActivity());
//            dialog.showDialog();
            APIService apiService = ApiUtils.getAPIService();
            apiService.login(name, email, password, User.SOCIAL).enqueue(new Callback<ResponeUser>() {
                @Override
                public void onResponse(Call<ResponeUser> call, Response<ResponeUser> response) {
                    User userModels = response.body().getData();
                    DataStoreManager.saveToken(response.body().getToken());
                    userModels.setName(name);
                    userModels.setAvatar(avatar);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    DataStoreManager.saveUser(userModels);
                    startActivity(intent);
                    AppUtil.showToast(self, R.string.success);
                    finish();
                    //dialog.hideDialog();
                }

                @Override
                public void onFailure(Call<ResponeUser> call, Throwable t) {
                    Toast.makeText(self, getString(R.string.msg_have_some_error), Toast.LENGTH_SHORT).show();
//                    dialog.hideDialog();
                }
            });
        }else {
            Toast.makeText(self,getString(R.string.msg_no_network), Toast.LENGTH_SHORT).show();
        }

    }


    private void signInGoogle() {
        if (NetworkUtility.isNetworkAvailable()) {
            // Sign out before signing in again
            if (mGoogleApiClient.isConnected()) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {

                            }
                        });

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
            } else {
                mGoogleApiClient.connect();
            }
        } else {
            Toast.makeText(self, getString(R.string.network_is_not_connect), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mCallbackManager != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        }
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            String email = "", name = "", avatar = "";
            if (acct.getEmail() != null) {
                email = acct.getEmail();
            }
            if (acct.getDisplayName() != null) {
                name = acct.getDisplayName();
            }
            if (acct.getPhotoUrl() != null) {
                avatar = acct.getPhotoUrl().toString();
            }

            if (!email.equals("")) {
                login(name,email, "",avatar, User.SOCIAL);
            } else {
                Toast.makeText(self, getString(R.string.msg_can_not_get_email), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void gotoHome(){

//        Global.startActivityWithoutAnimation(self, MainActivity.class, bundle);
//        // Close this activity
//        finish();
    }

    protected void showPermissionsReminder(final int reqCode, final boolean flag) {
        Global.showConfirmationDialog(self, getString(R.string.msg_remind_user_grants_permissions),
                getString(R.string.allow), getString(R.string.no_thank), false, new IConfirmation() {
                    @Override
                    public void onPositive() {
                        Global.isGranted(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.ACCESS_FINE_LOCATION}, reqCode, null);
                    }

                    @Override
                    public void onNegative() {
                        if (flag) {
                            finish();
                        }
                    }
                });
    }

    // Login with Facebook
    private void signInFacebook(){
        if (NetworkUtility.isNetworkAvailable()) {
            mCallbackManager = CallbackManager.Factory.create();

            if (AccessToken.getCurrentAccessToken() != null) {
                LoginManager.getInstance().logOut();
            }

            LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    String accessToken = loginResult.getAccessToken().getToken();
                    ModelManager.getFacebookInfo(self, mRequestQueue, accessToken, new ModelManagerListener() {
                        @Override
                        public void onSuccess(Object object) {
                            JSONObject jsonObject = (JSONObject) object;
                            String email    = jsonObject.optString("email");
                            String name     = jsonObject.optString("name");
                            String avatar   = "";

                            try {
                                avatar = jsonObject.getJSONObject("picture").getJSONObject("data").optString("url");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!email.equals("")) {
                                Log.e("Splash", "onSuccess login fb2");
                                login(name, email, "", avatar, User.SOCIAL);
                            } else {
                                Toast.makeText(self, R.string.msg_can_not_get_email, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onError() {
                            Log.e("Splash", "onError login fb");
                            Toast.makeText(self, R.string.msg_have_some_errors, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancel() {
                    Log.e("onCancel", "Facebook Login cancel");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.e("onError", "Facebook Login error" + error.getMessage().toString());
                }
            });
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        }else {
            Toast.makeText(self, getString(R.string.msg_no_network), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
