package es.centroafuera.rolappeame;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    private LinkedHashMap<Texto, Boolean> razas;
    private LinkedHashMap<Texto, Boolean> clases;
    private LinkedHashMap<Texto, Boolean> rasgos;
    private LinkedHashMap<Texto, Boolean> conjuros;
    /*

    private LinkedHashMap<Habilidad, Boolean> habilidades;
    private LinkedHashMap<Equipo, Boolean> inventario;
    private LinkedHashMap<Bestia, Boolean> bestias;

    private LinkedHashMap<Competencia, Boolean> competencias;
    private LinkedHashMap<Paquete, Boolean> paquete;
    */

    private ArrayList<String> jugadores; //Guardará los id de cada personaje

    //Constructores
    public Partida() {
        this.id = 0;
        this.idReal = "";
        this.nombre = "";
        this.imagen = null;
        this.razas = new LinkedHashMap<>();
        this.clases = new LinkedHashMap<>();
        this.rasgos = new LinkedHashMap<>();
        this.conjuros = new LinkedHashMap<>();
        this.tipoPartida = TipoPartida.DnD;
        this.jugadores = new ArrayList<>();
    }

    public Partida(String nombre, Bitmap imagen, LinkedHashMap<Texto, Boolean> razas, LinkedHashMap<Texto, Boolean> clases, LinkedHashMap<Texto, Boolean> rasgos, LinkedHashMap<Texto, Boolean> conjuros, TipoPartida tipoPartida) {
        this.id = 0;
        this.idReal = "";
        this.nombre = nombre;
        this.imagen = imagen;
        this.razas = razas;
        this.clases = clases;
        this.rasgos = rasgos;
        this.conjuros = conjuros;
        this.tipoPartida = tipoPartida;
        this.jugadores = new ArrayList<>();
    }

    public Partida(long id, String idReal, String nombre, Bitmap imagen, TipoPartida tipoPartida, LinkedHashMap<Texto, Boolean> razas, LinkedHashMap<Texto, Boolean> clases, LinkedHashMap<Texto, Boolean> rasgos, LinkedHashMap<Texto, Boolean> conjuros, ArrayList<String> jugadores) {
        this.id = id;
        this.idReal = idReal;
        this.nombre = nombre;
        this.imagen = imagen;
        this.tipoPartida = tipoPartida;
        this.razas = razas;
        this.clases = clases;
        this.rasgos = rasgos;
        this.conjuros = conjuros;
        this.jugadores = jugadores;
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

    public LinkedHashMap<Texto, Boolean> getRazas() {
        return razas;
    }

    public void setRazas(LinkedHashMap<Texto, Boolean> razas) {
        this.razas = razas;
    }

    public LinkedHashMap<Texto, Boolean> getClases() {
        return clases;
    }

    public void setClases(LinkedHashMap<Texto, Boolean> clases) {
        this.clases = clases;
    }

    public ArrayList<String> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<String> jugadores) {
        this.jugadores = jugadores;
    }

    public LinkedHashMap<Texto, Boolean> getRasgos() {
        return rasgos;
    }

    public void setRasgos(LinkedHashMap<Texto, Boolean> rasgos) {
        this.rasgos = rasgos;
    }

    public LinkedHashMap<Texto, Boolean> getConjuros() {
        return conjuros;
    }

    public void setConjuros(LinkedHashMap<Texto, Boolean> conjuros) {
        this.conjuros = conjuros;
    }
}


