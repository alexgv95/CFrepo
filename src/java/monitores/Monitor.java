/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitores;

/**
 *
 * @author rootjsn
 */
public class Monitor {
    
    int dni;
    String nombreCompleto;
    String email;
    String telefono;
    String numeroSS;
    
    public Monitor(int dni, String nombreCompleto, String email, String telefono, String numeroSS) {
        this.dni=dni;
        this.email=email;
        this.nombreCompleto=nombreCompleto;
        this.numeroSS=numeroSS;
        this.telefono=telefono;
    }
    
    
    

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNumeroSS() {
        return numeroSS;
    }

    public void setNumeroSS(String numeroSS) {
        this.numeroSS = numeroSS;
    }

    @Override
    public String toString() {
        return "Monitor{" + "dni=" + dni + ", nombreCompleto=" + nombreCompleto + ", email=" + email + ", telefono=" + telefono + ", numeroSS=" + numeroSS + '}';
    }
    
    
}