package es.centroafuera.rolappeame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cojo el botón
        FloatingActionButton anadirFAB = findViewById(R.id.anadirFAB);
        anadirFAB.setOnClickListener(this);

        //Abro la BDD, Relleno el ArrayList con personajes de la BDD, lo paso por el adaptador y lo muestro por pantalla
        BaseDeDatos db = new BaseDeDatos(this);
        ArrayList<Personaje> partidas = new ArrayList<>();
        partidas.addAll(db.getPersonajes());
        ListView lvPartidas = findViewById(R.id.partidasLV);
        PersonajeAdapter adaptador = new PersonajeAdapter(this, partidas);
        lvPartidas.setAdapter(adaptador);

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
        //TODO: Mandar a la activiy que Crea el personaje
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Esto es un dialogo")
                .setTitle("Dialogo de Muestra")
                .setPositiveButton("Continuar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Qué hacer si el usuario pulsa "Si"
                                // En este caso se cierra directamente el diálogo y no se hace nada más
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Qué hacer si el usuario pulsa "No"
                                // En este caso se cierra directamente el diálogo y no se hace nada más
                                dialog.dismiss();
                            }
                        }
                );
        builder.create().show();

        //SECCIÓN FUTURA:Inflar el menú de elección
        /*
            getMenuInflater().inflate(R.menu.elige, menu);
        */
    }

    /*SECCIÓN FUTURA
    //Dentro del menú de elección (Máster / Jugador)
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo;

        switch (item.getItemId()){
            case R.id.jugadorItM:
                //TO DO: Mandar a otra activity
                    Intent inten = new Intent(this, NOMBREACTIVITY.class);
                    startActivity(Intent);
                break;

            case R.id.masterItM:
                //TO DO: Mandar a otra activity
                    Intent inten = new Intent(this, NOMBREACTIVITY.class);
                    startActivity(Intent);
                break;

            default: break;
        }

        return true;
    }
    */
}
