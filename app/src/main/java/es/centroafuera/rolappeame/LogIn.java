package es.centroafuera.rolappeame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private EditText etEmail;
    private EditText etContra;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        etEmail = (EditText) findViewById(R.id.etEmail);
        etContra = (EditText) findViewById(R.id.etContra);

        Button btRegistrar = (Button) findViewById(R.id.btRegistrar);
        Button btEntrar = (Button) findViewById(R.id.btEntrar);

        btEntrar.setOnClickListener(this);
        btRegistrar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btEntrar: break;
            case R.id.btRegistrar:
                registrarUsuario();
                break;
            default: break;
        }
    }

    private void entrar(){

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

                    Toast.makeText(LogIn.this, "Se ha registrado correctamente el email " + email, Toast.LENGTH_LONG).show();
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        //Si hay colisión es que ya existe ese correo
                        Toast.makeText(LogIn.this, "Ese email ya existe. No se pudo registrar", Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(LogIn.this, "Ha ocurrido un error. No se ha podido registrar el email", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
