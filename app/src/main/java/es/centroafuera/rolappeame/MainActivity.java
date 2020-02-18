package es.centroafuera.rolappeame;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

/*
Este programa de momento sólo crea partidas como jugador:
Puedes crear tus personajes, ver sus estadísiticas y cambiarlas

ACCIONES FUTURAS:
    - Conectar partidas de varios personajes a uno de máster
    - Los personajes tendrán inventario
    - El máster podrá cambiar el inventario de los jugadores
*/

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Personaje> partidas = new ArrayList<>();
    PersonajeAdapter adaptador;
    ListView lvPartidas;

    ArrayList<Partida> partidasMaster = new ArrayList<>();
    PartidaAdapter adaptadorMaster;
    ListView lvPartidasMaster;

    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tab
        tabHost = findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec(getString(R.string.personajes));
        spec.setContent(R.id.personajes);
        spec.setIndicator(getString(R.string.personajes));
        tabHost.addTab(spec);
        spec = tabHost.newTabSpec(getString(R.string.partidas));
        spec.setContent(R.id.partidas);
        spec.setIndicator(getString(R.string.partidas));
        tabHost.addTab(spec);

        //añado los listeners
        FloatingActionButton anadirFAB = findViewById(R.id.anadirFAB);
        anadirFAB.setOnClickListener(this);
        FloatingActionButton FABanadir = findViewById(R.id.FABanadir);
        FABanadir.setOnClickListener(this);

        //Obtengo los personajes y los muestro en su tabhost
        lvPartidas = findViewById(R.id.partidasLV);
        getPersonajesFromFirebase(this);
        registerForContextMenu(lvPartidas);

        //Obtengo las partidas y las muestro en su tabhost
        lvPartidasMaster = findViewById(R.id.LVpartidas);
        getPartidasFromFirebase(this);
        registerForContextMenu(lvPartidasMaster);

        //Esto es un comentario como arriba de la página que te dirá si tienes o no partidas
        TextView comentario = findViewById(R.id.comentarioTV);
        if (partidas.size() == 0)
            comentario.setText(getString(R.string.comentarioInicial));
        else
            comentario.setText(getString(R.string.comentario));

    }

    public void getPersonajesFromFirebase(final MainActivity activity){

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

    public void getPartidasFromFirebase(final MainActivity activity){

        //Obtengo una lista de la base de datos
        DatabaseReference myRef = database.getReference("Partida"); //La clase en Java

        // Read from the database
        myRef.child("partidas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){
                    partidasMaster.clear();

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = (String) ds.child("nombre").getValue();
                        Bitmap imagen = null;
                        TipoPartida tipoPartida = TipoPartida.Akelarre;

                        int minVida = 0;
                        int maxVida = 0;
                        int minAtaque = 0;
                        int maxAtaque = 0;
                        int minDefensa = 0;
                        int maxDefensa = 0;

                        try {
                            imagen = StringToBitMap(ds.child("imagen").getValue().toString());
                            tipoPartida = TipoPartida.valueOf(ds.child("tipoPartida").getValue().toString());

                            minVida = Integer.parseInt(ds.child("minVida").getValue().toString());
                            maxVida = Integer.parseInt(ds.child("maxVida").getValue().toString());
                            minAtaque = Integer.parseInt(ds.child("minAtaque").getValue().toString());
                            maxAtaque = Integer.parseInt(ds.child("maxAtaque").getValue().toString());
                            minDefensa = Integer.parseInt(ds.child("minDefensa").getValue().toString());
                            maxDefensa = Integer.parseInt(ds.child("maxDefensa").getValue().toString());

                        }catch (NullPointerException e){}

                        Partida partidaT = new Partida(nombre, imagen, tipoPartida, minVida, maxVida, minAtaque, maxAtaque, minDefensa, maxDefensa);
                        partidaT.setIdT(ds.getKey());
                        partidasMaster.add(partidaT);
                    }

                    //Esto es un comentario como arriba de la página que te dirá si tienes o no partidas
                    TextView comentario = findViewById(R.id.TVcomentario);
                    if (partidasMaster.size() == 0)
                        comentario.setText(getString(R.string.comentarioInicial));
                    else
                        comentario.setText(getString(R.string.comentario));

                    adaptadorMaster = new PartidaAdapter(activity, partidasMaster);
                    lvPartidasMaster.setAdapter(adaptadorMaster);
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
    }

    //Si aprietas el float action button
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.anadirFAB: case R.id.FABanadir:

                //Elegir entre Master y Jugador
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.pregunta_partida))
                        .setPositiveButton(getString(R.string.jugador),
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
        Intent intent = new Intent(this, Configuracion.class);
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
                Intent intent;

                if (tabHost.getCurrentTabTag().equals(getString(R.string.personajes))) {
                    intent = new Intent(this, VistaPersonaje.class);
                    intent.putExtra("ID", partidas.get(pos).getIdT()); //Estoy pasando mal el Id
                }else{
                    intent = new Intent(this, VistaPartida.class);
                    intent.putExtra("ID", partidasMaster.get(pos).getIdT()); //Estoy pasando mal el Id
                }

                startActivity(intent);
                break;

            case R.id.imEliminar:
                if (tabHost.getCurrentTabTag().equals(getString(R.string.personajes))) {
                    Personaje temporal = partidas.get(pos);
                    DatabaseReference myRef = database.getReference("Personaje");
                    myRef.child("personajes").child(temporal.getIdT()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, R.string.eliminar_mensaje, Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, R.string.eliminar_error, Toast.LENGTH_LONG).show();
                        }
                    });

                }else{
                    Partida temporal = partidasMaster.get(pos);
                    DatabaseReference myRef = database.getReference("Partida");
                    myRef.child("partidas").child(temporal.getIdT()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, R.string.eliminar_mensaje, Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, R.string.eliminar_error, Toast.LENGTH_LONG).show();
                        }
                    });

                }

                break;

            default: break;
        }

        return true;
    }
}
