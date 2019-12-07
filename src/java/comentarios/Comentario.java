/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comentarios;

/**
 *
 * @author Alejandro
 */
public class Comentario {
    int valoracion;
    String comentario;

//    public Comentario(int valoracion, String comentario) {
//        this.valoracion = valoracion;
//        this.comentario = comentario;
//    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Comentario{" + "valoracion=" + valoracion + ", comentario=" + comentario + '}';
    }
    
    
}
