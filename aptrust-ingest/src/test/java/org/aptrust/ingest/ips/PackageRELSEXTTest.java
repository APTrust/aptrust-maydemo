package org.aptrust.ingest.ips;

import java.net.URISyntaxException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.aptrust.ingest.ips.PackageRELSEXT.Description;
import org.junit.Test;

public class PackageRELSEXTTest {

    @Test
    public void testPackageSerializationRountripping() throws JAXBException, URISyntaxException {
        Description d = new Description();
        d.setAccessConditions("restricted");
        d.setDPNBound(true);
        d.setId("info:fedora/test:test");
        d.setInstitution("uva");
        d.setTitle("Test title");
        d.setContentModelURIs(new PackageRELSEXT.ResourceDesignator[] { new PackageRELSEXT.ResourceDesignator("info:fedora/aptrust:package")});
        d.setIncludedResourceURIs(new PackageRELSEXT.ResourceDesignator[] { new PackageRELSEXT.ResourceDesignator("info:fedora/test:1"), new PackageRELSEXT.ResourceDesignator("info:fedora/test:2")});
        PackageRELSEXT r = new PackageRELSEXT();
        r.setDescription(d);

        JAXBContext jc = JAXBContext.newInstance(PackageRELSEXT.class);
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(r, System.out);
    }
}
