/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import dataBase.DBManager;
import java.io.IOException;
import java.util.ArrayList;
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
public class MostrarMisClases extends HttpServlet {

    DBManager db = new DBManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext contexto = request.getServletContext();
        HttpSession sesion = request.getSession();
        String id_usuario = (String) sesion.getAttribute("id_usuario");

        ArrayList misClases = db.mostrarMisClases(id_usuario);
        request.setAttribute("MisClases", misClases);

        ArrayList clasesSinRepetir = db.mostrarClasesSinRepetir(id_usuario);
        request.setAttribute("clasesSinRepetir", clasesSinRepetir);

        RequestDispatcher rd = contexto.getRequestDispatcher("/horariosSocio.xhtml");
        rd.forward(request, response);

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

//    @Override
//    public void destroy() {
//        try {
//            statement.close();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(mostrarInformacion.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                connection.close();
//
//            } catch (SQLException ex) {
//                Logger.getLogger(mostrarInformacion.class
//                        .getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

}
