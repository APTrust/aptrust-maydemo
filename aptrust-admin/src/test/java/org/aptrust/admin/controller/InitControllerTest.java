package org.aptrust.admin.controller;

import java.util.Properties;

import org.aptrust.admin.config.ConfigurationManager;
import org.aptrust.admin.controller.InitController.FileUpload;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * 
 * @author Daniel Bernstein
 * 
 */
public class InitControllerTest {


    @Test
    public void testInit() throws Exception {
        ConfigurationManager im = EasyMock.createMock(ConfigurationManager.class);
        im.initialize(EasyMock.isA(Properties.class));
        EasyMock.expectLastCall();
        EasyMock.replay(im);
        InitController c = new InitController(im);
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFile("name=value".getBytes());
        
        c.init(fileUpload);
        EasyMock.verify(im);
    }
}
