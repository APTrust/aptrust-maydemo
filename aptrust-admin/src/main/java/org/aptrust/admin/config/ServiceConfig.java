/*
 * Copyright (c) 2009-2013 AP Trust. All rights reserved.
 */
package org.aptrust.admin.config;

import org.aptrust.admin.exception.UninializedException;
import org.aptrust.client.api.AptrustClient;
import org.aptrust.client.impl.AptrustClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

/**
 * A bean configuration class supporting the service layer.
 * 
 * @author Daniel Bernstein
 * 
 */
@Configuration
public class ServiceConfig {
    
    @Bean
    public ConfigurationManager configurationManager(){
        return new ConfigurationManager();
    }

    @Bean
    @Scope(proxyMode=ScopedProxyMode.INTERFACES, value=WebApplicationContext.SCOPE_SESSION)
    public AptrustClient aptrustClient(ConfigurationManager initializationManager) throws UninializedException {
        AptrustClientImpl client = new AptrustClientImpl(initializationManager.getConfig());
        return client;
    }
}
