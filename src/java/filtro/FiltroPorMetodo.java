/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Ignacio Goirena
 */
public class FiltroPorMetodo implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[Filtro Métodos] Iniciando  el Filtro");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) request;

        if (rq.getMethod().equalsIgnoreCase("POST") || rq.getMethod().equalsIgnoreCase("GET")) {
            chain.doFilter(request, response);
            System.out.println("[Filtro Métodos] Recibiendo peticiciones POST o GET");
        }
        else{
            System.out.print("[Filtro Métodos] Error petición distinta de GET o POST");
        }

    }

    @Override
    public void destroy() {
        System.out.println("[Filtro Métodos] Destruyendo Filtro");
    }

}
