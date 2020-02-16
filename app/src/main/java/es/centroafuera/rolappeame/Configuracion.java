package es.centroafuera.rolappeame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Configuracion extends AppCompatActivity implements View.OnClickListener  {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        Button btPreferencias = findViewById(R.id.btPreferencias);
        btPreferencias.setOnClickListener(this);
        Button btCondiciones = findViewById(R.id.btCondiciones);
        btCondiciones.setOnClickListener(this);
        Button btMapa = findViewById(R.id.btMapa);
        btMapa.setOnClickListener(this);
        Button btVolver = findViewById(R.id.btVolver);
        btVolver.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btPreferencias:
                //TODO: Enviar a Activity Preferencias
                break;
            case R.id.btCondiciones:
                Intent intentCondiciones = new Intent(this, AcercaDe.class);
                startActivity(intentCondiciones);

                break;
            case R.id.btMapa:
                Intent intentMapa = new Intent(this, MapsActivity.class);
                startActivity(intentMapa);

                break;
            case R.id.btVolver:
                onBackPressed();
                break;
        }

    }
}
