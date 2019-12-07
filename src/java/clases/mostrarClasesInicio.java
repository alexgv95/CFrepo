/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import comentarios.Comentario;
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
 * @author programmerFriki
 */
public class mostrarClasesInicio extends HttpServlet {

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
            out.println("<title>Servlet mostrarClasesInicio</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet mostrarClasesInicio at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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

        ArrayList<Clase> clases = new ArrayList<>();
        ServletContext contexto = request.getServletContext();
        String query = "SELECT * FROM clases;";
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
                Clase clase = new Clase(resultSet.getString("CLASE"), resultSet.getString("DESCRIPCION"),
                        resultSet.getString("HORARIO"), resultSet.getString("MONITOR"), getComentarios(resultSet.getString("CLASE")));
                clases.add(clase);
                System.out.println("Clase: " + clase);
            }
            System.out.println(clases);
            request.setAttribute("clases", clases);
            statement.close();

            RequestDispatcher mostrarClases = contexto.getRequestDispatcher("/clases.xhtml");
            mostrarClases.forward(request, response);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(muestraMonitores.class.getName()).log(Level.SEVERE, null, ex);
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

    public ArrayList<Comentario> getComentarios(String nombreClase) throws SQLException {

        ArrayList<Comentario> comentarios = new ArrayList<>();
        String comentario = null;
        int valoracion;
        String query = "SELECT * FROM comentarios where clase = '" + nombreClase + "';";
        ResultSet resultSett = null;
        Connection connectionn = null;
        Statement statementt = null;
        System.out.println(query);
        try {
            System.out.println(query);
            connectionn = datasource.getConnection();
            statementt = connectionn.createStatement();
            resultSett = statementt.executeQuery(query);
            //System.out.println(resultSett);
            while (resultSett.next()) {
                if (resultSett.getString("COMENTARIO").isEmpty()) {
                    comentario = null;
                    System.out.println(resultSett.getString("COMENTARIO"));
                } else {
                    comentario = resultSett.getString("COMENTARIO");
                    valoracion = resultSett.getInt("VALORACION");
                    Comentario comentarioObj = new Comentario(valoracion, comentario);
                    System.out.println("Comentario: " + comentarioObj);
                    comentarios.add(comentarioObj);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        statementt.close();

        return comentarios;

    }

}
