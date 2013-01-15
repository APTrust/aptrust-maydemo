/*
 * Copyright (c) 2009-2013 AP Trust. All rights reserved.
 */

package org.aptrust.admin.controller;

import org.aptrust.client.api.AptrustClient;
import org.aptrust.common.exception.AptrustException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Daniel Bernstein Date: Jan 3, 2013
 * 
 */
@Controller
public class PackageDetailController {
    private static Logger log =
        LoggerFactory.getLogger(PackageDetailController.class);

    private AptrustClient client;

    @Autowired
    public PackageDetailController(AptrustClient client) {
        this.client = client;
    }

    @RequestMapping("/html/{institutionId}/package")
    public String get(@PathVariable String institutionId, Model model)
        throws AptrustException {

        log.debug("displaying package detail...");
        return "package";
    }
}