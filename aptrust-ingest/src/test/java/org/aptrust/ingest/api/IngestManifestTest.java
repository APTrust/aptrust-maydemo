package org.aptrust.ingest.api;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import org.aptrust.common.metadata.APTrustMetadata;
import org.junit.Test;

public class IngestManifestTest {

    @Test
    public void testReversabilityOfMarshalling() throws Exception {
        JAXBContext jc = JAXBContext.newInstance(IngestManifest.class, IngestPackage.class, DigitalObject.class, APTrustMetadata.class);
        File originalExampleFile = new File(this.getClass().getClassLoader().getResource("manifest-fedora-sample.xml").toURI());
        File outputFile = File.createTempFile("manifest-fedora-sample", ".xml");
        outputFile.deleteOnExit();
        System.out.println(outputFile.getPath());

        Unmarshaller u = jc.createUnmarshaller();
        IngestManifest manifest = (IngestManifest) u.unmarshal(originalExampleFile);
        Assert.assertEquals(3, manifest.getPackagesToSubmit().size());

        Marshaller m = jc.createMarshaller();
        m.setProperty("jaxb.formatted.output", Boolean.TRUE);
        m.marshal(manifest, new FileOutputStream(outputFile));

        // This is kind of a brittle test... if it fails, spend some time writing a better method 
        // of determining if data is lost in the roundtrip...
        Assert.assertTrue("Marshalled and unmarshalled versions must be equivalent!", Math.abs(originalExampleFile.length() - outputFile.length()) < 2);
    }

}
