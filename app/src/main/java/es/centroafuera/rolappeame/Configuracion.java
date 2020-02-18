package es.centroafuera.rolappeame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Configuracion extends AppCompatActivity implements View.OnClickListener {
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
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btPreferencias:
                Intent intentPreferencias = new Intent(this, Preferencias.class);
                startActivity(intentPreferencias);
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
