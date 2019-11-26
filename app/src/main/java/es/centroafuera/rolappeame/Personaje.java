package es.centroafuera.rolappeame;

public class Personaje {
    //TODO: Añadir Bitmap imagen y agregarlo en: el adaptador, la base de datos
    //Datos básicos
    private long id;
    private String nombre;
    //private String ubicacion; //FUTURO: Lugar donde se encuentra. Lo decidirá el máster
    private String notas;
    private int nivel;
    private Raza raza;
    private Oficio oficio;

    //Stats del personaje
    //TODO: terminar descripciones
    private int fuerza;
    private int agilidad;
    private int percepcion;
    private int constitucion;
    private int inteligencia;
    private int carisma;

    //Constructores
    public Personaje() {
    }

    public Personaje(String nombre, int nivel, Raza raza, Oficio oficio, int fuerza, int agilidad, int percepcion, int constitucion, int inteligencia, int carisma) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.raza = raza;
        this.oficio = oficio;
        this.fuerza = fuerza;
        this.agilidad = agilidad;
        this.percepcion = percepcion;
        this.constitucion = constitucion;
        this.inteligencia = inteligencia;
        this.carisma = carisma;
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

    public String getNotas() {
        return notas;
    }
    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getNivel() {
        return nivel;
    }
    public void setNivel(int nivel) {
        this.nivel = nivel;
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
}
