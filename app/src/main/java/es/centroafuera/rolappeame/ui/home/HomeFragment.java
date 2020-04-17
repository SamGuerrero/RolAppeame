package es.centroafuera.rolappeame.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.centroafuera.rolappeame.CrearPartida;
import es.centroafuera.rolappeame.CrearPersonaje;
import es.centroafuera.rolappeame.Oficio;
import es.centroafuera.rolappeame.Partida;
import es.centroafuera.rolappeame.PartidaAdapter;
import es.centroafuera.rolappeame.Personaje;
import es.centroafuera.rolappeame.PersonajeAdapter;
import es.centroafuera.rolappeame.R;
import es.centroafuera.rolappeame.Raza;
import es.centroafuera.rolappeame.TipoPartida;
import es.centroafuera.rolappeame.Usuario;
import es.centroafuera.rolappeame.VistaPartida;
import es.centroafuera.rolappeame.VistaPersonaje;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    View root;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Personaje> partidas = new ArrayList<>();
    PersonajeAdapter adaptador;
    ListView lvPartidas;

    ArrayList<Partida> partidasMaster = new ArrayList<>();
    PartidaAdapter adaptadorMaster;
    ListView lvPartidasMaster;

    TabHost tabHost;
    Usuario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String email = user.getEmail();
        usuario = new Usuario("sam"); //TODO: Cambiar esto


        //Tab
        tabHost = root.findViewById(R.id.tabhost);
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
        FloatingActionButton anadirFAB = root.findViewById(R.id.anadirFAB);
        anadirFAB.setOnClickListener(this);
        FloatingActionButton FABanadir = root.findViewById(R.id.FABanadir);
        FABanadir.setOnClickListener(this);

        //Obtengo los personajes y los muestro en su tabhost
        lvPartidas = root.findViewById(R.id.partidasLV);
        getPersonajesFromFirebase(this);
        registerForContextMenu(lvPartidas);

        //Obtengo las partidas y las muestro en su tabhost
        lvPartidasMaster = root.findViewById(R.id.LVpartidas);
        getPartidasFromFirebase(this);
        registerForContextMenu(lvPartidasMaster);

        //Esto es un comentario como arriba de la página que te dirá si tienes o no partidas
        TextView comentario = root.findViewById(R.id.comentarioTV);
        if (partidas.size() == 0)
            comentario.setText(getString(R.string.comentarioInicial));
        else
            comentario.setText(getString(R.string.comentario));


        return root;
    }

    public void getPersonajesFromFirebase(final HomeFragment activity){

        //FIXME: No está leyendo los personajes correspondientes
        //Obtengo una lista de la base de datos
        DatabaseReference myRef = database.getReference("Usuario"); //La clase en Java

        // Read from the database             //Usuarios > email > personajes

        myRef.child(usuario.getEmail()).child("personajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){
                    partidas.clear();

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Para cada id pedimos una subquery que nos lleve a la partida correspondiente
                        DatabaseReference subRef = database.getReference("Personaje");
                        subRef.child(ds.child("id_personaje").getValue().toString()).addValueEventListener(
                                new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot subDS) {
                                       if (subDS.exists()){
                                           String nombre = (String) subDS.child("nombre").getValue();
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
                                               imagen = StringToBitMap(subDS.child("imagen").getValue().toString());

                                               raza = Raza.valueOf(subDS.child("raza").getValue().toString());
                                               oficio =  Oficio.valueOf(subDS.child("oficio").getValue().toString());
                                               fuerza = Integer.parseInt(subDS.child("fuerza").getValue().toString());
                                               agilidad = Integer.parseInt(subDS.child("agilidad").getValue().toString());
                                               percepcion = Integer.parseInt(subDS.child("percepcion").getValue().toString());
                                               constitucion = Integer.parseInt(subDS.child("constitucion").getValue().toString());
                                               inteligencia = Integer.parseInt(subDS.child("inteligencia").getValue().toString());
                                               carisma = Integer.parseInt(subDS.child("carisma").getValue().toString());

                                           }catch (NullPointerException e){}

                                           Personaje personajeT = new Personaje(nombre, raza, oficio, fuerza, agilidad, percepcion, constitucion, inteligencia, carisma, imagen);
                                           personajeT.setIdT(subDS.getKey()); //Se guarda el ID real, donde se encuentra en la BDD
                                           partidas.add(personajeT);
                                       }
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError error) {
                                       Log.w(TAG, "Failed to read value.", error.toException());
                                   }
                               });

                        usuario.addPersonajes(ds.getKey()); //Añadimos la ID en la que se encuentra este personaje en concreto, así a la hora de borrarlo sabremos a donde ir

                    }

                    //Esto es un comentario como arriba de la página que te dirá si tienes o no partidas
                    TextView comentario = root.findViewById(R.id.comentarioTV);
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

    public void getPartidasFromFirebase(final HomeFragment activity){
        //Obtengo una lista de la base de datos
        DatabaseReference myRef = database.getReference("Usuario"); //La clase en Java

        // Read from the database             //Usuarios > email > partidas

        myRef.child(usuario.getEmail()).child("partidas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){
                    partidasMaster.clear();

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Buscamos en partida cada id dentro de Usuario

                        DatabaseReference subRef = database.getReference("Partida");
                        subRef.child(ds.child("id_partida").getValue().toString()).addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot subDS) {
                                        String nombre = (String) subDS.child("nombre").getValue();
                                        Bitmap imagen = null;
                                        TipoPartida tipoPartida = TipoPartida.Akelarre;

                                        int minVida = 0;
                                        int maxVida = 0;
                                        int minAtaque = 0;
                                        int maxAtaque = 0;
                                        int minDefensa = 0;
                                        int maxDefensa = 0;

                                        try {
                                            imagen = StringToBitMap(subDS.child("imagen").getValue().toString());
                                            tipoPartida = TipoPartida.valueOf(subDS.child("tipoPartida").getValue().toString());

                                            minVida = Integer.parseInt(subDS.child("minVida").getValue().toString());
                                            maxVida = Integer.parseInt(subDS.child("maxVida").getValue().toString());
                                            minAtaque = Integer.parseInt(subDS.child("minAtaque").getValue().toString());
                                            maxAtaque = Integer.parseInt(subDS.child("maxAtaque").getValue().toString());
                                            minDefensa = Integer.parseInt(subDS.child("minDefensa").getValue().toString());
                                            maxDefensa = Integer.parseInt(subDS.child("maxDefensa").getValue().toString());

                                        }catch (NullPointerException e){}

                                        Partida partidaT = new Partida(nombre, imagen, tipoPartida, minVida, maxVida, minAtaque, maxAtaque, minDefensa, maxDefensa);
                                        partidaT.setIdT(subDS.getKey());
                                        partidasMaster.add(partidaT);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.w(TAG, "Failed to read value.", error.toException());
                                    }
                                });

                        usuario.addPartidas(ds.getKey()); //Añadimos la ID en la que se encuentra este personaje en concreto, así a la hora de borrarlo sabremos a donde ir

                    }

                    //Esto es un comentario como arriba de la página que te dirá si tienes o no partidas
                    TextView comentario = root.findViewById(R.id.TVcomentario);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        Intent intent = new Intent(getContext(), CrearPersonaje.class);
        startActivity(intent);
    }

    public void crearPartida(){
        Intent intent = new Intent(getContext(), CrearPartida.class);
        startActivity(intent);
    }

    @Override //Infla el menú contextual
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.elige, menu);
    }

    @Override//Dentro del menú contextual
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = menuInfo.position;

        switch (item.getItemId()){
            case R.id.imVer:
                Intent intent;

                if (tabHost.getCurrentTabTag().equals(getString(R.string.personajes))) {
                    intent = new Intent(getContext(), VistaPersonaje.class);
                    intent.putExtra("ID", partidas.get(pos).getIdT()); //Estoy pasando mal el Id
                }else{
                    intent = new Intent(getContext(), VistaPartida.class);
                    intent.putExtra("ID", partidasMaster.get(pos).getIdT()); //Estoy pasando mal el Id
                }

                startActivity(intent);
                break;

            case R.id.imEliminar:
                if (tabHost.getCurrentTabTag().equals(getString(R.string.personajes))) {

                    //Se elimina de Usuario
                    DatabaseReference myRef = database.getReference("Usuario");
                    myRef.child(usuario.getEmail()).child("personajes").child(usuario.getPersonajes().get(pos)).removeValue();

                    Personaje temporal = partidas.get(pos);

                    //Se elimina de Personaje
                    myRef = database.getReference("Personaje");
                    myRef.child(temporal.getIdT()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), R.string.eliminar_mensaje, Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), R.string.eliminar_error, Toast.LENGTH_LONG).show();
                        }
                    });



                }else{
                    //Se elimina de Usuario
                    DatabaseReference myRef = database.getReference("Usuario");
                    myRef.child(usuario.getEmail()).child("partidas").child(usuario.getPartidas().get(pos)).removeValue();

                    //Se elimina de Partida
                    Partida temporal = partidasMaster.get(pos);
                    DatabaseReference secRef = database.getReference("Partida");
                    secRef.child("partidas").child(temporal.getIdT()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), R.string.eliminar_mensaje, Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), R.string.eliminar_error, Toast.LENGTH_LONG).show();
                        }
                    });

                }

                break;

            default: break;
        }

        return true;
    }
}