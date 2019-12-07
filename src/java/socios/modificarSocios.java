/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socios;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Ignacio Goirena
 */
public class modificarSocios extends HttpServlet {

    DataSource datasource;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void init() throws ServletException {
        InitialContext initialContext;
        try {
            initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("jdbc/CEUFIT01");
            Connection connection = datasource.getConnection();
            Statement createStatement = connection.createStatement();

        } catch (NamingException | SQLException ex) {
            Logger.getLogger(mostrarSocios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
        String dni = request.getParameter("DNI");
        String password = request.getParameter("PASSWORD");
        String tipo = request.getParameter("TIPO");
        String nombre = request.getParameter("NOMBRE");
        String apellidos = request.getParameter("APELLIDOS");
        String direccion = request.getParameter("DIRECCION");

        Socio socio = new Socio(dni, password, tipo, nombre, apellidos, direccion);

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
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = request.getServletContext();

        Connection connection = null;
        Statement statement = null;

        try {
            InitialContext initialContext = new InitialContext();
            connection = datasource.getConnection();
            statement = connection.createStatement();
            String dniOriginal = request.getParameter("DNIORIGINAL");
            String dni = request.getParameter("DNI");
            String password = request.getParameter("PASSWORD");
            String tipo = request.getParameter("TIPO");
            String nombre = request.getParameter("NOMBRE");
            String apellidos = request.getParameter("APELLIDOS");
            String direccion = request.getParameter("DIRECCION");
            String query = "UPDATE usuarios SET DNI='" + dni + "', PASSWORD='"
                    + password + "', TIPO='" + tipo + "', NOMBRE='" + nombre + "', APELLIDOS='"
                    + apellidos + "', DIRECCION ='" + direccion + "' WHERE DNI=" + dniOriginal + ";";
            System.out.println(query);
            connection = datasource.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);

            RequestDispatcher gestionSocios = context.getRequestDispatcher("/mostrarSocios");

            gestionSocios.forward(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(modificarSocios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(modificarSocios.class.getName()).log(Level.SEVERE, null, ex);
        }

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
