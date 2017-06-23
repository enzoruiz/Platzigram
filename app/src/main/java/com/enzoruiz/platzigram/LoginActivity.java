package com.enzoruiz.platzigram;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.enzoruiz.platzigram.view.ContainerActivity;
import com.enzoruiz.platzigram.view.CreateAccountActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String TAG = "LoginActivity";
    private Button btnLogin;
    private LoginButton btnLoginFacebook;
    private TextInputEditText tietUsername, tietPassword;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLoginFacebook = (LoginButton) findViewById(R.id.btnLoginFacebook);
        tietUsername = (TextInputEditText) findViewById(R.id.username);
        tietPassword = (TextInputEditText) findViewById(R.id.password);

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        btnLoginFacebook.setReadPermissions(Arrays.asList("email"));
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.w(TAG, "Usuario Logueado en FACEBOOK " + loginResult.getAccessToken().getApplicationId());
                loginFacebookFirebase(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.w(TAG, "LOGIN FACEBOOK CANCELADO");
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG, "ERROR LOGIN CON FACEBOOK " + error.toString());
                error.printStackTrace();
            }
        });

    }

    private void loginFacebookFirebase(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        Log.w(TAG, authCredential.toString());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, ContainerActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "No se pudo loguear.", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "NO SE PUDO LOGUEAR CON FACEBOOK.");
                }
            }
        });
    }

    public void goCreateAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void login(){
        String username = tietUsername.getText().toString();
        String password = tietPassword.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    Log.w(TAG, "Usuario Logueado " + firebaseUser.getEmail());

                    Intent intent = new Intent(LoginActivity.this, ContainerActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getParent(), "No se pudo loguear.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
