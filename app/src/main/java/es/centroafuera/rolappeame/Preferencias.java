package es.centroafuera.rolappeame;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class Preferencias extends AppCompatActivity implements View.OnClickListener {
    CheckBox cbSonido, cbNoche, cbIdioma;
    AudioManager audioManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        Button btVolver = findViewById(R.id.btVolver);
        btVolver.setOnClickListener(this);

        cbSonido = findViewById(R.id.cbSonido);
        cbSonido.setOnClickListener(this);
        cbNoche = findViewById(R.id.cbNoche);
        cbIdioma = findViewById(R.id.cbIdioma);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btVolver:

                onBackPressed();
                break;

            case R.id.cbSonido:
                activarPreferencias();
                break;

        }

    }

    public void activarPreferencias(){
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
}
