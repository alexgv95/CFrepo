/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perfil;

import dataBase.DBManager;
import java.io.IOException;
import java.sql.SQLException;
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
import login.Usuarios;

/**
 *
 * @author enrique
 */
public class InformacionPerfilSocio extends HttpServlet {

    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request
     * @param response
     * @throws ServletException if a servlet-specific error occurs
     * @throws java.io.IOException

     */

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ServletContext contexto = request.getServletContext();
            HttpSession sesion = request.getSession();
            Integer id_usuario = (Integer) sesion.getAttribute("id_usuario");
            
            DBManager db = new DBManager();
            Usuarios usr = db.usuarioID(id_usuario);
            request.setAttribute("nombreSocio", usr.getNombre());
            request.setAttribute("apellidosSocio", usr.getApellidos());
            request.setAttribute("direccionSocio", usr.getDireccion());

            RequestDispatcher mostrarDescripcion = contexto.getRequestDispatcher("/perfilSocio.xhtml");
            mostrarDescripcion.forward(request, response);
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(InformacionPerfilSocio.class.getName()).log(Level.SEVERE, null, ex);
        }

         

    }

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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
