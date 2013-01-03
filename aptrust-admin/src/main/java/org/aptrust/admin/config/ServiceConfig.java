/*
 * Copyright (c) 2009-2012 DuraSpace. All rights reserved.
 */
package org.aptrust.admin.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.aptrust.client.api.AptrustClient;
import org.aptrust.client.api.IngestProcessSummary;
import org.aptrust.client.api.IngestStatus;
import org.aptrust.client.api.InstitutionInfo;
import org.aptrust.client.api.Summary;
import org.aptrust.common.exception.AptrustException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A bean configuration class supporting the service layer.
 * 
 * @author Daniel Bernstein
 * 
 */
@Configuration
public class ServiceConfig {

    @Bean
    public AptrustClient aptrustClient() {
        return new AptrustClient() {

            @Override
            public Collection<String> getInstitutionIds()
                throws AptrustException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public InstitutionInfo getInstitutionInfo(String institutionId)
                throws AptrustException {
                return new InstitutionInfo(institutionId, "Institution ("
                    + institutionId + ")");
            }

            @Override
            public Summary getSummary(String institutionId)
                throws AptrustException {
                Summary s = new Summary();
                s.setBytesUsed(10 * 1024 * 1024 * 1024l);
                s.setDpnBoundPackageCount(1000l);
                s.setFailedPackageCount(10l);
                s.setInstitutionPackageCount(10000l);
                s.setObjectCount(101234l);
                s.setInstitutionId(institutionId);
                s.setPackageCount(10332l);
                s.setPrivatePackageCount(5032l);
                s.setPublicPackageCount(5020l);
                return s;
            }

            @Override
            public List<IngestProcessSummary>
                findIngestProcesses(String institutionId,
                                    Date startDate,
                                    String name,
                                    IngestStatus status)
                    throws AptrustException {
                return Arrays.asList(new IngestProcessSummary[] {
                    new IngestProcessSummary(institutionId,
                                             "Test name",
                                             "admin",
                                             IngestStatus.IN_PROGRESS,
                                             50,
                                             new Date(),
                                             null,
                                             null),
                    new IngestProcessSummary(institutionId,
                                             "Test name 2",
                                             "admin",
                                             IngestStatus.FAILED,
                                             50,
                                             new Date(),
                                             null,
                                             "1/4 packages failed") });

            }
        };
    }
}
