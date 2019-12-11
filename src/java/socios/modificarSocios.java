/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socios;

import dataBase.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import login.Usuarios;

/**
 *
 * @author Ignacio Goirena
 */
public class modificarSocios extends HttpServlet {

    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet modificarSocios</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet modificarSocios at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        ServletContext contexto = request.getServletContext();

        Usuarios socio = new Usuarios();
        String dni = request.getParameter("DNI");
        socio.setDni(dni);
        String password = request.getParameter("PASSWORD");
        socio.setPassword(password);
        String tipo = request.getParameter("TIPO");
        socio.setTipo(tipo);
        String nombre = request.getParameter("NOMBRE");
        socio.setNombre(nombre);
        String apellidos = request.getParameter("APELLIDOS");
        socio.setApellidos(apellidos);
        String direccion = request.getParameter("DIRECCION");
        socio.setDireccion(direccion);
        request.setAttribute("socio", socio);

        RequestDispatcher gestionSocios = contexto.getRequestDispatcher("/modificarSocios.xhtml");
        gestionSocios.forward(request, response);

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

        ServletContext context = request.getServletContext();

        String dniOriginal = request.getParameter("DNIORIGINAL");
        String dni = request.getParameter("DNI");
        String password = request.getParameter("PASSWORD");
        String tipo = request.getParameter("TIPO");
        String nombre = request.getParameter("NOMBRE");
        String apellidos = request.getParameter("APELLIDOS");
        String direccion = request.getParameter("DIRECCION");

        DBManager db = new DBManager();
        db.modificarSocio(nombre, dni, password, tipo, apellidos, dniOriginal, direccion);

        RequestDispatcher gestionSocios = context.getRequestDispatcher("/mostrarSocios");
        gestionSocios.forward(request, response);

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
