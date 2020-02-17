package es.centroafuera.rolappeame;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        cbNoche = findViewById(R.id.cbNoche);
        //TODO: Modo noche

        //Idioma
        cbIdioma = findViewById(R.id.cbIdioma);
        cbIdioma.setOnClickListener(this);
        TextView titulo = findViewById(R.id.tvTitulo);
        if (titulo.getText().equals("Preferences")){
            cbIdioma.setSelected(true);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btVolver:
                activarIdioma();
                onBackPressed();
                break;

            case R.id.cbSonido:
                activarSonido();
                break;

            case R.id.cbIdioma:
                activarIdioma();
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

        }else{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 100, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, 100, 0);

        }
    }

    public void activarIdioma(){
        Locale loc;

        if (cbIdioma.isChecked()){
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
}
