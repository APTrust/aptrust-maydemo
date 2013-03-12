/*
 * Copyright (c) 2009-2012 DuraSpace. All rights reserved.
 */
package org.aptrust.admin.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.solr.client.solrj.response.FacetField;
import org.aptrust.client.api.AptrustClient;
import org.aptrust.client.api.AptrustObjectDetail;
import org.aptrust.client.api.HealthCheckInfo;
import org.aptrust.client.api.IngestProcessSummary;
import org.aptrust.client.api.IngestStatus;
import org.aptrust.client.api.InstitutionInfo;
import org.aptrust.client.api.PackageSummary;
import org.aptrust.client.api.PackageSummaryQueryResponse;
import org.aptrust.client.api.SearchParams;
import org.aptrust.client.api.Summary;
import org.aptrust.client.impl.AptrustClientImpl;
import org.aptrust.client.impl.AptrustPackageDetail;
import org.aptrust.client.impl.ObjectDescriptor;
import org.aptrust.client.impl.PropertiesClientConfig;
import org.aptrust.client.utilities.ClientUtil;
import org.aptrust.common.exception.AptrustException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yourmediashelf.fedora.client.FedoraClient;

/**
 * A bean configuration class supporting the service layer.
 * 
 * @author Daniel Bernstein
 * 
 */
@Configuration
public class ServiceConfig {

    @Bean
    public AptrustClient aptrustClient() throws IOException {
        Properties p = new Properties();
        p.load(ClientUtil.class.getClassLoader().getResourceAsStream("client-config.properties"));
        PropertiesClientConfig config = new PropertiesClientConfig(p);
        AptrustClientImpl client = new AptrustClientImpl(config);
        return client;
    }
}
