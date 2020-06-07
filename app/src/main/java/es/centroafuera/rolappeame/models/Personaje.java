package es.centroafuera.rolappeame.models;

import android.graphics.Bitmap;

import java.util.concurrent.atomic.AtomicInteger;

public class Personaje {
    //Datos
    private static final AtomicInteger count = new AtomicInteger(0);
    private long id;
    private String nombre;
    private Bitmap imagen;
    //private String ubicacion; //FUTURO: Lugar donde se encuentra. Lo decidirá el máster
    private String raza;
    private String subraza;
    private String clase;

    //Stats del personaje
    private int fuerza;
    private int destreza;
    private int percepcion;
    private int constitucion;
    private int inteligencia;
    private int carisma;

    private String idReal; //Aquí se guardará el Id que tiene realmente en la base de datos

    //Constructores
    public Personaje() {
        this.id = count.incrementAndGet();
    }

    public Personaje(String nombre, String raza, String subraza, String clase, int fuerza, int destreza, int percepcion, int constitucion, int inteligencia, int carisma, Bitmap imagen) {
        this.nombre = nombre;
        this.raza = raza;
        this.subraza = subraza;
        this.clase = clase;
        this.fuerza = fuerza;
        this.destreza = destreza;
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

    public String getRaza() {
        return raza;
    }
    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSubraza() {
        return subraza;
    }

    public void setSubraza(String subraza) {
        this.subraza = subraza;
    }

    public String getClase() {
        return clase;
    }
    public void setClase(String clase) {
        this.clase = clase;
    }

    public int getFuerza() {
        return fuerza;
    }
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getDestreza() {
        return destreza;
    }
    public void setDestreza(int destreza) {
        this.destreza = destreza;
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

    public String getIdReal() {
        return idReal;
    }

    public void setIdReal(String idReal) {
        this.idReal = idReal;
    }
}
