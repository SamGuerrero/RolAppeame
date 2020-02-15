package es.centroafuera.rolappeame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

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
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Personaje> partidas = new ArrayList<>();
    PersonajeAdapter adaptador;
    ListView lvPartidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //añado los listeners
        FloatingActionButton anadirFAB = findViewById(R.id.anadirFAB);
        anadirFAB.setOnClickListener(this);

        //Obtengo los personajes y los muestro por pantalal
        lvPartidas = findViewById(R.id.partidasLV);
        getMensajesFromFirebase(this);

        registerForContextMenu(lvPartidas);

        //Esto es un comentario como arriba de la página que te dirá si tienes o no partidas
        TextView comentario = findViewById(R.id.comentarioTV);
        if (partidas.size() == 0)
            comentario.setText(getString(R.string.comentarioInicial));
        else
            comentario.setText(getString(R.string.comentario));
    }

    public void getMensajesFromFirebase(final MainActivity activity){

        //Obtengo una lista de la base de datos
        DatabaseReference myRef = database.getReference("Personaje"); //La clase en Java

        // Read from the database
        myRef.child("personajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){
                    partidas.clear();

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = (String) ds.child("nombre").getValue();
                        Bitmap imagen = null;
                        Raza raza = Raza.DRACÓNIDO;
                        Oficio oficio = Oficio.BARDO;
                        int fuerza = 0;
                        int agilidad = 0;
                        int percepcion = 0;
                        int constitucion = 0;
                        int inteligencia = 0;
                        int carisma = 0;

                        try {
                            imagen = StringToBitMap(ds.child("imagen").getValue().toString());

                            raza = Raza.valueOf(ds.child("raza").getValue().toString());
                            oficio =  Oficio.valueOf(ds.child("oficio").getValue().toString());
                            fuerza = Integer.parseInt(ds.child("fuerza").getValue().toString());
                            agilidad = Integer.parseInt(ds.child("agilidad").getValue().toString());
                            percepcion = Integer.parseInt(ds.child("percepcion").getValue().toString());
                            constitucion = Integer.parseInt(ds.child("constitucion").getValue().toString());
                            inteligencia = Integer.parseInt(ds.child("inteligencia").getValue().toString());
                            carisma = Integer.parseInt(ds.child("carisma").getValue().toString());

                        }catch (NullPointerException e){}

                        Personaje personajeT = new Personaje(nombre, raza, oficio, fuerza, agilidad, percepcion, constitucion, inteligencia, carisma, imagen);
                        personajeT.setIdT(ds.getKey());
                        partidas.add(personajeT);
                    }

                    //Esto es un comentario como arriba de la página que te dirá si tienes o no partidas
                    TextView comentario = findViewById(R.id.comentarioTV);
                    if (partidas.size() == 0)
                        comentario.setText(getString(R.string.comentarioInicial));
                    else
                        comentario.setText(getString(R.string.comentario));

                    adaptador = new PersonajeAdapter(activity, partidas);
                    lvPartidas.setAdapter(adaptador);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    //Cuando vuelve de hacer el personaje
    public void onResume() {
        super.onResume();

        //getMensajesFromFirebase();
        //adaptador.notifyDataSetChanged();

    }

    //Si aprietas el float action button
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.anadirFAB:

                //Elegir entre Master y Jugador
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Cómo quieres jugar?")
                        .setPositiveButton("Jugador",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        crearPersonaje();
                                    }
                                })
                        .setNeutralButton("Master",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        crearPartida();
                                    }
                                });
                builder.create().show();



                break;
        }
    }

    public void crearPersonaje(){
        Intent intent = new Intent(this, CrearPersonaje.class);
        startActivity(intent);
    }

    public void crearPartida(){
        Intent intent = new Intent(this, CrearPartida.class);
        startActivity(intent);
    }

    //Infla el Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fijo, menu);
        return true;
    }

    @Override //Dentro del Action Bar
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, AcercaDe.class);
        startActivity(intent);
        return true;
    }

    @Override //Infla el menú contextual
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.elige, menu);
    }

    @Override//Dentro del menú contextual
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = menuInfo.position;

        switch (item.getItemId()){
            case R.id.imVer:
                Intent intent = new Intent(this, VistaPersonaje.class);

                intent.putExtra("ID", partidas.get(pos).getIdT()); //Estoy pasando mal el Id

                startActivity(intent);
                break;

            case R.id.imEliminar:
                Personaje temporal = partidas.get(pos);
                DatabaseReference myRef = database.getReference("Personaje");
                myRef.child("personajes").child(temporal.getIdT()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "El personaje se ha eliminado correctamente", Toast.LENGTH_LONG);
                    }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "El personaje no se ha podido eliminar", Toast.LENGTH_LONG);
                    }
                });

                //getMensajesFromFirebase();
                adaptador.notifyDataSetChanged();

                break;

            default: break;
        }

        return true;
    }
}
