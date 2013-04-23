package org.aptrust.admin.controller;

import org.aptrust.client.api.AptrustClient;
import org.aptrust.common.exception.AptrustException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.Message;
import org.springframework.binding.message.Severity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

    @RequestMapping(value={"/","/home"})
    public String getHome(Model model) throws AptrustException {
        log.debug("calling...");
        model.addAttribute("institutions", this.client.getInstitutions());
        return "home";
    }

    @RequestMapping(value={"/","/home"}, method=RequestMethod.POST, params={"action=rebuildIndex"})
    public View rebuildIndex(RedirectAttributes redirectAttributes) throws AptrustException {
        log.debug("calling...");
        try{
            this.client.rebuildIndex();
            redirectAttributes.addFlashAttribute("message",
                                                 new Message(null,
                                                             "Initiated index rebuild.",
                                                             Severity.INFO));
        }catch(AptrustException ex){
            redirectAttributes.addFlashAttribute("message",
                                                 new Message(null,
                                                             "Initiated index rebuild failed: " + ex.getMessage(),
                                                             Severity.ERROR));
            
        }
        return new RedirectView("home", true, false, false);
    }

}
