package org.aptrust.admin.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aptrust.admin.config.ConfigurationManager;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This filter checks that the application has been initialized before allowing requests into the app.
 * Requests will be passed down the chain as long as the app has been initialized or the request is for the 
 * initialization end point (/init).
 * @author Daniel Bernstein
 *
 */
@Component
public class InitCheckFilter implements Filter {

    private ConfigurationManager initializationManager;
    
    @Autowired
    public InitCheckFilter(ConfigurationManager initializationManager) {
        this.initializationManager = initializationManager;
    }
    
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
        throws IOException,
            ServletException {
        
        if(initializationManager.isInitialized() || isInit((HttpServletRequest)request)){
            chain.doFilter(request, response);
        }else{
            HttpServletResponse hresponse = (HttpServletResponse)response;
            hresponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE_503);
            hresponse.getWriter()
                     .write("The AP Trust Admin application must be initialized either remotely or a with a local properties file before it may be accessed.");
       }
    }
    
    private boolean isInit(HttpServletRequest request) {
        return request.getMethod().equals("POST")
            && request.getRequestURI().endsWith(InitController.REQUEST_PATH);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void destroy() {
    }
}
