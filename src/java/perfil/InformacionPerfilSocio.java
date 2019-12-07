/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perfil;

import clases.mostrarInformacion;
import java.io.IOException;
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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author enrique
 */
public class InformacionPerfilSocio extends HttpServlet {

    DataSource datasource;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @throws ServletException if a servlet-specific error occurs
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
        ServletContext contexto = request.getServletContext();
        HttpSession sesion = request.getSession();
        String query1 = null;
        query1 = "select NOMBRE, APELLIDOS, DIRECCION from USUARIOS where (ID_USUARIO ='" + sesion.getAttribute("id_usuario") + "');";
        ResultSet resultSet1 = null;
        Statement statement = null;
        Connection connection = null;
        ArrayList comentarios = new ArrayList();
        try {
            connection = datasource.getConnection();
            System.out.println(query1);
            statement = connection.createStatement();
            resultSet1 = statement.executeQuery(query1);
            resultSet1.next();
            request.setAttribute("nombreSocio", resultSet1.getString("NOMBRE"));
            request.setAttribute("apellidosSocio", resultSet1.getString("APELLIDOS"));
            request.setAttribute("direccionSocio", resultSet1.getString("DIRECCION"));

            RequestDispatcher mostrarDescripcion = contexto.getRequestDispatcher("/perfilSocio.xhtml");
            mostrarDescripcion.forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE,
                    "Falló la consulta", ex);
        } finally {
            if (resultSet1 != null) {
                try {
                    resultSet1.close();
                } catch (SQLException ex) {
                    Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE,
                            "No se pudo cerrar el Resulset", ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

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
