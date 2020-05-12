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

    /*
    Devuelve un ArrayList con las Razas existentes
     */
    public static List<String> getRazasFromDatabase() {
        final ArrayList<String> razas = new ArrayList<>();

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
                        razas.add(nombre);
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

    /*
    Devuelve un ArrayList con las Clases existentes
     */
    public static List<String> getClasesFromDatabase() {
        final ArrayList<String> clases = new ArrayList<>();

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
                        clases.add(nombre);
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

    /*
    Inserta un personaje
     */
    public static void insertarPersonaje(Personaje personaje){
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
        myRef.child("sam").child(Utils.PERSONAJES_USUARIO).push().child(Utils.ID_PERSONAJE).setValue(personaje.getId()); //TODO: Sam es prueba

    }

    /*
    Actualiza un personaje
     */
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

    /*
    Devuelve un personaje concreto
     */
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

    /*
    Elimina un personaje
     */
    public static void eliminarPersonaje(int pos, final Context context, ArrayList<Personaje> personajes){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Usuario usuario = new Usuario("sam"); //TODO: Sam es de prueba
        //Se elimina de Usuario
        DatabaseReference myRef = database.getReference(Utils.TABLA_USUARIOS);
        myRef.child(usuario.getEmail()).child(Utils.PERSONAJES_USUARIO).child(usuario.getPersonajes().get(pos)).removeValue();

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

    /*
    Inserta una partida
     */
    public static void insertarPartida(Partida partida){
        //Partida > ID > Cada Dato
        //TODO: Cuando cree las condiciones todo lo que se añada a la base de datos deberá ser mediante constantes
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Utils.TABLA_PARTIDAS); //Referencia a la clase Java
        myRef.child(String.valueOf(partida.getId())).child(Utils.NOMBRE_PARTIDA).setValue(partida.getNombre()); //El .push() es para crear un id único, lo pondría antes del segundo child
        myRef.child(String.valueOf(partida.getId())).child(Utils.IMAGEN_PARTIDA).setValue(Utils.BitMapToString(partida.getImagen()));
        myRef.child(String.valueOf(partida.getId())).child("tipoPartida").setValue(partida.getTipoPartida());


        //Usuario > email > personajes > id_Personaje
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String email = user.getEmail();
        myRef = database.getReference(Utils.TABLA_USUARIOS);
        myRef.child("sam").child(Utils.PARTIDAS_USUARIO).push().child(Utils.ID_PARTIDA).setValue(partida.getId()); //TODO: Sam es de prueba
    }

    /*
    Elimina una partida
     */
    public static void eliminarPartida(int pos, final Context context, ArrayList<Partida> partidas){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Usuario usuario = new Usuario("sam"); //TODO: Sam es de prueba
        //Se elimina de Usuario
        DatabaseReference myRef = database.getReference(Utils.TABLA_USUARIOS);
        myRef.child(usuario.getEmail()).child(Utils.PARTIDAS_USUARIO).child(usuario.getPartidas().get(pos)).removeValue();

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
