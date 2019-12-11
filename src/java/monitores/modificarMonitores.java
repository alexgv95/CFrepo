/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitores;

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
 * @author rootjsn
 */
public class modificarMonitores extends HttpServlet {

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
            out.println("<title>Servlet modificarMonitores</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet modificarMonitores at " + request.getContextPath() + "</h1>");
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
        Monitor mon = new Monitor();
        mon.setDni(Integer.parseInt(request.getParameter("dni")));
        mon.setNombreCompleto(request.getParameter("nombreCompleto"));
        mon.setEmail(request.getParameter("email"));
        mon.setNumeroSS(request.getParameter("numeroSS"));
        mon.setTelefono(request.getParameter("telefono"));
        request.setAttribute("monitor", mon);
        RequestDispatcher mostrarClases = contexto.getRequestDispatcher("/modificarMonitor.xhtml");
        mostrarClases.forward(request, response);

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

        Integer dniOriginal = Integer.parseInt(request.getParameter("dniOriginal"));
        Integer dni = Integer.parseInt(request.getParameter("dni"));
        String nombre = request.getParameter("nombreCompleto");
        String email = request.getParameter("email");
        String numeroSS = request.getParameter("numeroSS");
        String telefono = request.getParameter("telefono");

        DBManager db = new DBManager();
        db.modificarMonitor(nombre, dni, email, telefono, numeroSS, dniOriginal);

        RequestDispatcher pInici = context.getRequestDispatcher("/muestraMonitores");
        pInici.forward(request, response);

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
