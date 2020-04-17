package es.centroafuera.rolappeame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Utils {
    public static String TABLA_PERSONAJES = "Personaje";
    public static String TABLA_PARTIDAS = "Partida";
    public static String TABLA_USUARIOS = "Usuario";
    public static String TABLA_SITIOS = "Sitio";

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

}
