/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import dataBase.DBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author enrique
 */
public class mostrarClasesDelHorario extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ServletContext contexto = request.getServletContext();
            HttpSession sesion = request.getSession();
            Integer id_usuario =  (Integer) sesion.getAttribute("id_usuario");
            DBManager db = new DBManager();
            ArrayList arrayClases = db.verClasesParaApuntarse(id_usuario);
            request.setAttribute("TablaDeClases", arrayClases);
            RequestDispatcher rd = contexto.getRequestDispatcher("/clasesSocio.xhtml");
            rd.forward(request, response);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(mostrarClasesDelHorario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs EN EL MÉTODO DOPOST, VA A
     * COGER EL ID_CLASE Y SE LO VA A ASIGNAR A UN USUARIO DE UNA TABLA
     * ID_USUARIO-ID_CLASE. PARA SABER EL USUARIO, TIENE QUE GUARDAR LA SESIÓN Y
     * EL ID_USUARIO SERÁ UN ATRIBUTO DE LA SESIÓN.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
