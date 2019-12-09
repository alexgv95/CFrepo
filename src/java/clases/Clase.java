/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import comentarios.Comentario;
import java.util.ArrayList;

/**
 *
 * @author Alejandro
 */

public class Clase {
    private String clase;
    private String descripcion;
    private String horario;
    private String monitor;
    private ArrayList<Comentario> comentarios;
    private Integer id_clase;
    private Integer id_horario;
    private int ocupacion = 0;
    


//    public Clase(String clase, String descripcion, String horario, String monitor, ArrayList<Comentario> comentarios) {
//        this.clase = clase;
//        this.descripcion = descripcion;
//        this.horario = horario;
//        this.monitor = monitor;
//        this.comentarios = comentarios;
//    }
//
//    public Clase(String clase, String descripcion, String horario, String monitor) {
//        this.clase = clase;
//        this.descripcion = descripcion;
//        this.horario = horario;
//        this.monitor = monitor;
//    }
    

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    @Override
    public String toString() {
        return "Clase{" + "clase=" + getClase() + ", descripcion=" + getDescripcion() + ", horario=" + getHorario() + ", monitor=" + getMonitor() + ", comentarios=" + getComentarios() + '}';
    }

    /**
     * @return the id_clase
     */
    public Integer getId_clase() {
        return id_clase;
    }

    /**
     * @param id_clase the id_clase to set
     */
    public void setId_clase(Integer id_clase) {
        this.id_clase = id_clase;
    }

    /**
     * @return the id_horario
     */
    public Integer getId_horario() {
        return id_horario;
    }

    /**
     * @param id_horario the id_horario to set
     */
    public void setId_horario(Integer id_horario) {
        this.id_horario = id_horario;
    }

    /**
     * @return the ocupacion
     */
    public int getOcupacion() {
        return ocupacion;
    }

    /**
     * @param ocupacion the ocupacion to set
     */
    public void setOcupacion(int ocupacion) {
        this.ocupacion = ocupacion;
    }

 
}