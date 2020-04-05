package es.centroafuera.rolappeame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.common.api.Status;


public class Configuracion extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        firebaseAuth = FirebaseAuth.getInstance();

        Button btPreferencias = findViewById(R.id.btPreferencias);
        btPreferencias.setOnClickListener(this);
        Button btCondiciones = findViewById(R.id.btCondiciones);
        btCondiciones.setOnClickListener(this);
        Button btMapa = findViewById(R.id.btMapa);
        btMapa.setOnClickListener(this);
        Button btVolver = findViewById(R.id.btVolver);
        btVolver.setOnClickListener(this);
        Button btCerrarSesion = findViewById(R.id.btCerrarSesion);
        btCerrarSesion.setOnClickListener(this);

        //Cerrar sesión con Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btPreferencias: case R.id.btCondiciones: case R.id.btMapa:
                break;

            case R.id.btVolver:
                onBackPressed();
                break;

            case R.id.btCerrarSesion:
                cerrarSesion();
                break;
        }

    }

    private void cerrarSesion() {

        //Si está con facebook
        if (AccessToken.getCurrentAccessToken() == null){
            LoginManager.getInstance().logOut();

        }else {
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);

            if (opr.isDone()) { //Si ha iniciado sesión con Google
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Intent intent = new Intent(Configuracion.this, LogInActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Algo ha fallado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }else {
                firebaseAuth.signOut();
            }
        }

        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
