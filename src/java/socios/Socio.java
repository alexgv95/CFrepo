/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socios;

/**
 *
 * @author Ignacio Goirena
 */
public class Socio {

    String dni;
    String password;
    String tipo;
    String nombre;
    String apellidos;
    String direccion;

    public Socio(String dni, String password, String tipo, String nombre, String apellidos, String direccion) {
        this.dni = dni;
        this.password = password;
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Socio{" + "dni=" + dni + ", password=" + password + ", tipo=" + tipo + ", nombre=" + nombre + ", apellidos=" + apellidos + ", direccion=" + direccion + '}';
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
