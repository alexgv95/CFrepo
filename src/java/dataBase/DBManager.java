/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import clases.Clase;
import comentarios.Comentario;
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
import login.Usuarios;
import maquinas.Maquina;
import monitores.Monitor;

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

    public ArrayList verClasesParaApuntarse(Integer id_usuario) throws NamingException, SQLException {

        String query = "SELECT CLASE, HORARIO, MONITOR, ID_HORARIO FROM horarios WHERE (ID_HORARIO NOT IN (SELECT ID_HORARIO FROM apuntados WHERE (ID_USUARIO=' " + id_usuario + "')));";
        System.out.println(query);
        ArrayList arrayClases = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                Clase clases = new Clase();
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

    public void apuntarseAClase(Integer id_usuario, String id_horario) throws NamingException {

        String query = "INSERT INTO apuntados(ID_USUARIO, ID_HORARIO) VALUE('" + id_usuario + "', '" + id_horario + "');";
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

    public ArrayList mostrarMisClases(Integer id_usuario) {

        String query1 = "SELECT CLASE, HORARIO, MONITOR FROM horarios WHERE "
                + "(ID_HORARIO IN (SELECT ID_HORARIO FROM apuntados WHERE (ID_USUARIO='" + id_usuario + "')));";
        ArrayList misClases = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                Clase clases = new Clase();
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

    public ArrayList mostrarClasesSinRepetir(Integer id_usuario) {

        String query1 = "SELECT DISTINCT CLASE FROM horarios WHERE "
                + "(ID_HORARIO IN (SELECT ID_HORARIO FROM apuntados WHERE (ID_USUARIO='" + id_usuario + "')));";
        ArrayList misClases = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                Clase clases = new Clase();
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

        String query1 = "SELECT CLASE FROM clases";
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

        String query1 = "select DESCRIPCION from clases where (CLASE ='" + clase + "');";
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

        String query1 = "select COMENTARIO from comentarios where (CLASE ='" + clase + "');";
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
        String query = "SELECT COUNT(ID_HORARIO) FROM apuntados WHERE (ID_HORARIO IN (SELECT ID_HORARIO FROM horarios WHERE (CLASE = '" + clase + "')));";
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

    public ArrayList mostrarMaquinas() {

        String query1 = "SELECT MAQUINA, CANTIDAD FROM maquinas";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList maquinasList = new ArrayList();
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                Maquina maquina = new Maquina();
                maquina.setMaquina(rs.getString("MAQUINA"));
                maquina.setCantidad(rs.getInt("CANTIDAD"));
                maquinasList.add(maquina);
            }

        } catch (NamingException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(conn, rs, st);
        }
        return maquinasList;

    }

    public void annadirMaquina(String maquina, int cantidad) {

        String query = "INSERT INTO maquinas(MAQUINA, CANTIDAD) VALUE('" + maquina + "', '" + cantidad + "');";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.desconectar(conn, rs, st);
        }

    }

    public void borrarMaquina(String maquina) {

        String query = "DELETE FROM maquinas WHERE MAQUINA='" + maquina + "';";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.desconectar(conn, rs, st);
        }

    }

    public void modificarMaquina(String NombreMaquina, int cantidad, String maquina) {

        String query = "UPDATE maquinas SET MAQUINA='" + NombreMaquina + "',CANTIDAD='" + cantidad + "'WHERE (MAQUINA ='" + maquina + "');";
        System.out.println(query);
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.desconectar(conn, rs, st);
        }

    }

    public Usuarios usuario(String dni) throws NamingException, SQLException {

        String query = "SELECT TIPO, PASSWORD, ID_USUARIO, NOMBRE FROM usuarios WHERE (DNI='" + dni + "');";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        Usuarios usuario = new Usuarios();
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            usuario.setId_usuario(rs.getInt("ID_USUARIO"));
            usuario.setPassword(rs.getString("PASSWORD"));
            usuario.setTipo(rs.getString("TIPO"));
            usuario.setNombre(rs.getString("NOMBRE"));

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE,
                    "Falló la consulta", ex);
        } finally {

            this.desconectar(conn, rs, st);
        }
        return usuario;
    }

    public Usuarios usuarioID(Integer id_usuario) throws NamingException, SQLException {

        String query = "select NOMBRE, APELLIDOS, DIRECCION from USUARIOS where (ID_USUARIO ='" + id_usuario + "');";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        Usuarios usuario = new Usuarios();
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            usuario.setNombre(rs.getString("NOMBRE"));
            usuario.setApellidos(rs.getString("APELLIDOS"));
            usuario.setDireccion(rs.getString("DIRECCION"));

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE,
                    "Falló la consulta", ex);
        } finally {

            this.desconectar(conn, rs, st);
        }
        return usuario;
    }

