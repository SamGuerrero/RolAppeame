package es.centroafuera.rolappeame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import es.centroafuera.rolappeame.adapters.PersonajeAdapter;
import es.centroafuera.rolappeame.models.Partida;
import es.centroafuera.rolappeame.models.Personaje;
import es.centroafuera.rolappeame.models.Texto;
import es.centroafuera.rolappeame.models.TipoPartida;
import es.centroafuera.rolappeame.models.Usuario;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Utils {
    public static String TABLA_PERSONAJES = "Personaje";
    public static String TABLA_PARTIDAS = "Partida";
    public static String TABLA_USUARIOS = "Usuario";
    public static String TABLA_SITIOS = "Sitio";
    public static String TABLA_REGLAS = "Reglas";

    //Dentro de Usuarios
    public static String PARTIDAS_USUARIO = "partidas";
    public static String ID_PARTIDA = "id_partida";
    public static String PERSONAJES_USUARIO = "personajes";
    public static String ID_PERSONAJE = "id_personaje";

    //Dentro de Partidas
    public static String NOMBRE_PARTIDA = "nombre";
    public static String IMAGEN_PARTIDA = "imagen";

    //Dentro de Personajes
    public static String NOMBRE_PERSONAJE = "nombre";
    public static String IMAGEN_PERSONAJE = "imagen";
    public static String DESTREZA_PERSONAJE = "destreza";
    public static String CARISMA_PERSONAJE = "carisma";
    public static String CONSTITUCION_PERSONAJE = "constitucion";
    public static String FUERZA_PERSONAJE = "fuerza";
    public static String INTELIGENCIA_PERSONAJE = "inteligencia";
    public static String CLASE_PERSONAJE = "clase";
    public static String PERCEPCION_PERSONAJE = "percepcion";
    public static String RAZA_PERSONAJE = "raza";
    public static String SUBRAZA_PERSONAJE = "subraza";

    //Dentro de Sitios
    public static String NOMBRE_SITIO = "nombre";
    public static String DESCRIPCION_SITIO = "descripcion";
    public static String LATITUD_SITIO = "latitud";
    public static String LONGITUD_SITIO = "longitud";

    //Dentro de Reglas
    public static String TABLA_RAZAS = "Razas";
    public static String TABLA_CLASES = "Clases";
    public static String TABLA_RASGOS = "Rasgos";
    public static String TABLA_CONJUROS = "Conjuros";

    //Dentro de Razas
    public static String RAZA_DESCRIPCION = "descripcion";
    public static String RAZA_SUBRAZA = "subrazas";

    //Dentro de Clases
    public static String CLASE_DESCRIPCION = "descripcion";
    public static String CONJUROS_CLASE = "conjuros";
    public static String RASGOS_CLASE = "rasgos";

    //Convierte de String a Imagen
    public static Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    //Convierte de Imagen a String
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream ByteStream=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, ByteStream);
        byte [] b = ByteStream.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private static LinkedHashMap<Texto, Boolean> arrayToLinkHAshMap(ArrayList<HashMap> lista) {
        LinkedHashMap<Texto, Boolean> lhm = new LinkedHashMap<>();

        Texto t = new Texto();
        for (HashMap<String, String> map : lista) {

            if (!map.get("titulo").equals(null))
                t.setTitulo(map.get("titulo"));

            if (!map.get("descripcion").equals(null))
                t.setDescripcion(map.get("descripcion"));

            lhm.put(t, true);
        }

        return lhm;
    }

    /**Devuelve un ArrayList con las Razas existentes */
    public static List<Texto> getRazasFromDatabase() {
        final ArrayList<Texto> razas = new ArrayList<>();

        //Obtengo una lista de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TABLA_REGLAS); //La clase en Java

        // Read from the database
        myRef.child(TABLA_RAZAS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = ds.getKey();
                        String descripcion = String.valueOf(ds.child(RAZA_DESCRIPCION).getValue());
                        razas.add(new Texto(nombre, descripcion));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return razas;
    }

    /**Devuelve un ArrayList con las Clases existentes*/
    public static List<Texto> getClasesFromDatabase() {
        final ArrayList<Texto> clases = new ArrayList<>();

        //Obtengo una lista de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TABLA_REGLAS); //La clase en Java

        // Read from the database
        myRef.child(TABLA_CLASES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = ds.getKey();
                        String descripcion = String.valueOf(ds.child(CLASE_DESCRIPCION).getValue());
                        clases.add(new Texto(nombre, descripcion));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return clases;
    }

    /**Devuelve un ArrayList con los Rasgos existentes*/
    public static List<Texto> getRasgosFromDatabase(){
        final ArrayList<Texto> rasgos = new ArrayList<>();

        //Obtengo una lista de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TABLA_REGLAS); //La clase en Java

        // Read from the database
        myRef.child(TABLA_RASGOS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = ds.getKey();
                        String descripcion = String.valueOf(ds.getValue());
                        rasgos.add(new Texto(nombre, descripcion));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return rasgos;
    }

    /**Devuelve un ArrayList con los Conjuros existentes*/
    public static List<Texto> getConjurosFromDatabase(){
        final ArrayList<Texto> conjuros = new ArrayList<>();

        //Obtengo una lista de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TABLA_REGLAS); //La clase en Java

        // Read from the database
        myRef.child(TABLA_CONJUROS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = ds.getKey();
                        String descripcion = String.valueOf(ds.getValue());
                        conjuros.add(new Texto(nombre, descripcion));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return conjuros;
    }

    public static ArrayList<Texto> getConjuros(String clase){
        ArrayList<Texto> lista = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

         //Reglas > Clases > claseConcreta > conjuros > trucos + 1
        DatabaseReference myRef = database.getReference(TABLA_REGLAS);
        myRef.child(TABLA_CLASES).child(clase).child(CONJUROS_CLASE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                if (ds.exists()){
                    int total = 0;

                    if(ds.child("trucos").exists())
                        total += (long) ds.child("trucos").getValue();
                    if(ds.child("1").exists())
                        total += (long) ds.child("1").getValue();

                    //Reglas > Conjuros
                    DatabaseReference subRef = database.getReference(TABLA_REGLAS);
                    int finalTotal = total;
                    subRef.child(TABLA_CONJUROS).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                int aleatorio = 0;
                                boolean completo = false;

                                for (DataSnapshot d: dataSnapshot.getChildren()){
                                    aleatorio = (int) Math.random()*50;

                                    if ((aleatorio /2 == 0) && (!completo)) {
                                        String titulo = String.valueOf(d.getKey());
                                        String descripcion = String.valueOf(d.getValue());

                                        Log.i("Conjuro", String.valueOf(d.getKey()));

                                        Texto t = new Texto(titulo, descripcion);
                                        lista.add(t);

                                        if (lista.size() == finalTotal)
                                            completo = true;

                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return lista;
    };

    public static ArrayList<Texto> getRasgos(String clase, String raza){
        ArrayList<Texto> lista = new ArrayList<>();
        ArrayList<String > rasgos = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Reglas > Clases > claseConcreta > rasgos > id > get String
        DatabaseReference myRef = database.getReference(TABLA_REGLAS);
        myRef.child(TABLA_CLASES).child(clase).child(RASGOS_CLASE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                if (ds.exists()){
                    ArrayList<String> r;
                    for (DataSnapshot d : ds.getChildren()) {
                        r = (ArrayList<String>) d.getValue();
                        rasgos.addAll(r);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Reglas > Razas > raza concreta > rasgos > id > getstring
        myRef.child(TABLA_RAZAS).child(raza).child(RASGOS_CLASE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                if (ds.exists()){
                    String r;
                    for (DataSnapshot d : ds.getChildren()) {
                        r = (String) d.getValue();
                        rasgos.add(r);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Reglas > Rasgos > Nombre > Descripcion

        for (String titulo : rasgos){
            Log.i("Titulo", titulo);

            myRef.child(TABLA_RASGOS).child(titulo).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot ds) {
                    if (ds.exists()){
                        Texto t = new Texto(titulo, String.valueOf(ds.getValue()));
                        Log.i("Descripcion", String.valueOf(ds.getValue()));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        return lista;
    };

    /**Inserta un personaje*/
    public static void insertarPersonaje(Personaje personaje, Usuario usuario){
        //Personaje > ID > Cada dato
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TABLA_PERSONAJES); //Referencia a la clase Java
        myRef.child(String.valueOf(personaje.getId())).child(NOMBRE_PERSONAJE).setValue(personaje.getNombre()); //El .push() es para crear un id único, lo pondría antes del segundo child
        myRef.child(String.valueOf(personaje.getId())).child(IMAGEN_PERSONAJE).setValue(Utils.BitMapToString(personaje.getImagen()));
        myRef.child(String.valueOf(personaje.getId())).child(CLASE_PERSONAJE).setValue(personaje.getClase());
        myRef.child(String.valueOf(personaje.getId())).child(RAZA_PERSONAJE).setValue(personaje.getRaza());
        myRef.child(String.valueOf(personaje.getId())).child(SUBRAZA_PERSONAJE).setValue(personaje.getSubraza());

        myRef.child(String.valueOf(personaje.getId())).child(DESTREZA_PERSONAJE).setValue(personaje.getDestreza());
        myRef.child(String.valueOf(personaje.getId())).child(CARISMA_PERSONAJE).setValue(personaje.getCarisma());
        myRef.child(String.valueOf(personaje.getId())).child(CONSTITUCION_PERSONAJE).setValue(personaje.getConstitucion());

        myRef.child(String.valueOf(personaje.getId())).child(FUERZA_PERSONAJE).setValue(personaje.getFuerza());
        myRef.child(String.valueOf(personaje.getId())).child(INTELIGENCIA_PERSONAJE).setValue(personaje.getInteligencia());
        myRef.child(String.valueOf(personaje.getId())).child(PERCEPCION_PERSONAJE).setValue(personaje.getPercepcion());

        //Usuario > email > personajes > id_Personaje
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String email = user.getEmail();
        myRef = database.getReference(TABLA_USUARIOS);
        myRef.child(usuario.getNombre()).child(PERSONAJES_USUARIO).push().child(ID_PERSONAJE).setValue(personaje.getId());

    }

    /**Actualiza un personaje*/
    public static void actualizarPersonaje(Map<String, Object> personajeT, Personaje personaje, final Context context){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Personaje > personajes > id > Todos los datos del personaje
        DatabaseReference myRef = database.getReference(TABLA_PERSONAJES);
        myRef.child(personaje.getIdReal()).updateChildren(personajeT).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.guardado_mensaje, Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, R.string.guardado_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**Devuelve un personaje concreto*/
    static Personaje personaje;
    public static Personaje getPersonajeId(String id, final Context context){

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Cojo el personaje de la base de datos
        DatabaseReference myRef = database.getReference();

        // Read from the database
        myRef.child(Utils.TABLA_PERSONAJES).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (ds.exists()) {
                    String nombre = (String) ds.child(NOMBRE_PERSONAJE).getValue();
                    Bitmap imagen = null;
                    String raza = "";
                    String subraza = "";
                    String clase = "";
                    int fuerza = 0;
                    int destreza = 0;
                    int percepcion = 0;
                    int constitucion = 0;
                    int inteligencia = 0;
                    int carisma = 0;

                    try {
                        imagen = StringToBitMap(ds.child(IMAGEN_PERSONAJE).getValue().toString());

                    }catch (NullPointerException e){}

                        raza = ds.child(RAZA_PERSONAJE).getValue().toString();
                        subraza = ds.child(SUBRAZA_PERSONAJE).getValue().toString();
                        clase =  ds.child(CLASE_PERSONAJE).getValue().toString();

                        fuerza = Integer.parseInt(ds.child(FUERZA_PERSONAJE).getValue().toString());
                        destreza = Integer.parseInt(ds.child(DESTREZA_PERSONAJE).getValue().toString());
                        percepcion = Integer.parseInt(ds.child(PERCEPCION_PERSONAJE).getValue().toString());
                        constitucion = Integer.parseInt(ds.child(CONSTITUCION_PERSONAJE).getValue().toString());
                        inteligencia = Integer.parseInt(ds.child(INTELIGENCIA_PERSONAJE).getValue().toString());
                        carisma = Integer.parseInt(ds.child(CARISMA_PERSONAJE).getValue().toString());


                    personaje = new Personaje(nombre, raza, subraza, clase, fuerza, destreza, percepcion, constitucion, inteligencia, carisma, imagen);
                    personaje.setIdReal(ds.getKey());

                } else {
                    Toast.makeText(context, R.string.error_general, Toast.LENGTH_LONG).show();
                    personaje = new Personaje();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return personaje;
    }


    /**Devuelve la lista de personajes**/
    public static ArrayList<Personaje> getPersonajes(final Usuario usuario){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<Personaje> personajes = new ArrayList<>();

        //Obtengo una lista de la base de datos
        final DatabaseReference[] myRef = {database.getReference(TABLA_USUARIOS)}; //La clase en Java

        ////Usuarios > user > personajes

        myRef[0].child(usuario.getNombre()).child(PERSONAJES_USUARIO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Para cada id pedimos una subquery que nos lleve a la partida correspondiente

                        DatabaseReference subRef = database.getReference(TABLA_PERSONAJES);

                        //Personajes > idPartida
                        subRef.child(ds.child(ID_PERSONAJE).getValue().toString()).addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot subDS) {

                                        if (subDS.exists()){
                                            String nombre = (String) subDS.child(NOMBRE_PERSONAJE).getValue();
                                            Bitmap imagen = null;
                                            String raza = "";
                                            String subraza = "";
                                            String oficio = "";
                                            int fuerza = 0;
                                            int agilidad = 0;
                                            int percepcion = 0;
                                            int constitucion = 0;
                                            int inteligencia = 0;
                                            int carisma = 0;

                                            try {
                                                imagen = Utils.StringToBitMap(subDS.child(IMAGEN_PERSONAJE).getValue().toString());
                                            }catch (NullPointerException e){}
                                                raza = subDS.child(RAZA_PERSONAJE).getValue().toString();
                                                subraza = subDS.child(SUBRAZA_PERSONAJE).getValue().toString();
                                                oficio =  subDS.child(CLASE_PERSONAJE).getValue().toString();
                                                fuerza = Integer.parseInt(subDS.child(FUERZA_PERSONAJE).getValue().toString());
                                                agilidad = Integer.parseInt(subDS.child(DESTREZA_PERSONAJE).getValue().toString());
                                                percepcion = Integer.parseInt(subDS.child(PERCEPCION_PERSONAJE).getValue().toString());
                                                constitucion = Integer.parseInt(subDS.child(CONSTITUCION_PERSONAJE).getValue().toString());
                                                inteligencia = Integer.parseInt(subDS.child(INTELIGENCIA_PERSONAJE).getValue().toString());
                                                carisma = Integer.parseInt(subDS.child(CARISMA_PERSONAJE).getValue().toString());



                                            Personaje personajeT = new Personaje(nombre, raza, subraza, oficio, fuerza, agilidad, percepcion, constitucion, inteligencia, carisma, imagen);
                                            personajeT.setIdReal(subDS.getKey()); //Se guarda el ID real, donde se encuentra en la BDD

                                            for (Personaje p: personajes){
                                                if (p.getNombre().equals(personajeT.getNombre()))
                                                    personajes.remove(p);
                                            }

                                            personajes.add(personajeT);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.w(TAG, "Failed to read value.", error.toException());
                                    }
                                });

                        usuario.addPersonajes(ds.getKey()); //Añadimos la ID en la que se encuentra este personaje en concreto, así a la hora de borrarlo sabremos a donde ir

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return personajes;
    }

    /**Elimina un personaje*/
    public static void eliminarPersonaje(int pos, final Context context, ArrayList<Personaje> personajes, Usuario usuario){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Se elimina de Usuario
        DatabaseReference myRef = database.getReference(TABLA_USUARIOS);
        myRef.child(usuario.getNombre()).child(PERSONAJES_USUARIO).child(usuario.getPersonajes().get(pos)).removeValue();

        Personaje temporal = personajes.get(pos);

        //Se elimina de Personaje
        myRef = database.getReference(TABLA_PERSONAJES);
        myRef.child(temporal.getIdReal()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.eliminar_mensaje, Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, R.string.eliminar_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**Inserta una partida*/
    public static void insertarPartida(Partida partida, Usuario usuario){
        //Partida > ID > Cada Dato
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TABLA_PARTIDAS); //Referencia a la clase Java
        myRef.child(String.valueOf(partida.getId())).child(NOMBRE_PARTIDA).setValue(partida.getNombre()); //El .push() es para crear un id único, lo pondría antes del segundo child
        myRef.child(String.valueOf(partida.getId())).child(IMAGEN_PARTIDA).setValue(Utils.BitMapToString(partida.getImagen()));
        myRef.child(String.valueOf(partida.getId())).child("tipoPartida").setValue(partida.getTipoPartida());

        ArrayList razasIncluidas = new ArrayList();
        for (Map.Entry m : partida.getRazas().entrySet()) {
            if (m.getValue().equals(true))
                razasIncluidas.add(m.getKey());
        }
        myRef.child(String.valueOf(partida.getId())).child(TABLA_RAZAS).setValue(razasIncluidas);

        ArrayList clasesIncluidas = new ArrayList();
        for (Map.Entry m : partida.getClases().entrySet()) {
            if (m.getValue().equals(true))
                clasesIncluidas.add(m.getKey());
        }
        myRef.child(String.valueOf(partida.getId())).child(TABLA_CLASES).setValue(clasesIncluidas);

        ArrayList rasgosIncluidos = new ArrayList();
        for (Map.Entry m : partida.getRasgos().entrySet()) {
            if (m.getValue().equals(true))
                rasgosIncluidos.add(m.getKey());
        }
        myRef.child(String.valueOf(partida.getId())).child(TABLA_RASGOS).setValue(rasgosIncluidos);

        ArrayList conjurosIncluidos = new ArrayList();
        for (Map.Entry m : partida.getConjuros().entrySet()) {
            if (m.getValue().equals(true))
                conjurosIncluidos.add(m.getKey());
        }
        myRef.child(String.valueOf(partida.getId())).child(TABLA_CONJUROS).setValue(conjurosIncluidos);


        //Usuario > email > personajes > id_Personaje
        myRef = database.getReference(TABLA_USUARIOS);
        myRef.child(usuario.getNombre()).child(PARTIDAS_USUARIO).push().child(ID_PARTIDA).setValue(partida.getId());
    }

    /**Actualiza una partida*/
    public static void actualizarPartida(Map<String, Object> nuevaPartida, Partida partida, final Context context){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Personaje > id > Todos los datos del personaje
        DatabaseReference myRef = database.getReference(TABLA_PARTIDAS);
        myRef.child(partida.getIdReal()).updateChildren(nuevaPartida).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.guardado_mensaje, Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, R.string.guardado_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**Devuelve una partida concreta*/
    public static Partida getPartidaId(String id, final Context context){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        Partida partida = new Partida();

        //Obtengo una lista de la base de datos
        DatabaseReference myRef = database.getReference(TABLA_PARTIDAS); //La clase en Java

        //Partidas > id
        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {

                String nombre = (String) ds.child(NOMBRE_PARTIDA).getValue();
                Bitmap imagen = null;
                TipoPartida tipoPartida = TipoPartida.DnD;
                ArrayList razas = new ArrayList<>();
                ArrayList clases = new ArrayList<>();
                ArrayList rasgos = new ArrayList<>();
                ArrayList conjuros = new ArrayList<>();

                try{
                    imagen = Utils.StringToBitMap(ds.child(Utils.IMAGEN_PARTIDA).getValue().toString());

                }catch (NullPointerException e){}
                    tipoPartida = TipoPartida.valueOf(ds.child("tipoPartida").getValue().toString());


                try{
                    razas = (ArrayList) ds.child(TABLA_RAZAS).getValue();
                    clases = (ArrayList) ds.child(TABLA_CLASES).getValue();
                    rasgos = (ArrayList) ds.child(TABLA_RASGOS).getValue();
                    conjuros = (ArrayList) ds.child(TABLA_CONJUROS).getValue();
                }catch (NullPointerException e){}
                tipoPartida = TipoPartida.valueOf(ds.child("tipoPartida").getValue().toString());


                partida.setIdReal(ds.getKey());
                partida.setNombre(nombre);
                partida.setTipoPartida(tipoPartida);
                partida.setImagen(imagen);
                partida.setRazas(arrayToLinkHAshMap(razas));
                partida.setClases(arrayToLinkHAshMap(clases));
                partida.setRasgos(arrayToLinkHAshMap(rasgos));
                partida.setConjuros(arrayToLinkHAshMap(conjuros));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return partida;
    }

    /**Devuelve una partida por defecto, con todos los rasgos, conjuros, clases y razas**/
    public static Partida getPartidaDefecto(){
        Partida partida = new Partida();

        //Inicializo todos los datos a True
        //Razas
        List<Texto> stringRazas = getRazasFromDatabase();
        LinkedHashMap<Texto, Boolean> razas = new LinkedHashMap<>();
        for (Texto raza: stringRazas)
            razas.put(raza, true);

        //Clases
        List<Texto> stringClases = getClasesFromDatabase();
        LinkedHashMap<Texto, Boolean> clases = new LinkedHashMap<>();
        for (Texto clase: stringClases)
            clases.put(clase, true);

        //Rasgos
        List<Texto> stringRasgos = getRasgosFromDatabase();
        LinkedHashMap<Texto, Boolean> rasgos = new LinkedHashMap<>();
        for (Texto rasgo: stringRasgos)
            rasgos.put(rasgo, true);

        //Conjuros
        List<Texto> stringConjuros = getConjurosFromDatabase();
        LinkedHashMap<Texto, Boolean> conjuros = new LinkedHashMap<>();
        for (Texto conjuro: stringConjuros)
            conjuros.put(conjuro, true);

        //Guardo y devuelvo partida
        partida.setRazas(razas);
        partida.setClases(clases);
        partida.setRasgos(rasgos);
        partida.setConjuros(conjuros);

        return partida;

    }

    /**Devuelve una lista de partidas por Usuario**/
    public static ArrayList<Partida> getPartidas(final Usuario usuario){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<Partida> partidas = new ArrayList<>();

        //Obtengo una lista de la base de datos
        DatabaseReference myRef = database.getReference(TABLA_USUARIOS); //La clase en Java

        //Usuarios > usuario > partidas

        myRef.child(usuario.getNombre()).child(PARTIDAS_USUARIO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Buscamos en partida cada id dentro de Usuario

                        DatabaseReference subRef = database.getReference(TABLA_PARTIDAS);

                        //Partidas > Partida_ID
                        subRef.child(ds.child(ID_PARTIDA).getValue().toString()).addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot subDS) {
                                        String nombre = (String) subDS.child(NOMBRE_PARTIDA).getValue();
                                        Bitmap imagen = null;
                                        TipoPartida tipoPartida = TipoPartida.DnD;
                                        ArrayList razas = new ArrayList<>();
                                        ArrayList clases = new ArrayList<>();
                                        ArrayList rasgos = new ArrayList<>();
                                        ArrayList conjuros = new ArrayList<>();

                                        try{
                                            imagen = Utils.StringToBitMap(subDS.child(IMAGEN_PARTIDA).getValue().toString());
                                            tipoPartida = TipoPartida.valueOf(subDS.child("tipoPartida").getValue().toString());

                                            Log.i("Tipo de variable", subDS.child(TABLA_RAZAS).getValue().getClass().toString());

                                            razas = (ArrayList) subDS.child(TABLA_RAZAS).getValue();
                                            clases = (ArrayList) subDS.child(TABLA_CLASES).getValue();
                                            rasgos = (ArrayList) subDS.child(TABLA_RASGOS).getValue();
                                            conjuros = (ArrayList) subDS.child(TABLA_CONJUROS).getValue();


                                        }catch (NullPointerException e){}

                                        Partida partidaT = new Partida();
                                        partidaT.setIdReal(subDS.getKey());
                                        partidaT.setNombre(nombre);
                                        partidaT.setTipoPartida(tipoPartida);
                                        partidaT.setImagen(imagen);
                                        partidaT.setRazas(arrayToLinkHAshMap(razas));
                                        partidaT.setClases(arrayToLinkHAshMap(clases));
                                        partidaT.setRasgos(arrayToLinkHAshMap(rasgos));
                                        partidaT.setConjuros(arrayToLinkHAshMap(conjuros));

                                        partidas.add(partidaT);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.w(TAG, "Failed to read value.", error.toException());
                                    }
                                });

                        usuario.addPartidas(ds.getKey()); //Añadimos la ID en la que se encuentra este personaje en concreto, así a la hora de borrarlo sabremos a donde ir

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return partidas;
    }

    /**Elimina una partida*/
    public static void eliminarPartida(int pos, final Context context, ArrayList<Partida> partidas, Usuario usuario){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Se elimina de Usuario
        DatabaseReference myRef = database.getReference(TABLA_USUARIOS);
        myRef.child(usuario.getNombre()).child(PARTIDAS_USUARIO).child(usuario.getPartidas().get(pos)).removeValue();

        //Se elimina de Partida
        Partida temporal = partidas.get(pos);
        DatabaseReference secRef = database.getReference(TABLA_PARTIDAS);
        secRef.child(temporal.getIdReal()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.eliminar_mensaje, Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, R.string.eliminar_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**Devuelve lista de usuarios**/
    public static ArrayList<String> getUsuarios(){
        final ArrayList<String> lista = new ArrayList<>();

        //Obtengo una lista de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TABLA_USUARIOS); //La clase en Java

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = ds.getKey();
                        lista.add(nombre);
                        Log.i("Nombre", nombre);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return lista;
    }

    /**Devuelve una lista de subRazas**/
    public static ArrayList<String> getSubRazas(String raza){
        final ArrayList<String> lista = new ArrayList<>();

        //Obtengo una lista de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TABLA_REGLAS); //La clase en Java

        // Read from the database
        myRef.child(TABLA_RAZAS).child(raza).child(RAZA_SUBRAZA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = ds.getKey();
                        lista.add(nombre);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return lista;
    }
}
