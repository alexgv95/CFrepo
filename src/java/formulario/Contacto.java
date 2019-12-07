/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formulario;

/**
 *
 * @author rootjsn
 */
public class Contacto {
    
    String idformulario;
    String nombre;
    String email;
    String mensaje;

    public Contacto(String idformulario, String nombre, String email, String mensaje) {
        this.idformulario = idformulario;
        this.nombre = nombre;
        this.email = email;
        this.mensaje = mensaje;
    }

    public String getIdformulario() {
        return idformulario;
    }

    public void setIdformulario(String idformulario) {
        this.idformulario = idformulario;
    }



    public String getFormulario() {
        return idformulario;
    }

    public void setFormulario(String formulario) {
        this.idformulario = formulario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    @Override
    public String toString() {
        return "Contacto{" + "formulario=" + idformulario + ", nombre=" + nombre + ", email=" + email + ", mensaje=" + mensaje + '}';
    }
    
}
