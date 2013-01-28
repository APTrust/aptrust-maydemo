/*
 * Copyright (c) 2009-2013 AP Trust. All rights reserved.
 */

package org.aptrust.admin.controller;

import org.aptrust.client.api.AptrustClient;
import org.aptrust.client.api.AptrustObjectDetail;
import org.aptrust.client.impl.AptrustPackageDetail;
import org.aptrust.common.exception.AptrustException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "/html/{institutionId}/package/{packageId}")
    public String
        get(@PathVariable String institutionId,
            @PathVariable String packageId,
            @RequestParam(required=false) String objectId,
            Model model) throws AptrustException {
        
        log.debug("calling institutionId = {}, packageId = {}, objectId = {}",
                  new Object[]{institutionId,
                  packageId,
                  objectId});
        
        AptrustPackageDetail packageDetail =
            this.client.getPackageDetail(institutionId, packageId);

        log.debug("adding package detail to map: {}", packageDetail);
        
        model.addAttribute("packageDetail", packageDetail);

        if (objectId == null) {
            objectId = packageDetail.getObjectDescriptors().get(0).getId();
        }

        AptrustObjectDetail objectDetail =
            this.client.getObjectDetail(institutionId, packageId, objectId);
        
        log.debug("adding object detail to map: {}", objectDetail);
        model.addAttribute("objectDetail", objectDetail);

        return "package";
    }

}
