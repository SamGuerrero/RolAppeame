package es.centroafuera.rolappeame;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Partida {
    private static final AtomicInteger count = new AtomicInteger(0);
    private long id;
    private String idReal; //Aquí se guardará el Id que tiene realmente en la base de datos
    private String nombre;
    private Bitmap imagen;
    private TipoPartida tipoPartida;
    private int minVida;
    private int maxVida;
    private int minAtaque;
    private int maxAtaque;
    private int minDefensa;
    private int maxDefensa;
    private ArrayList<String> personajes; //Guardará los id de cada personaje

    //Constructores
    public Partida() {
    }

    public Partida(String nombre, Bitmap imagen, TipoPartida tipoPartida, int minVida, int maxVida, int minAtaque, int maxAtaque, int minDefensa, int maxDefensa) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.tipoPartida = tipoPartida;
        this.minVida = minVida;
        this.maxVida = maxVida;
        this.minAtaque = minAtaque;
        this.maxAtaque = maxAtaque;
        this.minDefensa = minDefensa;
        this.maxDefensa = maxDefensa;
        this.id = count.incrementAndGet();
        this.personajes = new ArrayList<>();
    }

    public Partida(String nombre, Bitmap imagen, TipoPartida tipoPartida, int minVida, int maxVida, int minAtaque, int maxAtaque, int minDefensa, int maxDefensa, ArrayList<String> personajes) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.tipoPartida = tipoPartida;
        this.minVida = minVida;
        this.maxVida = maxVida;
        this.minAtaque = minAtaque;
        this.maxAtaque = maxAtaque;
        this.minDefensa = minDefensa;
        this.maxDefensa = maxDefensa;
        this.id = count.incrementAndGet();
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

    public TipoPartida getTipoPartida() {
        return tipoPartida;
    }

    public void setTipoPartida(TipoPartida tipoPartida) {
        this.tipoPartida = tipoPartida;
    }

    public int getMinVida() {
        return minVida;
    }

    public void setMinVida(int minVida) {
        this.minVida = minVida;
    }

    public int getMaxVida() {
        return maxVida;
    }

    public void setMaxVida(int maxVida) {
        this.maxVida = maxVida;
    }

    public int getMinAtaque() {
        return minAtaque;
    }

    public void setMinAtaque(int minAtaque) {
        this.minAtaque = minAtaque;
    }

    public int getMaxAtaque() {
        return maxAtaque;
    }

    public void setMaxAtaque(int maxAtaque) {
        this.maxAtaque = maxAtaque;
    }

    public int getMinDefensa() {
        return minDefensa;
    }

    public void setMinDefensa(int minDefensa) {
        this.minDefensa = minDefensa;
    }

    public int getMaxDefensa() {
        return maxDefensa;
    }

    public void setMaxDefensa(int maxDefensa) {
        this.maxDefensa = maxDefensa;
    }

    public ArrayList<String> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(ArrayList<String> personajes) {
        this.personajes = personajes;
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

    public String getIdReal() {
        return idReal;
    }

    public void setIdReal(String idReal) {
        this.idReal = idReal;
    }

    //Métodos propios de Partida
    public void addPersonaje(String personaje){
        this.personajes.add(personaje);
    }

    public int sacarVida(){
        return (int) Math.round(Math.random()*(maxVida-minVida) + minVida);
    }

    public int sacarAtaque(){
        return (int) Math.round(Math.random()*(maxAtaque-minAtaque) + minAtaque);
    }

    public int sacarDefensa(){
        return (int) Math.round(Math.random()*(maxDefensa-minDefensa) + minDefensa);
    }

}


