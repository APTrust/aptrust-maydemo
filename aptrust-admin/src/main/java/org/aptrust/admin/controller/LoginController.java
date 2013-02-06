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
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping("/login")
    public String getLogin() throws AptrustException {
        log.debug("calling...");
        return "login";
    }
}
