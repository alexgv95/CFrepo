/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import clases.TablaDeClases;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author enrique
 */
public class DBManager {

    DataSource datasource;

    public Connection conectar() throws SQLException, NamingException {
        InitialContext initialContext = new InitialContext();
        datasource = (DataSource) initialContext.lookup("jdbc/CEUFIT01");
        Connection con = datasource.getConnection();
        return con;
    }

    public void desconectar(Connection conn, ResultSet rs, Statement st) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE,
                        "No se pudo cerrar el Resulset", ex);
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ArrayList verClasesParaApuntarse(String id_usuario) throws NamingException, SQLException {

        String query = "SELECT CLASE, HORARIO, MONITOR, ID_HORARIO FROM HORARIOS WHERE (ID_HORARIO NOT IN (SELECT ID_HORARIO FROM APUNTADOS WHERE (ID_USUARIO=' " + id_usuario + "')));";
        ArrayList arrayClases = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                TablaDeClases clases = new TablaDeClases();
                clases.setClase(rs.getString("CLASE"));
                clases.setHorario(rs.getString("HORARIO"));
                clases.setMonitor(rs.getString("MONITOR"));
                clases.setId_horario(rs.getString("ID_HORARIO"));
                clases.setOcupacion(this.ocupacionClases(clases.getClase()));
                arrayClases.add(clases);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE,
                    "Falló la consulta", ex);
        } finally {

            this.desconectar(conn, rs, st);
        }
        return arrayClases;
    }

    public void apuntarseAClase(String id_usuario, String id_horario) throws NamingException {

        String query = "INSERT INTO APUNTADOS(ID_USUARIO, ID_HORARIO) VALUE('" + id_usuario + "', '" + id_horario + "');";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE,
                    "Falló la consulta", ex);
        } finally {
            this.desconectar(conn, rs, st);
        }

    }

    public ArrayList mostrarMisClases(String id_usuario) {

        String query1 = "SELECT CLASE, HORARIO, MONITOR FROM HORARIOS WHERE "
                + "(ID_HORARIO IN (SELECT ID_HORARIO FROM APUNTADOS WHERE (ID_USUARIO='" + id_usuario + "')));";
        ArrayList misClases = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                TablaDeClases clases = new TablaDeClases();
                clases.setClase(rs.getString("CLASE"));
                clases.setHorario(rs.getString("HORARIO"));
                clases.setMonitor(rs.getString("MONITOR"));
                clases.setOcupacion(this.ocupacionClases(clases.getClase()));
                misClases.add(clases);
            }

        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(conn, rs, st);
        }
        return misClases;
    }

    public ArrayList mostrarClasesSinRepetir(String id_usuario) {

        String query1 = "SELECT DISTINCT CLASE FROM HORARIOS WHERE "
                + "(ID_HORARIO IN (SELECT ID_HORARIO FROM APUNTADOS WHERE (ID_USUARIO='" + id_usuario + "')));";
        ArrayList misClases = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                TablaDeClases clases = new TablaDeClases();
                clases.setClase(rs.getString("CLASE"));
                misClases.add(clases);
            }

        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(conn, rs, st);
        }
        return misClases;
    }

    public ArrayList mostrarClases() {

        String query1 = "SELECT CLASE FROM CLASES";
        ArrayList clases = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                clases.add(rs.getString("CLASE"));
            }

        } catch (NamingException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(conn, rs, st);
        }
        return clases;

    }

    public String mostrarDescripcion(String clase) {

        String query1 = "select DESCRIPCION from CLASES where (CLASE ='" + clase + "');";
        String descripcion = null;
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query1);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(conn, rs, st);
        }
        return descripcion;
    }

    public ArrayList mostrarComentarios(String clase) {

        String query1 = "select COMENTARIO from COMENTARIOS where (CLASE ='" + clase + "');";
        ArrayList comentarios = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                comentarios.add(rs.getString("COMENTARIO"));
            }

        } catch (NamingException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(conn, rs, st);
        }
        return comentarios;

    }

    public int ocupacionClases(String clase) {

        int ocupacion = 0;
        String query = "SELECT COUNT(ID_HORARIO) FROM APUNTADOS WHERE (ID_USUARIO IN (SELECT ID_USUARIO FROM HORARIOS WHERE (CLASE = '" + clase + "')));";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            ocupacion = rs.getInt(1);

        } catch (NamingException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(conn, rs, st);
        }
        return ocupacion;
    }

}
