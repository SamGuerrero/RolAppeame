package es.centroafuera.rolappeame;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Partida {
    //Básico
    private static final AtomicInteger count = new AtomicInteger(0);
    private long id;
    private String idReal; //Aquí se guardará el Id que tiene realmente en la base de datos

    //Caracteristicas
    private String nombre;
    private Bitmap imagen;
    private TipoPartida tipoPartida;

    //Reglas
    private LinkedHashMap<Raza, Boolean> razas;
    private LinkedHashMap<Oficio, Boolean> clases;
    /*
    private LinkedHashMap<Hechizo, Boolean> hechizos;
    private LinkedHashMap<Rasgo, Boolean> rasgos;
    private LinkedHashMap<Habilidad, Boolean> habilidades;
    private LinkedHashMap<Equipo, Boolean> inventario;
    private LinkedHashMap<Bestia, Boolean> bestias;

    private LinkedHashMap<Competencia, Boolean> competencias;
    private LinkedHashMap<Paquete, Boolean> paquete;
    */

    private ArrayList<String> personajes; //Guardará los id de cada personaje

    //Constructores
    public Partida() {
    }

    public Partida(long id, String idReal, String nombre, Bitmap imagen, LinkedHashMap<Raza, Boolean> razas, LinkedHashMap<Oficio, Boolean> clases, TipoPartida tipoPartida) {
        this.id = id;
        this.idReal = idReal;
        this.nombre = nombre;
        this.imagen = imagen;
        this.razas = razas;
        this.clases = clases;
        this.tipoPartida = tipoPartida;
        this.personajes = new ArrayList<>();
    }

    public Partida(long id, String idReal, String nombre, Bitmap imagen, TipoPartida tipoPartida, LinkedHashMap<Raza, Boolean> razas, LinkedHashMap<Oficio, Boolean> clases, ArrayList<String> personajes) {
        this.id = id;
        this.idReal = idReal;
        this.nombre = nombre;
        this.imagen = imagen;
        this.tipoPartida = tipoPartida;
        this.razas = razas;
        this.clases = clases;
        this.personajes = personajes;
    }

    //Getters y Setters

    public static AtomicInteger getCount() {
        return count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdReal() {
        return idReal;
    }

    public void setIdReal(String idReal) {
        this.idReal = idReal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public TipoPartida getTipoPartida() {
        return tipoPartida;
    }

    public void setTipoPartida(TipoPartida tipoPartida) {
        this.tipoPartida = tipoPartida;
    }

    public LinkedHashMap<Raza, Boolean> getRazas() {
        return razas;
    }

    public void setRazas(LinkedHashMap<Raza, Boolean> razas) {
        this.razas = razas;
    }

    public LinkedHashMap<Oficio, Boolean> getClases() {
        return clases;
    }

    public void setClases(LinkedHashMap<Oficio, Boolean> clases) {
        this.clases = clases;
    }

    public ArrayList<String> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(ArrayList<String> personajes) {
        this.personajes = personajes;
    }
}


