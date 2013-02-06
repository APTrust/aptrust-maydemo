package org.aptrust.admin.controller;

import java.util.Date;
import java.util.List;

import org.aptrust.client.api.AptrustClient;
import org.aptrust.client.api.IngestProcessSummary;
import org.aptrust.common.exception.AptrustException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Daniel Bernstein Date Dec 15, 2012
 * 
 */
@Controller
public class InstitutionDashboardController {
    private static Logger log = LoggerFactory.getLogger(InstitutionDashboardController.class);

    private AptrustClient client;

    @Autowired
    public InstitutionDashboardController(AptrustClient client) {
        this.client = client;
    }

    @RequestMapping("/html/{institutionId}")
    public String getHome(@PathVariable 
                          @ModelAttribute 
                          String institutionId,
                          Model model) throws AptrustException {
        List<IngestProcessSummary> recentIngests =
            this.client.findIngestProcesses(institutionId,
                                            new Date(),
                                            null,
                                            null);
        model.addAttribute("recentIngests", recentIngests);
        model.addAttribute("summary", this.client.getSummary(institutionId));
        log.debug("displaying home...");
        return "dashboard";
    }
}
