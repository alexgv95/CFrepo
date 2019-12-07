/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
 * @author Alejandro
 */
public class ManejadorClases extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    DataSource datasource;
    ArrayList<Clase> clases = new ArrayList<>();

    @Override
    public void init() throws ServletException {

        try {
            InitialContext initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("jdbc/CEUFIT01");
            Connection connection = datasource.getConnection();
            Statement createStatement = connection.createStatement();
            System.out.println("Habemus Conexion!!");
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet ManejadorClases</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManejadorClases at " + request.getContextPath() + "</h1>");
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

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Admin</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de las clases:</h1>");
            out.println("<ul>");

            String query = null;
            query = "SELECT *" + "FROM CLASES";
            ResultSet resultSet = null;
            Statement statement = null;
            Connection connection = null;
            try {
                connection = datasource.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Clase clase = new Clase(resultSet.getString("clase"), resultSet.getString("descripcion"),
                            resultSet.getString("horario"), resultSet.getString("monitor"));
                    clases.add(clase);
                    System.out.println("Clase: " + clase);
                }
                //Si que sale del bucle
                request.setAttribute("ArrayClases", clases);
                RequestDispatcher volverAMenu = contexto.getRequestDispatcher("/clasesAdmin.xhtml");
                volverAMenu.forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE,
                                "No se pudo cerrar el Resulset", ex);
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
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

        try {
            response.setContentType("text/html;charset=UTF-8");
            ServletContext contexto = request.getServletContext();

            String clase = request.getParameter("clase");
            String horario = request.getParameter("horario");
            String monitor = request.getParameter("monitor");
            String descripcion = request.getParameter("descripcion");
            System.out.println(clase);
            System.out.println(horario);
            System.out.println(monitor);

            String query = "INSERT INTO CLASES VALUES('" + clase + "', '" + descripcion
                    + "', '" + horario + "', '" + monitor + "');";
            System.out.println(query);

            Connection conn = datasource.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            RequestDispatcher paginaInicio
                    = contexto.getRequestDispatcher("/sociosAdmin.xhtml");
            paginaInicio.forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
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
