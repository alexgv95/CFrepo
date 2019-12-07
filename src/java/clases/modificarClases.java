///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package clases;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.sql.DataSource;
//import monitores.muestraMonitores;
//
///**
// *
// * @author Alejandro
// */
//public class modificarClases extends HttpServlet {
//
//    DataSource datasource;
//
//    
//    @Override
//    public void init() throws ServletException {
//
//        try {
//            InitialContext initialContext = new InitialContext();
//            datasource = (DataSource) initialContext.lookup("jdbc/CEUFIT01");
//            Connection connection = datasource.getConnection();
//            Statement createStatement = connection.createStatement();
//            System.out.println("Habemus Conexion!!");
//        } catch (NamingException | SQLException ex) {
//            Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet modificarClases</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet modificarClases at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        ServletContext contexto = request.getServletContext();
//
//        String clase = request.getParameter("clase");
//        String horario = request.getParameter("horario");
//        String monitor = request.getParameter("monitor");
//        String descripcion = request.getParameter("descripcion");
//
//        Clase claseAEditar = new Clase(clase, descripcion, horario, monitor);
//        request.setAttribute("claseAEditar", claseAEditar);
//
//        RequestDispatcher volverAEditar
//                = contexto.getRequestDispatcher("/modificarClases.xhtml");
//        volverAEditar.forward(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        response.setContentType("text/html;charset=UTF-8");
//        ServletContext context = request.getServletContext();
//
//        Connection connection = null;
//        Statement statement = null;
//        try {
//            String claseOriginal = request.getParameter("claseOriginal");
//            String claseNueva = request.getParameter("clase");
//            String horario = request.getParameter("horario");
//            String monitor = request.getParameter("monitor");
//            String descripcion = request.getParameter("descripcion");
//            System.out.println(claseOriginal);
//            System.out.println(claseNueva);
//            System.out.println(horario);
//            System.out.println(monitor);
//            System.out.println(claseOriginal);
//
//            String query = "UPDATE CLASES SET CLASE='" + claseNueva + "', "
//                    + "HORARIO='" + horario + "', MONITOR='" + monitor + "', "
//                    + "DESCRIPCION='" + descripcion + "' WHERE CLASE='" + claseOriginal + "';";
//            System.out.println(query);
//            Connection conn = datasource.getConnection();
//            Statement stmt = conn.createStatement();
//            stmt.executeUpdate(query);
//
//            RequestDispatcher paginaInicio
//                    = context.getRequestDispatcher("/mostrarClases");
//            paginaInicio.forward(request, response);
//
//        } catch (SQLException ex) {
//            Logger.getLogger(muestraMonitores.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
