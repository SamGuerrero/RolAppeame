package es.centroafuera.rolappeame.models;

import java.util.ArrayList;

public class Usuario {

    private String nombre;
    private ArrayList<String> partidas; //Guarda los IDs de las pertidas
    private ArrayList<String> personajes; //Guarda los IDs de los personajes

    //Constructores
    public Usuario(String nombre) {
        this.nombre = nombre;
        this.partidas = new ArrayList<>();
        this.personajes = new ArrayList<>();
    }

    public Usuario(String nombre, ArrayList<String> partidas, ArrayList<String> personajes) {
        this.nombre = nombre;
        this.partidas = partidas;
        this.personajes = personajes;
    }

    //Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getPartidas() {
        return partidas;
    }

    public void setPartidas(ArrayList<String> partidas) {
        this.partidas = partidas;
    }

    public ArrayList<String> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(ArrayList<String> personajes) {
        this.personajes = personajes;
    }

    public void addPersonajes(String personaje){
        this.personajes.add(personaje);
    }

    public void addPartidas(String partida){
        this.partidas.add(partida);
    }
}
