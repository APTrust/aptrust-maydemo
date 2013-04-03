/*
 * Copyright (c) 2009-2013 AP Trust. All rights reserved.
 */

package org.aptrust.admin.controller;


import org.aptrust.admin.domain.WebSearchParams;
import org.aptrust.client.api.AptrustClient;
import org.aptrust.client.api.PackageSummaryQueryResponse;
import org.aptrust.client.api.SearchConstraint;
import org.aptrust.common.exception.AptrustException;
import org.aptrust.common.solr.AptrustSolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Daniel Bernstein
 * @created Jan 3, 2013
 * 
 */
@Controller
public class DiscoveryController extends BaseController {
    private static Logger log =
        LoggerFactory.getLogger(DiscoveryController.class);

    static String QUERY_RESPONSE_KEY = "queryResponse";
    static final String SEARCH_PARAMS_KEY = "searchParams";
    
    private AptrustClient client;

    @Autowired
    public DiscoveryController(AptrustClient client) {
        this.client = client;
    }

    public class Constraints extends AutoPopulatingList<SearchConstraint> {
        public Constraints() {
            super(SearchConstraint.class);
        }
    }

    @ModelAttribute
    public Constraints constraints() {
        return new Constraints();
    }

    @RequestMapping(INSTITUTION_ROOT_PATH+"/discovery")
    public String get(@PathVariable @ModelAttribute String institutionId,
                      @ModelAttribute WebSearchParams searchParams,
                      Model model) throws AptrustException {
        log.debug("calling discovery...");
        

        PackageSummaryQueryResponse response =
            client.findPackageSummaries(institutionId, searchParams, AptrustSolrDocument.DPN_BOUND, AptrustSolrDocument.ACCESS_CONTROL_POLICY, AptrustSolrDocument.FAILED_HEATH_CHECK);
        model.addAttribute(QUERY_RESPONSE_KEY, response);
        model.addAttribute(SEARCH_PARAMS_KEY, searchParams);

        return "discovery";
    }

}
