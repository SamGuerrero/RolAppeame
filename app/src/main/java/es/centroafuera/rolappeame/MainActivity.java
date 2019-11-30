package es.centroafuera.rolappeame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/*
Este programa de momento sólo crea partidas como jugador:
Puedes crear tus personajes, ver sus estadísiticas y cambiarlas

ACCIONES FUTURAS:
    - Crear partidas como máster
    - Ver tus tiendas de rol más cercanas
    - Conectar partidas de varios personajes a uno de máster
    - Los personajes tendrán inventario
    - El máster podrá cambiar el inventario de los jugadores
*/

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    BaseDeDatos db;
    ArrayList<Personaje> partidas;
    PersonajeAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //añado los listeners
        FloatingActionButton anadirFAB = findViewById(R.id.anadirFAB);
        anadirFAB.setOnClickListener(this);

        //Abro la BDD, Relleno el ArrayList con personajes de la BDD, lo paso por el adaptador y lo muestro por pantalla
        partidas = new ArrayList<>();
        ListView lvPartidas = findViewById(R.id.partidasLV);
        adaptador = new PersonajeAdapter(this, partidas);
        lvPartidas.setAdapter(adaptador);

        //Esto es un comentario como arriba de la página que te dirá si tienes o no partidas
        TextView comentario = findViewById(R.id.comentarioTV);
        if (partidas.size() == 0)
            comentario.setText("Aquí se mostrarán tus partidas cuando añadas alguna");
        else
            comentario.setText("Tus personajes:");
    }

    //Cuando vuelve de hacer el personaje
    public void onResume() {
        super.onResume();

        //Abro la BDD, Relleno el ArrayList con personajes de la BDD, lo paso por el adaptador y lo muestro por pantalla
        db = new BaseDeDatos(this);
        partidas.clear();
        partidas.addAll(db.getPersonajes());

        adaptador.notifyDataSetChanged();

        //Esto es un comentario como arriba de la página que te dirá si tienes o no partidas
        TextView comentario = findViewById(R.id.comentarioTV);
        if (partidas.size() == 0)
            comentario.setText("Aquí se mostrarán tus partidas cuando añadas alguna");
        else
            comentario.setText("Tus personajes:");
    }

    //Crea el botón de ajustes
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fijo, menu);
        return true;
    }

    //Si aprietas el float action button
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.anadirFAB:
                Intent intent = new Intent(this, CrearPersonaje.class);
                startActivity(intent);

                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ajustes:
                Intent intent = new Intent(this, AcercaDe.class);
                startActivity(intent);
                return true;

            default:
                return true;
        }
    }
}
