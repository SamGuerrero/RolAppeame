package es.centroafuera.rolappeame.ui.gallery;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Locale;

import es.centroafuera.rolappeame.MainActivity;
import es.centroafuera.rolappeame.R;

public class GalleryFragment extends Fragment implements View.OnClickListener {

    private GalleryViewModel galleryViewModel;
    CheckBox cbSonido, cbNoche, cbIdioma;
    AudioManager audioManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        //TODO: Cambiar esta función que de todas formas es algo cutre
        //Silenciar movil
        cbSonido = root.findViewById(R.id.cbSonido);
        cbSonido.setOnClickListener(this);
        //audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Modo noche
        cbNoche = root.findViewById(R.id.cbNoche);
        cbNoche.setOnClickListener(this);

        //Idioma
        cbIdioma = root.findViewById(R.id.cbIdioma);
        cbIdioma.setOnClickListener(this);


        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

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
        //audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if (cbSonido.isChecked()){
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
            Toast.makeText(getContext(), R.string.sonido_bajado, Toast.LENGTH_SHORT).show();

        }else{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 100, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, 100, 0);
            Toast.makeText(getContext(), R.string.sonido_subido, Toast.LENGTH_SHORT).show();

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
        //TODO: Arreglar lo del getContext desde Fragment
        //getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        Intent refrescar = new Intent(getContext(), MainActivity.class);
        refrescar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(refrescar);

    }

    public void ajustarBrillo(){

        if (cbNoche.isChecked() && (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        Intent refrescar = new Intent(getContext(), MainActivity.class);
        refrescar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(refrescar);


    }
}