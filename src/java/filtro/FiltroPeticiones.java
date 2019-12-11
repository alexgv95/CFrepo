/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtro;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;



/**
 *
 * @author Ignacio Goirena
 */
public class FiltroPeticiones implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[Filtro Peticiones] Iniciando  el Filtro");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        StringBuilder buffer = new StringBuilder();
        for (Enumeration parametrosPeticion = request.getParameterNames();
                parametrosPeticion.hasMoreElements();) {
            String nombreParametro = (String) parametrosPeticion.nextElement();
            String valoresDeParametros[] = request.getParameterValues(nombreParametro);
            int numeroParametros = valoresDeParametros.length;
            buffer.append(nombreParametro);
            buffer.append(" = ");
            for (int i = 0; i < numeroParametros; i++) {
                buffer.append(valoresDeParametros[i]);
                buffer.append(" | ");
                if (i < numeroParametros - 1) {
                    buffer.append(",");
                }
            }
        }
        System.out.println("[Filtro Peticiones] La peticion ha sido recibida desde la IP: "
                + request.getRemoteAddr());
        if (!buffer.toString().equals("")) {
            System.out.println("[Filtro Peticiones] \tla peticion tiene los parametros: " + buffer);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("[Filtro Peticiones] Destruyendo Filtro");
    }
    
}
