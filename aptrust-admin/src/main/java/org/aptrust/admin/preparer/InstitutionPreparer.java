package org.aptrust.admin.preparer;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.aptrust.client.api.AptrustClient;
import org.aptrust.client.api.InstitutionInfo;
import org.aptrust.common.exception.AptrustException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Daniel Bernstein 
 * Date Jan 15, 2013
 * 
 */
@Component("institutionPreparer")
public class InstitutionPreparer implements ViewPreparer {
    private static Logger log =
        LoggerFactory.getLogger(InstitutionPreparer.class);

    private AptrustClient client;

    @Autowired
    public InstitutionPreparer(AptrustClient client) {
        this.client = client;
    }

    @Override
    public void execute(TilesRequestContext tilesContext,
                        AttributeContext attributeContext) {
        try {

            String institutionId =
                (String) tilesContext.getRequestScope().get("institutionId");
            InstitutionInfo institution;
            institution = this.client.getInstitutionInfo(institutionId);
            attributeContext.putAttribute("institution",
                                          new Attribute(institution),
                                          true);
        } catch (AptrustException e) {
            throw new RuntimeException(e);
        }
    }

}
