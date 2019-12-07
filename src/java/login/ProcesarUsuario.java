package login;

import dataBase.DBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcesarUsuario extends HttpServlet {

    DBManager db = new DBManager();
    String rutaSocio;
    String rutaAdmin;
    String rutaCasoError;
    
    @Override
    public void init(ServletConfig config){
     
        rutaSocio = config.getServletContext().getInitParameter("rutaSocio");
        rutaAdmin = config.getServletContext().getInitParameter("rutaAdmin");
        rutaCasoError = config.getInitParameter("rutaCasoError");
    
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext contexto = request.getServletContext();
        HttpSession sesion = request.getSession();
        String dni = request.getParameter("DNI");
        System.out.println(dni);
        try {
            String password = request.getParameter("password");
                    System.out.println(password);
            Usuarios usr = db.usuario(dni);
            String passwordBuena = usr.getPassword();
                    System.out.println(passwordBuena);
            String tipoUsuario = usr.getTipo();

            sesion.setAttribute("id_usuario", usr.getId_usuario());

            if (dni != null && password.equals(passwordBuena)) {

                if (tipoUsuario.equals("SOCIO")) {
                    RequestDispatcher anhadirServlet
                            = contexto.getRequestDispatcher(rutaSocio);
                    anhadirServlet.forward(request, response);
                }

                if (tipoUsuario.equals("ADMIN")) {
                    RequestDispatcher anhadirServlet
                            = contexto.getRequestDispatcher(rutaAdmin);

//                    Cookie cookie = new Cookie("CookieUser", dni);
//                    cookie.setMaxAge(-1);
//                    response.addCookie(cookie);

                    anhadirServlet.forward(request, response);
                }

            } else {
                request.setAttribute("errorMessage", ""
                        + "DNI o contraseña incorrectos");
                RequestDispatcher paginaError
                        = contexto.getRequestDispatcher(rutaCasoError);
                paginaError.forward(request, response);
            } this.destroy();
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "No has introducido una contraseña válida");
            RequestDispatcher paginaError
                    = contexto.getRequestDispatcher(rutaCasoError);
            paginaError.forward(request, response);
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(ProcesarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public void destroy(){
    
        rutaSocio = null;
        rutaAdmin = null;
        rutaCasoError = null;
        System.out.println("Atributos puestos a null de nuevo");
    
    }
}
