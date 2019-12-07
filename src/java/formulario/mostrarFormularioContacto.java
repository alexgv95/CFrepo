/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formulario;

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
import monitores.muestraMonitores;

/**
 *
 * @author rootjsn
 */
public class mostrarFormularioContacto extends HttpServlet {

    
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
    
    
    @Override
    public void init() throws ServletException {

        try {
            InitialContext initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("jdbc/CEUFIT01");
            Connection connection = datasource.getConnection();
            Statement createStatement = connection.createStatement();
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
            out.println("<title>Servlet mostrarFormularioContacto</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet mostraruiiiiiiiFormularioContacto at " + request.getContextPath() + "</h1>");
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
        mostrarFormulario(request, response);
        
        
        
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
        mostrarFormulario(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    
    public void mostrarFormulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        ArrayList<Contacto> formularioContacto  = new ArrayList<>();
        ServletContext contexto = request.getServletContext();
        String query = "SELECT * FROM contacto;";
        ResultSet resultSet = null;
        Connection conn = null;
        Statement statement = null;
        System.out.println(query);
        try {
            
             conn = datasource.getConnection();
             statement = conn.createStatement();
             resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                Contacto entrada = new Contacto(resultSet.getString("ID_FORMULARIO"), resultSet.getString("NOMBRE"),
                    resultSet.getString("EMAIL"), resultSet.getString("MENSAJE"));
                formularioContacto.add(entrada);
                System.out.println("Entrada: " + entrada);
            }
            conn.close();
            request.setAttribute("formularioContacto", formularioContacto);
            RequestDispatcher mostrarFormulario = contexto.getRequestDispatcher("/mostrarFormularioAdmin.xhtml");
            mostrarFormulario.forward(request, response);        
        } catch (SQLException ex) {
            Logger.getLogger(mostrarFormularioContacto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
