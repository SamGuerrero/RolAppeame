package es.centroafuera.rolappeame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private EditText etEmail;
    private EditText etContra;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_login);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_titulo);


        firebaseAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        etEmail = (EditText) findViewById(R.id.etEmail);
        etContra = (EditText) findViewById(R.id.etContra);

        Button btRegistrar = (Button) findViewById(R.id.btRegistrar);
        Button btEntrar = (Button) findViewById(R.id.btEntrar);



        loginButton = (LoginButton) findViewById(R.id.btEntrarFB);
        loginButton.setReadPermissions("email");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LogInActivity.this, "Bienvenido", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Toast.makeText(LogInActivity.this, "Se ha cancelado el proceso", Toast.LENGTH_LONG).show();
                return;
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LogInActivity.this, "Ha ocurrido un error", Toast.LENGTH_LONG).show();
                return;
            }
        });

        btEntrar.setOnClickListener(this);
        btRegistrar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btEntrar:
                entrar();
                break;
            case R.id.btRegistrar:
                registrarUsuario();
                break;
            default: break;
        }
    }

    private void entrar(){
        //Recogemos el email y la contraseña
        final String email = etEmail.getText().toString().trim();
        String contraseña = etContra.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (contraseña.isEmpty()) {
            Toast.makeText(this, "Debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(LogInActivity.this, "Bienvenido " + email, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(LogInActivity.this, "Ha ocurrido un error. No se ha podido entrar", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void registrarUsuario(){
        //Recogemos el email y la contraseña
        final String email = etEmail.getText().toString().trim();
        String contraseña = etContra.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (contraseña.isEmpty()) {
            Toast.makeText(this, "Debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())

                    Toast.makeText(LogInActivity.this, "Se ha registrado correctamente el email " + email, Toast.LENGTH_LONG).show();
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        //Si hay colisión es que ya existe ese correo
                        Toast.makeText(LogInActivity.this, "Ese email ya existe. No se pudo registrar", Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(LogInActivity.this, "Ha ocurrido un error. No se ha podido registrar el email", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
