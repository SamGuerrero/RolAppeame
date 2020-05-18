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

    //Dentro de Clases
    public static String CLASE_DESCRIPCION = "descripcion";

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

    /**Inserta un personaje*/
    public static void insertarPersonaje(Personaje personaje, Usuario usuario){
        //Personaje > ID > Cada dato
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Utils.TABLA_PERSONAJES); //Referencia a la clase Java
        myRef.child(String.valueOf(personaje.getId())).child(Utils.NOMBRE_PERSONAJE).setValue(personaje.getNombre()); //El .push() es para crear un id único, lo pondría antes del segundo child
        myRef.child(String.valueOf(personaje.getId())).child(Utils.IMAGEN_PERSONAJE).setValue(Utils.BitMapToString(personaje.getImagen()));
        myRef.child(String.valueOf(personaje.getId())).child("oficio").setValue(personaje.getOficio());
        myRef.child(String.valueOf(personaje.getId())).child("raza").setValue(personaje.getRaza());

        myRef.child(String.valueOf(personaje.getId())).child("agilidad").setValue(personaje.getAgilidad());
        myRef.child(String.valueOf(personaje.getId())).child("carisma").setValue(personaje.getCarisma());
        myRef.child(String.valueOf(personaje.getId())).child("constitucion").setValue(personaje.getConstitucion());

        myRef.child(String.valueOf(personaje.getId())).child("fuerza").setValue(personaje.getFuerza());
        myRef.child(String.valueOf(personaje.getId())).child("inteligencia").setValue(personaje.getInteligencia());
        myRef.child(String.valueOf(personaje.getId())).child("percepcion").setValue(personaje.getPercepcion());

        //Usuario > email > personajes > id_Personaje
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String email = user.getEmail();
        myRef = database.getReference(Utils.TABLA_USUARIOS);
        myRef.child(usuario.getNombre()).child(Utils.PERSONAJES_USUARIO).push().child(Utils.ID_PERSONAJE).setValue(personaje.getId());

    }

    /**Actualiza un personaje*/
    public static void actualizarPersonaje(Map<String, Object> personajeT, Personaje personaje, final Context context){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Personaje > personajes > id > Todos los datos del personaje
        DatabaseReference myRef = database.getReference(Utils.TABLA_PERSONAJES);
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
                    String nombre = (String) ds.child(Utils.NOMBRE_PERSONAJE).getValue();
                    Bitmap imagen = null;
                    String raza = "";
                    String oficio = "";
                    int fuerza = 0;
                    int agilidad = 0;
                    int percepcion = 0;
                    int constitucion = 0;
                    int inteligencia = 0;
                    int carisma = 0;

                    try {
                        imagen = Utils.StringToBitMap(ds.child(Utils.IMAGEN_PERSONAJE).getValue().toString());

                        raza = ds.child("raza").getValue().toString();
                        oficio =  ds.child("oficio").getValue().toString();
                        fuerza = Integer.parseInt(ds.child("fuerza").getValue().toString());
                        agilidad = Integer.parseInt(ds.child("agilidad").getValue().toString());
                        percepcion = Integer.parseInt(ds.child("percepcion").getValue().toString());
                        constitucion = Integer.parseInt(ds.child("constitucion").getValue().toString());
                        inteligencia = Integer.parseInt(ds.child("inteligencia").getValue().toString());
                        carisma = Integer.parseInt(ds.child("carisma").getValue().toString());

                    }catch (NullPointerException e){}

                    personaje = new Personaje(nombre, raza, oficio, fuerza, agilidad, percepcion, constitucion, inteligencia, carisma, imagen);
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
        DatabaseReference myRef = database.getReference(Utils.TABLA_USUARIOS); //La clase en Java

        // Read from the database             //Usuarios > email > personajes

        myRef.child(usuario.getNombre()).child(Utils.PERSONAJES_USUARIO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Para cada id pedimos una subquery que nos lleve a la partida correspondiente
                        DatabaseReference subRef = database.getReference(Utils.TABLA_PERSONAJES);
                        subRef.child(ds.child(Utils.ID_PERSONAJE).getValue().toString()).addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot subDS) {
                                        if (subDS.exists()){
                                            String nombre = (String) subDS.child(Utils.NOMBRE_PERSONAJE).getValue();
                                            Bitmap imagen = null;
                                            String raza = "";
                                            String oficio = "";
                                            int fuerza = 0;
                                            int agilidad = 0;
                                            int percepcion = 0;
                                            int constitucion = 0;
                                            int inteligencia = 0;
                                            int carisma = 0;

                                            try {
                                                imagen = Utils.StringToBitMap(subDS.child(Utils.IMAGEN_PERSONAJE).getValue().toString());

                                                raza = subDS.child("raza").getValue().toString();
                                                oficio =  subDS.child("oficio").getValue().toString();
                                                fuerza = Integer.parseInt(subDS.child("fuerza").getValue().toString());
                                                agilidad = Integer.parseInt(subDS.child("agilidad").getValue().toString());
                                                percepcion = Integer.parseInt(subDS.child("percepcion").getValue().toString());
                                                constitucion = Integer.parseInt(subDS.child("constitucion").getValue().toString());
                                                inteligencia = Integer.parseInt(subDS.child("inteligencia").getValue().toString());
                                                carisma = Integer.parseInt(subDS.child("carisma").getValue().toString());

                                            }catch (NullPointerException e){}

                                            Personaje personajeT = new Personaje(nombre, raza, oficio, fuerza, agilidad, percepcion, constitucion, inteligencia, carisma, imagen);
                                            personajeT.setIdReal(subDS.getKey()); //Se guarda el ID real, donde se encuentra en la BDD
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
        DatabaseReference myRef = database.getReference(Utils.TABLA_USUARIOS);
        myRef.child(usuario.getNombre()).child(Utils.PERSONAJES_USUARIO).child(usuario.getPersonajes().get(pos)).removeValue();

        Personaje temporal = personajes.get(pos);

        //Se elimina de Personaje
        myRef = database.getReference(Utils.TABLA_PERSONAJES);
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
        //Partida > ID > Cada Dato //FIXME: No debería cogerlo por partida.getId sino por un push automatico, guardar luego el código push para meter el resto de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Utils.TABLA_PARTIDAS); //Referencia a la clase Java
        myRef.child(String.valueOf(partida.getId())).child(Utils.NOMBRE_PARTIDA).setValue(partida.getNombre()); //El .push() es para crear un id único, lo pondría antes del segundo child
        myRef.child(String.valueOf(partida.getId())).child(Utils.IMAGEN_PARTIDA).setValue(Utils.BitMapToString(partida.getImagen()));
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
        myRef = database.getReference(Utils.TABLA_USUARIOS);
        myRef.child(usuario.getNombre()).child(Utils.PARTIDAS_USUARIO).push().child(Utils.ID_PARTIDA).setValue(partida.getId());
    }

    /**Actualiza una partida*/
    public static void actualizarPartida(Map<String, Object> partidaT, Partida partida, final Context context){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Personaje > id > Todos los datos del personaje
        DatabaseReference myRef = database.getReference(Utils.TABLA_PARTIDAS);
        myRef.child(partida.getIdReal()).updateChildren(partidaT).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        //Pruebas a mano
        LinkedHashMap<Texto, Boolean> razas = new LinkedHashMap<>();
        razas.put(new Texto("Orco", "Grande y feo"), true);
        razas.put(new Texto("Elfo", "Delgaducho y snob"), true);

        LinkedHashMap<Texto, Boolean> clases = new LinkedHashMap<>();
        clases.put(new Texto("Guerrero", "Espaditas y tal"), true);
        clases.put(new Texto("Mago", "En plan Harry Potter pero sin ser tan inútil"), true);

        LinkedHashMap<Texto, Boolean> rasgos = new LinkedHashMap<>();
        rasgos.put(new Texto("Fortaleza Enana", "Mazo resistente"), true);
        rasgos.put(new Texto("Visión nocturna", "Puedes ver en la oscuridad sin gafas especiales ni nada"), true);

        LinkedHashMap<Texto, Boolean> conjuros = new LinkedHashMap<>();
        conjuros.put(new Texto("Prestidigitacion", "Básicamente tus manos son un mechero"), true);
        conjuros.put(new Texto("Don de lenguas", "Puedes hablar cualquier idioma durante X turnos"), true);

        Partida partida = new Partida("Prueba a mano", null, razas, clases, rasgos, conjuros, TipoPartida.DnD);

        //TODO: Devolver partida de la base de datos

        return partida;
    }

    /**Devuelve una partida por defecto**/
    public static Partida getPartidaDefecto(){
        Partida partida = new Partida();

        //FIXME: Aquí pasa algo raro pero no sé el qué
        //Inicializo todos los datos a True
        //Razas
        /*List<Texto> stringRazas = getRazasFromDatabase();
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
            conjuros.put(conjuro, true);*/

        //Para probar, porque no tengo buena conexión y no me muestra las cosas
        LinkedHashMap<Texto, Boolean> razas = new LinkedHashMap<>();
        razas.put(new Texto("Orco", "Grande y feo"), true);
        razas.put(new Texto("Elfo", "Delgaducho y snob"), true);

        LinkedHashMap<Texto, Boolean> clases = new LinkedHashMap<>();
        clases.put(new Texto("Guerrero", "Espaditas y tal"), true);
        clases.put(new Texto("Mago", "En plan Harry Potter pero sin ser tan inútil"), true);

        LinkedHashMap<Texto, Boolean> rasgos = new LinkedHashMap<>();
        rasgos.put(new Texto("Fortaleza Enana", "Mazo resistente"), true);
        rasgos.put(new Texto("Visión nocturna", "Puedes ver en la oscuridad sin gafas especiales ni nada"), true);

        LinkedHashMap<Texto, Boolean> conjuros = new LinkedHashMap<>();
        conjuros.put(new Texto("Prestidigitacion", "Básicamente tus manos son un mechero"), true);
        conjuros.put(new Texto("Don de lenguas", "Puedes hablar cualquier idioma durante X turnos"), true);

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
        DatabaseReference myRef = database.getReference(Utils.TABLA_USUARIOS); //La clase en Java

        // Read from the database             //Usuarios > email > partidas

        myRef.child(usuario.getNombre()).child(Utils.PARTIDAS_USUARIO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Buscamos en partida cada id dentro de Usuario

                        DatabaseReference subRef = database.getReference(Utils.TABLA_PARTIDAS);
                        subRef.child(ds.child(Utils.ID_PARTIDA).getValue().toString()).addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot subDS) {
                                        String nombre = (String) subDS.child(Utils.NOMBRE_PARTIDA).getValue();
                                        Bitmap imagen = null;
                                        TipoPartida tipoPartida = TipoPartida.DnD;
                                        HashMap<Integer, Texto> razas = new HashMap<>();
                                        HashMap<Integer, Texto> clases = new HashMap<>();
                                        HashMap<Integer, Texto> rasgos = new HashMap<>();
                                        HashMap<Integer, Texto> conjuros = new HashMap<>();

                                        //FIXME: ArrayLis cannot be cast to HashMap
                                       /* try {
                                            imagen = Utils.StringToBitMap(subDS.child(Utils.IMAGEN_PARTIDA).getValue().toString());
                                            tipoPartida = TipoPartida.valueOf(subDS.child("tipoPartida").getValue().toString());

                                            razas = (HashMap<Integer, Texto>) subDS.child(TABLA_RAZAS).getValue();
                                            clases = (HashMap<Integer, Texto>) subDS.child(TABLA_CLASES).getValue();
                                            rasgos = (HashMap<Integer, Texto>) subDS.child(TABLA_RASGOS).getValue();
                                            conjuros = (HashMap<Integer, Texto>) subDS.child(TABLA_CONJUROS).getValue();

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

                                        partidas.add(partidaT);*/
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

        //Una lista de partidas por defecto para probar las cosas
        LinkedHashMap<Texto, Boolean> razas = new LinkedHashMap<>();
        razas.put(new Texto("Orco", "Grande y feo"), true);
        razas.put(new Texto("Elfo", "Delgaducho y snob"), true);

        LinkedHashMap<Texto, Boolean> clases = new LinkedHashMap<>();
        clases.put(new Texto("Guerrero", "Espaditas y tal"), true);
        clases.put(new Texto("Mago", "En plan Harry Potter pero sin ser tan inútil"), true);

        LinkedHashMap<Texto, Boolean> rasgos = new LinkedHashMap<>();
        rasgos.put(new Texto("Fortaleza Enana", "Mazo resistente"), true);
        rasgos.put(new Texto("Visión nocturna", "Puedes ver en la oscuridad sin gafas especiales ni nada"), true);

        LinkedHashMap<Texto, Boolean> conjuros = new LinkedHashMap<>();
        conjuros.put(new Texto("Prestidigitacion", "Básicamente tus manos son un mechero"), true);
        conjuros.put(new Texto("Don de lenguas", "Puedes hablar cualquier idioma durante X turnos"), true);

        Partida partidaT = new Partida("Prueba a mano", null, razas, clases, rasgos, conjuros, TipoPartida.DnD);
        partidas.add(partidaT);

        return partidas;
    }

    private static LinkedHashMap<Texto, Boolean> arrayToLinkHAshMap(HashMap<Integer, Texto> lista) {
        LinkedHashMap<Texto, Boolean> lhm = new LinkedHashMap<>();
        for (Texto t : lista.values())
            lhm.put(t, true);

        return lhm;
    }

    /**Elimina una partida*/
    public static void eliminarPartida(int pos, final Context context, ArrayList<Partida> partidas, Usuario usuario){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Se elimina de Usuario
        DatabaseReference myRef = database.getReference(Utils.TABLA_USUARIOS);
        myRef.child(usuario.getNombre()).child(Utils.PARTIDAS_USUARIO).child(usuario.getPartidas().get(pos)).removeValue();

        //Se elimina de Partida
        Partida temporal = partidas.get(pos);
        DatabaseReference secRef = database.getReference(Utils.TABLA_PARTIDAS);
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

}
