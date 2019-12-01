package es.centroafuera.rolappeame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static es.centroafuera.rolappeame.Constantes.AGILIDAD;
import static es.centroafuera.rolappeame.Constantes.BASEDATOS;
import static es.centroafuera.rolappeame.Constantes.CARISMA;
import static es.centroafuera.rolappeame.Constantes.CONSTITUCION;
import static es.centroafuera.rolappeame.Constantes.FUERZA;
import static es.centroafuera.rolappeame.Constantes.IMAGEN;
import static es.centroafuera.rolappeame.Constantes.INTELIGENCIA;
import static es.centroafuera.rolappeame.Constantes.NOMBRE;
import static es.centroafuera.rolappeame.Constantes.OFICIO;
import static es.centroafuera.rolappeame.Constantes.PERCEPCION;
import static es.centroafuera.rolappeame.Constantes.RAZA;
import static es.centroafuera.rolappeame.Constantes.TABLA_PERSONAJES;

public class BaseDeDatos extends SQLiteOpenHelper {
    public final static int VERSION = 1;

    public BaseDeDatos(Context contexto){
        super(contexto, BASEDATOS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLA_PERSONAJES + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOMBRE + " TEXT, " +
                RAZA + " TEXT, " +
                OFICIO + " TEXT, " +
                FUERZA + " INTEGER, " +
                AGILIDAD + " INTEGER, " +
                PERCEPCION + " INTEGER, " +
                CONSTITUCION + " INTEGER, " +
                INTELIGENCIA + " INTEGER, " +
                CARISMA + " INTEGER, " +
                IMAGEN + " BLOB" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void nuevoPersonaje(Personaje personaje){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOMBRE, personaje.getNombre());
        values.put(RAZA, personaje.getRaza().toString());
        values.put(OFICIO, personaje.getOficio().toString());

        values.put(FUERZA, personaje.getFuerza());
        values.put(AGILIDAD, personaje.getAgilidad());
        values.put(PERCEPCION, personaje.getPercepcion());
        values.put(CONSTITUCION, personaje.getConstitucion());
        values.put(INTELIGENCIA, personaje.getInteligencia());
        values.put(CARISMA, personaje.getCarisma());
        values.put(IMAGEN, Util.getBytes(personaje.getImagen()));

        db.insertOrThrow(TABLA_PERSONAJES, null, values);
        db.close();
    }

    public void borrarPersonaje(Personaje personaje){
        SQLiteDatabase db = getWritableDatabase();

        String[] arguments = new String[]{String.valueOf(personaje.getId())};
        db.delete(TABLA_PERSONAJES, _ID  + " = ?", arguments);
        db.close();
    }

    public List<Personaje> getPersonajes(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Personaje> personajes = new ArrayList<>();

        String[] SELECT = new String[]{_ID, NOMBRE, RAZA, OFICIO, FUERZA, AGILIDAD, PERCEPCION, CONSTITUCION, INTELIGENCIA, CARISMA, IMAGEN};
        Cursor cursor = db.query(TABLA_PERSONAJES, SELECT, null, null, null, null, NOMBRE);


        while (cursor.moveToNext()){
            Personaje personaje = new Personaje();
            personaje.setId(cursor.getLong(0));
            personaje.setNombre(cursor.getString(1));
            personaje.setRaza(Raza.valueOf(cursor.getString(2)));
            personaje.setOficio(Oficio.valueOf(cursor.getString(3)));

            personaje.setFuerza(cursor.getInt(4));
            personaje.setAgilidad(cursor.getInt(5));
            personaje.setPercepcion(cursor.getInt(6));
            personaje.setConstitucion(cursor.getInt(7));
            personaje.setInteligencia(cursor.getInt(8));
            personaje.setCarisma(cursor.getInt(9));

            personaje.setImagen(Util.getBitmap(cursor.getBlob(10)));

            personajes.add(personaje);
        }

        return personajes;
    }

    //Devuelve un personaje concreto egún el ID
    public Personaje getPersonajeId(long id){

        SQLiteDatabase db = getWritableDatabase();

        String[] SELECT = new String[]{_ID, NOMBRE, RAZA, OFICIO, FUERZA, AGILIDAD, PERCEPCION, CONSTITUCION, INTELIGENCIA, CARISMA, IMAGEN};
        Cursor cursor = db.query(TABLA_PERSONAJES, SELECT, _ID + " = " + id, null, null, null, NOMBRE);

        Personaje personaje = new Personaje();
        while (cursor.moveToNext()) {

            personaje = new Personaje();
            personaje.setId(cursor.getLong(0));
            personaje.setNombre(cursor.getString(1));
            personaje.setRaza(Raza.valueOf(cursor.getString(2)));
            personaje.setOficio(Oficio.valueOf(cursor.getString(3)));

            personaje.setFuerza(cursor.getInt(4));
            personaje.setAgilidad(cursor.getInt(5));
            personaje.setPercepcion(cursor.getInt(6));
            personaje.setConstitucion(cursor.getInt(7));
            personaje.setInteligencia(cursor.getInt(8));
            personaje.setCarisma(cursor.getInt(9));

            personaje.setImagen(Util.getBitmap(cursor.getBlob(10)));
        }

        return personaje;
    }

    //Modifica el personaje
    public void actualizaPersonaje(Personaje personaje){
        SQLiteDatabase db = getWritableDatabase();

        String[] arguments = new String[]{String.valueOf(personaje.getId())};
        ContentValues values = new ContentValues();
        values.put(FUERZA, personaje.getFuerza());
        values.put(AGILIDAD, personaje.getAgilidad());
        values.put(PERCEPCION, personaje.getPercepcion());
        values.put(CONSTITUCION, personaje.getConstitucion());
        values.put(INTELIGENCIA, personaje.getInteligencia());
        values.put(CARISMA, personaje.getCarisma());
        values.put(IMAGEN, Util.getBytes(personaje.getImagen()));

        db.update(TABLA_PERSONAJES, values, _ID + " = ?", arguments);
        db.close();
    }
}
