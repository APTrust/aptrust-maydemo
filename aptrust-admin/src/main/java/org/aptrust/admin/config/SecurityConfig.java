/*
 * Copyright (c) 2009-2012 DuraSpace. All rights reserved.
 */
package org.aptrust.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * A bean configuration class supporting the security layer.
 * 
 * @author Daniel Bernstein
 * 
 */
@Configuration
@ImportResource(value="/WEB-INF/config/security-config.xml")
public class SecurityConfig {
}
