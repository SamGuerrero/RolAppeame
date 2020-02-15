package es.centroafuera.rolappeame;

import android.graphics.Bitmap;

import java.util.concurrent.atomic.AtomicInteger;

public class Personaje {
    //Datos
    private static final AtomicInteger count = new AtomicInteger(0);
    private long id;
    private String nombre;
    private Bitmap imagen;
    //private String ubicacion; //FUTURO: Lugar donde se encuentra. Lo decidirá el máster
    private Raza raza;
    private Oficio oficio;

    //Stats del personaje
    private int fuerza;
    private int agilidad;
    private int percepcion;
    private int constitucion;
    private int inteligencia;
    private int carisma;

    //Constructores
    public Personaje() {
        this.id = count.incrementAndGet();
    }

    public Personaje(String nombre, Raza raza, Oficio oficio, int fuerza, int agilidad, int percepcion, int constitucion, int inteligencia, int carisma, Bitmap imagen) {
        this.nombre = nombre;
        this.raza = raza;
        this.oficio = oficio;
        this.fuerza = fuerza;
        this.agilidad = agilidad;
        this.percepcion = percepcion;
        this.constitucion = constitucion;
        this.inteligencia = inteligencia;
        this.carisma = carisma;
        this.imagen = imagen;
        this.id = count.incrementAndGet();
    }

    //Setters y Getters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Raza getRaza() {
        return raza;
    }
    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    public Oficio getOficio() {
        return oficio;
    }
    public void setOficio(Oficio oficio) {
        this.oficio = oficio;
    }

    public int getFuerza() {
        return fuerza;
    }
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getAgilidad() {
        return agilidad;
    }
    public void setAgilidad(int agilidad) {
        this.agilidad = agilidad;
    }

    public int getPercepcion() {
        return percepcion;
    }
    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }

    public int getConstitucion() {
        return constitucion;
    }
    public void setConstitucion(int constitucion) {
        this.constitucion = constitucion;
    }

    public int getInteligencia() {
        return inteligencia;
    }
    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    public int getCarisma() {
        return carisma;
    }
    public void setCarisma(int carisma) {
        this.carisma = carisma;
    }

    public Bitmap getImagen() {
        return imagen;
    }
    public void setImagen(Bitmap imagen) { this.imagen = imagen; }
}
