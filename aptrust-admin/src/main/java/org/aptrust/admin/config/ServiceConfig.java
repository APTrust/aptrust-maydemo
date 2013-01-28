/*
 * Copyright (c) 2009-2012 DuraSpace. All rights reserved.
 */
package org.aptrust.admin.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
import org.aptrust.client.impl.AptrustPackageDetail;
import org.aptrust.client.impl.ObjectDescriptor;
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

            @Override
            public PackageSummaryQueryResponse
                findPackageSummaries(String institutionId,
                                     SearchParams searchParams)
                    throws AptrustException {
                List<FacetField> facetFields = new LinkedList<FacetField>();

                FacetField ff = new FacetField("dpnBound");
                ff.add("true", 100);
                ff.add("false", 234);
                facetFields.add(ff);

                List<PackageSummary> packageSummaries =
                    new LinkedList<PackageSummary>();
                PackageSummary ps =
                    new PackageSummary("package1",
                                       "Test Package",
                                       new Date(),
                                       108,
                                       "Udacity",
                                       new HealthCheckInfo(new Date(), true));
                packageSummaries.add(ps);

                return new PackageSummaryQueryResponse(packageSummaries,
                                                       facetFields);
            }
            
            @Override
            public AptrustPackageDetail getPackageDetail(String institutionId, String packageId)
                throws AptrustException {
                
                List<ObjectDescriptor> objectDescriptors = new LinkedList<ObjectDescriptor>();
 
                for(int i = 0; i < 10; i++){
                    String id = "object"+i;
                    objectDescriptors.add(new ObjectDescriptor(id));
                }
                
                
                AptrustPackageDetail ps =
                    new AptrustPackageDetail("package1",
                                       "Test Package",
                                       new Date(),
                                       108,
                                       "Udacity",
                                       new HealthCheckInfo(new Date(), true), objectDescriptors);
                
                return ps;

            }
            
            @Override
            public AptrustObjectDetail getObjectDetail(String institutionId,
                                                       String packageId,
                                                       String objectId)
                throws AptrustException {
                return new AptrustObjectDetail(objectId);
            }
        };
    }
}
