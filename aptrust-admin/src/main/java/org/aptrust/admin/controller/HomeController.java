package org.aptrust.admin.controller;

import org.aptrust.client.api.AptrustClient;
import org.aptrust.common.exception.AptrustException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Daniel Bernstein Date Dec 15, 2012
 * 
 */
@Controller
public class HomeController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(HomeController.class);

    private AptrustClient client;

    @Autowired
    public HomeController(AptrustClient client) {
        this.client = client;
    }

    @RequestMapping("/")
    public String getHome(Model model) throws AptrustException {
        log.debug("calling...");
        model.addAttribute("institutions", this.client.getInstitutions());
        return "home";
    }
}
