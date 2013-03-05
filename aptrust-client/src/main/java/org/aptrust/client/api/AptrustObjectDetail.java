package org.aptrust.client.api;

import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.aptrust.common.solr.AptrustSolrDocument;
import org.aptrust.common.solr.SolrField;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.yourmediashelf.fedora.generated.management.DatastreamProfile;

/**
 * 
 * @author Daniel Bernstein
 *
 */
public class AptrustObjectDetail {
    private String objectId;

    public AptrustObjectDetail() {
    }

    public AptrustObjectDetail(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    @SolrField(name=AptrustSolrDocument.ID)
    public void setObjectId(String id) {
        objectId = id;
    }

    public String getTitle(){
        return getObjectId();
    }

    
    
    public List<DatastreamProfile> getDatastreamProfiles(){
        
        List<DatastreamProfile> list = new LinkedList<DatastreamProfile>();
        int i = 0;
        while(i++ < 5){
            DatastreamProfile d = new DatastreamProfile();
            d.setDsMIME("application/pdf");
            d.setDsLabel("Datastream #" +i);
            d.setDsSize(new BigInteger("123423423"));
            GregorianCalendar cal = new GregorianCalendar(2012, i+1, i+1);
            XMLGregorianCalendar value = new XMLGregorianCalendarImpl(cal);
            d.setDsCreateDate(value);
            list.add(d);
        }
        
        return list;
    }
    
}
