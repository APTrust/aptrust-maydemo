package org.aptrust.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Daniel Bernstein
 * 
 */
@Controller
public class ErrorController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(ErrorController.class);
    
    @RequestMapping("/404")
    public String get404(Model model) {
        log.debug("404 error...");
        model.addAttribute("description", "The page you requested does not exist.");
        return "exception";
    }
}
