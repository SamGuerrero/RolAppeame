package es.centroafuera.rolappeame;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Evento {
    private static final AtomicInteger count = new AtomicInteger(0);
    private long id;
    private String idT;

    private String nombre;
    private String descripcion;
    private Date fecha;

    public Evento(){
        nombre = "";
        descripcion ="";
        fecha = new Date();
    }

    public Evento(Date fecha){
        nombre = "";
        descripcion ="";
        this.fecha = fecha;
    }

    public Evento(String nombre, String descripcion, Date fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.id = count.incrementAndGet();
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdT() {
        return idT;
    }

    public void setIdT(String idT) {
        this.idT = idT;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
