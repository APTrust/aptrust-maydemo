package org.aptrust.admin.controller;

import org.aptrust.common.exception.AptrustException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Daniel Bernstein Date Feb 5, 2013
 * 
 */
@Controller
public class AccessDeniedController {
    private static Logger log = LoggerFactory.getLogger(AccessDeniedController.class);
    @RequestMapping("/access-denied")
    public String getLogin() throws AptrustException {
        log.debug("calling...");
        return "access-denied";
    }
}