//    public ArrayList clasesInicio() throws NamingException, SQLException {
//
//        String query = "select * from clases";
//        Statement st = null;
//        ResultSet rs = null;
//        Connection conn = null;
//
//        ArrayList<Clase> clases = new ArrayList<>();
//        try {
//            conn = this.conectar();
//            st = conn.createStatement();
//            rs = st.executeQuery(query);
//
//            while (rs.next()) {
//                Clase clase = new Clase();
//                clase.setClase(rs.getString("CLASE"));
//                clase.setDescripcion(rs.getString("DESCRIPCION"));
//                clase.setHorario(this.horarioClase(rs.getString("CLASE")));
//                clase.setMonitor(this.monitorClase(rs.getString("CLASE")));
//                clase.setComentarios(this.getComentarios(rs.getString("CLASE")));
//                clases.add(clase);
//
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE,
//                    "Falló la consulta", ex);
//        } finally {
//
//            this.desconectar(conn, rs, st);
//        }
//        return clases;
//    }
    public ArrayList clases() throws NamingException, SQLException {

        String query = "select * from horarios";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<Clase> clases = new ArrayList<>();
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                Clase clase = new Clase();
                clase.setId_horario(rs.getString("ID_HORARIO"));
                clase.setId_clase(rs.getInt("ID_CLASE"));
                clase.setClase(rs.getString("CLASE"));
                clase.setDescripcion(this.descripcionClase(rs.getString("CLASE")));
                clase.setHorario(rs.getString("HORARIO"));
                clase.setMonitor(rs.getString("MONITOR"));
                clase.setComentarios(this.getComentarios(rs.getString("CLASE")));
                clases.add(clase);

            }

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE,
                    "Falló la consulta", ex);
        } finally {

            this.desconectar(conn, rs, st);
        }
        return clases;
    }

    public ArrayList getComentarios(String clase) {

        String query1 = "SELECT * FROM comentarios where (clase = '" + clase + "');";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList comentarios = new ArrayList();
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                Comentario com = new Comentario();
                com.setComentario(rs.getString("COMENTARIO"));
                com.setValoracion(rs.getInt("VALORACION"));
                comentarios.add(com);
            }

        } catch (NamingException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(conn, rs, st);
        }
        return comentarios;

    }

    public String descripcionClase(String clase) {

        String query = "select DESCRIPCION from clases where (CLASE ='" + clase + "');";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        String descripcion = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            descripcion = rs.getString("DESCRIPCION");
            return descripcion;
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            this.desconectar(conn, rs, st);

        }
        return descripcion;
    }

    public void anadirClase(String id_clase, String clase, String descripcion) {

        String query = "INSERT INTO clases (ID_ClASE,CLASE,DESCRIPCION) VALUES('" + id_clase + "','" + clase
                + "', '" + descripcion + "');";
        System.out.println(query);
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);

        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            this.desconectar(conn, rs, st);
        }
    }

    public void anadirHorario(String id_clase, String clase, String horario, String monitor) {

        String query = "INSERT INTO horarios (CLASE,HORARIO,MONITOR,ID_CLASE) VALUES('" + clase
                + "', '" + horario + "','" + monitor + "','" + id_clase + "');";
        System.out.println(query);
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);

        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            this.desconectar(conn, rs, st);
        }
    }

    public void borraClaseHorario(String id_horario) {

        String query = "DELETE FROM horarios where (ID_HORARIO='" + id_horario + "');";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.desconectar(conn, rs, st);
        }

    }
    //    public String horarioClase(String clase) throws NamingException, SQLException {
    //
    //        String query = "select HORARIO from horarios where (CLASE ='" + clase + "');";
    //        Statement st = null;
    //        ResultSet rs = null;
    //        Connection conn = null;
    //        String horario = null;
    //        try {
    //            conn = this.conectar();
    //            st = conn.createStatement();
    //            rs = st.executeQuery(query);
    //            rs.next();
    //            horario = rs.getString("HORARIO");
    //
    //        } catch (SQLException ex) {
    //            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE,
    //                    "Falló la consulta", ex);
    //        } finally {
    //
    //            this.desconectar(conn, rs, st);
    //        }
    //        return horario;
    //    }

    //    public String monitorClase(String clase) throws NamingException, SQLException {
    //
    //        String query = "select MONITOR from horarios where (CLASE ='" + clase + "');";
    //        Statement st = null;
    //        ResultSet rs = null;
    //        Connection conn = null;
    //        String descripcion = null;
    //        try {
    //            conn = this.conectar();
    //            st = conn.createStatement();
    //            rs = st.executeQuery(query);
    //            rs.next();
    //            descripcion = rs.getString("MONITOR");
    //
    //        } catch (SQLException ex) {
    //            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE,
    //                    "Falló la consulta", ex);
    //        } finally {
    //
    //            this.desconectar(conn, rs, st);
    //        }
    //        return descripcion;
    //    }
    public void insertarComentario(String clase, String comentario, String valoracion) {

        String query = "INSERT INTO comentarios(CLASE, COMENTARIO, VALORACION) VALUE('" + clase
                + "','" + comentario + "','" + valoracion + "');";
        System.out.println(query);
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);

        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            this.desconectar(conn, rs, st);
        }
    }

    public void insertarMonitor(Integer dni, String nombreCompleto, String email, String numeroSS, String telefono) {

        String query = "INSERT INTO monitor VALUES(" + dni
                + ", '" + nombreCompleto + "', '" + email + "', '" + numeroSS
                + "', '" + telefono + "');";
        System.out.println(query);
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);

        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            this.desconectar(conn, rs, st);
        }
    }

    public void borrarMonitor(Integer dni) {

        String query = "DELETE FROM monitor where (DNI='" + dni + "');";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.desconectar(conn, rs, st);
        }

    }

    public ArrayList mostrarMonitores() {

        String query1 = "SELECT * FROM monitor";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList monitores = new ArrayList();
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                Monitor mon = new Monitor();
                mon.setDni(rs.getInt("DNI"));
                mon.setEmail(rs.getString("EMAIL"));
                mon.setNombreCompleto(rs.getString("NOMBRE"));
                mon.setNumeroSS(rs.getString("NUMEROSS"));
                mon.setTelefono(rs.getString("TELEFONO"));
                monitores.add(mon);
            }

        } catch (NamingException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(conn, rs, st);
        }
        return monitores;
    }

    public void modificarMonitor(String nombre, int dni, String email, String telefono, String numeroSS, int dniOriginal) {

        String query = "UPDATE monitor SET DNI=" + dni + ", NOMBRE='" + nombre + "', EMAIL='" + email + "', TELEFONO='" + telefono + "', NUMEROSS='" + numeroSS + "' WHERE DNI=" + dniOriginal + ";";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.desconectar(conn, rs, st);
        }
    }

    public ArrayList mostrarSocios() {

        String query1 = "SELECT * FROM usuarios;";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList socios = new ArrayList();
        try {
            conn = this.conectar();
            st = conn.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                Usuarios usr = new Usuarios();
                usr.setApellidos(rs.getString("APELLIDOS"));
                usr.setDireccion(rs.getString("DIRECCION"));
                usr.setDni(rs.getString("DNI"));
                usr.setNombre(rs.getString("NOMBRE"));
                usr.setTipo(rs.getString("TIPO"));
                socios.add(usr);
            }

        } catch (NamingException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(conn, rs, st);
        }
        return socios;

    }

    public void modificarSocio(String nombre, String dni, String password, String tipo, String apellidos, String dniOriginal, String direccion) {

        String query = "UPDATE usuarios SET DNI='" + dni + "', PASSWORD='"
                + password + "', TIPO='" + tipo + "', NOMBRE='" + nombre + "', APELLIDOS='"
                + apellidos + "', DIRECCION ='" + direccion + "' WHERE DNI=" + dniOriginal + ";";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.desconectar(conn, rs, st);
        }
    }

    public void anadirSocio(String nombre, String dni, String password, String tipo, String apellidos, String direccion) {

        String query = "INSERT INTO usuarios (DNI,PASSWORD,TIPO,NOMBRE,APELLIDOS,DIRECCION) VALUES('" + dni
                + "', '" + password + "', '" + tipo + "', '" + nombre + "', '" + apellidos
                + "', '" + direccion + "');";
        System.out.println(query);
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);

        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            this.desconectar(conn, rs, st);
        }
    }

    public void borraSocio(String dni) {

        String query = "DELETE FROM usuarios where (DNI='" + dni + "');";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.desconectar(conn, rs, st);
        }

    }

    public void modificarClase(String descripcion, String clase, String claseOriginal) {

        String query = "UPDATE clases SET DESCRIPCION='" + descripcion + "' WHERE (CLASE='" + claseOriginal + "');";
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.desconectar(conn, rs, st);
        }
    }

    public void modificarHorario(String claseNueva, String monitor, String horario, String id_horario) {

        String query = "UPDATE horarios SET CLASE='" + claseNueva + "', " + "MONITOR='" + monitor + "'," + "HORARIO='" + horario + "' WHERE (ID_HORARIO='" + id_horario + "');";

        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = this.conectar();
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.desconectar(conn, rs, st);
        }
    }

}
