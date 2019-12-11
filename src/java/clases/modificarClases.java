/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import dataBase.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alejandro
 */
public class modificarClases extends HttpServlet {

    

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
            out.println("<title>Servlet modificarClases</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet modificarClases at " + request.getContextPath() + "</h1>");
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

        Clase claseAEditar = new Clase();
        claseAEditar.setClase(request.getParameter("clase"));
        claseAEditar.setId_clase(Integer.parseInt(request.getParameter("id_clase")));
        claseAEditar.setDescripcion(request.getParameter("descripcion"));
        claseAEditar.setHorario(request.getParameter("horario"));
        claseAEditar.setId_horario(request.getParameter("id_horario"));
        claseAEditar.setMonitor(request.getParameter("monitor"));
        request.setAttribute("claseAEditar", claseAEditar);

        RequestDispatcher volverAEditar
                = contexto.getRequestDispatcher("/modificarClases.xhtml");
        volverAEditar.forward(request, response);
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

        String claseOriginal = request.getParameter("claseOriginal");
        String claseNueva = request.getParameter("clase");
        String horario = request.getParameter("horario");
        String monitor = request.getParameter("monitor");
        String descripcion = request.getParameter("descripcion");
        String id_horario = request.getParameter("id_horario");

        DBManager db = new DBManager();
        db.modificarClase(descripcion, claseNueva, claseOriginal);
        db.modificarHorario(claseNueva, monitor, horario, id_horario);
        
        RequestDispatcher paginaInicio
                = context.getRequestDispatcher("/mostrarClases");

        paginaInicio.forward(request, response);

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
