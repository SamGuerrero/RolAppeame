package es.centroafuera.rolappeame;

import java.util.ArrayList;

public class Usuario {

    private String email;
    private ArrayList<String> partidas; //Guarda los IDs de las pertidas
    private ArrayList<String> personajes; //Guarda los IDs de los personajes

    //Constructores
    public Usuario(String email) {
        this.email = email;
        this.partidas = new ArrayList<>();
        this.personajes = new ArrayList<>();
    }

    public Usuario(String email, ArrayList<String> partidas, ArrayList<String> personajes) {
        this.email = email;
        this.partidas = partidas;
        this.personajes = personajes;
    }

    //Getters y Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
