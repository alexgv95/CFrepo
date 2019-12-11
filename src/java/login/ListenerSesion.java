/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.util.Date;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * 
 */
public class ListenerSesion implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        System.out.print("[Listener Sesión] A las " + getTime() + "  se creo la sesion con ID: "
                + session.getId() + " MaxInactiveInterval="
                + session.getMaxInactiveInterval());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        System.out.println("[Listener Sesión] A las " + getTime() + " se destruyo la sesion con ID: "
                + session.getId());
    }

    private String getTime() {
        return new Date(System.currentTimeMillis()).toString();
    }
}
