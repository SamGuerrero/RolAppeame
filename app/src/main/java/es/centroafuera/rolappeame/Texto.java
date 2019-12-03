package es.centroafuera.rolappeame;

public class Texto {
    private String titulo;
    private String texto;

    public Texto() {
        titulo = "";
        texto = "";
    }

    public Texto(String titulo, String texto){
        this.titulo = titulo;
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}