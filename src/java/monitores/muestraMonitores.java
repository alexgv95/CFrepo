/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitores;

import clases.mostrarInformacion;
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
 * @author rootjsn
 */
public class muestraMonitores extends HttpServlet {

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
            out.println("<title>Servlet muestraMonitores</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet muestraMonitores at " + request.getContextPath() + "</h1>");
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
            mostrarTodosMonitores(request, response);
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

                mostrarTodosMonitores(request, response);

        
    
    
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

    public void mostrarTodosMonitores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
                
        ArrayList<Monitor> monitores  = new ArrayList<>();
        ServletContext contexto = request.getServletContext();
        String query = "SELECT * FROM monitor;";
        ResultSet resultSet = null;
        Connection connection = null;
        Statement statement = null;
        System.out.println(query);
        try {
            InitialContext initialContext = new InitialContext();
            
            System.out.println(query);
             connection = datasource.getConnection();
             statement = connection.createStatement();
             resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                Monitor monitor = new Monitor(Integer.parseInt(resultSet.getString("DNI")), resultSet.getString("nombre"),
                    resultSet.getString("Email"), resultSet.getString("numeroSS"), resultSet.getString("telefono"));
                monitores.add(monitor);
                System.out.println("Monitor: " + monitor);
            }
            connection.close();
            
            request.setAttribute("monitores", monitores);
            RequestDispatcher mostrarMonitores = contexto.getRequestDispatcher("/monitoresAdmin.xhtml");
            mostrarMonitores.forward(request, response);        
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(muestraMonitores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}   