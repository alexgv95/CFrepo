/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author enrique
 */
public class TablaDeClases {

    private String clase = null;
    private String horario = null;
    private String monitor = null;
    private String id_horario = null;
    private int ocupacion = 0;

    public TablaDeClases() {
    }

    /**
     * @return the clase
     */
    public String getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * @return the horario
     */
    public String getHorario() {
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(String horario) {
        this.horario = horario;
    }

    /**
     * @return the monitor
     */
    public String getMonitor() {
        return monitor;
    }

    /**
     * @param monitor the monitor to set
     */
    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    /**
     * @return the id_horario
     */
    public String getId_horario() {
        return id_horario;
    }

    /**
     * @param id_horario the id_horario to set
     */
    public void setId_horario(String id_horario) {
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
