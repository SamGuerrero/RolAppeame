package es.centroafuera.rolappeame;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class Preferencias extends AppCompatActivity implements View.OnClickListener {
    CheckBox cbSonido, cbNoche, cbIdioma;
    AudioManager audioManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        Button btVolver = findViewById(R.id.btVolver);
        btVolver.setOnClickListener(this);

        //Silenciar movil
        cbSonido = findViewById(R.id.cbSonido);
        cbSonido.setOnClickListener(this);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Modo noche
        cbNoche = findViewById(R.id.cbNoche);
        cbNoche.setOnClickListener(this);

        //Idioma
        cbIdioma = findViewById(R.id.cbIdioma);
        cbIdioma.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btVolver:
                ajustarBrillo();
                onBackPressed();
                break;

            case R.id.cbSonido:
                activarSonido();
                break;

            case R.id.cbIdioma:
                activarIdioma();
                break;

            case R.id.cbNoche:
                ajustarBrillo();
                break;
        }

    }

    public void activarSonido(){
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if (cbSonido.isChecked()){
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
            Toast.makeText(this, R.string.sonido_bajado, Toast.LENGTH_SHORT).show();

        }else{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 100, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, 100, 0);
            Toast.makeText(this, R.string.sonido_subido, Toast.LENGTH_SHORT).show();

        }
    }

    public void activarIdioma(){
        Locale loc;

        if ((cbIdioma.isChecked()) && (cbSonido.getText().equals("Sonido del movil")) ){
            loc = new Locale(Locale.ENGLISH.getLanguage());

        }else{
            loc = new Locale("es", "ES");
        }

        Locale.setDefault(loc);
        Configuration config = new Configuration();
        config.locale = loc;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        Intent refrescar = new Intent(this, MainActivity.class);
        startActivity(refrescar);
        finish();

    }

    public void ajustarBrillo(){

        if (cbNoche.isChecked() && (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        Intent refrescar = new Intent(this, MainActivity.class);
        startActivity(refrescar);
        finish();


    }


}
