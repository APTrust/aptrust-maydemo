package org.aptrust.admin.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.aptrust.admin.config.ConfigurationManager;
import org.aptrust.client.impl.PropertiesClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

/**
 * This controller handles initialization via a posted properties file. 
 * See client-config-example.properties in the ./aptrust-client project for 
 * file format.
 * @author Daniel Bernstein 
 * 
 */
@Controller
public class InitController extends BaseController {
    public static final String REQUEST_PATH = "/init";

    private static Logger log = LoggerFactory.getLogger(InitController.class);

    private ConfigurationManager configurationManager;

    @Autowired
    public InitController(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @RequestMapping(value = REQUEST_PATH, method = RequestMethod.POST)
    public @ResponseBody String init(FileUpload fileUpload) throws IOException{
        log.debug("initializing...");
        InputStream is = new ByteArrayInputStream(fileUpload.getFile());
        Properties props = new Properties();
        props.load(is);
        this.configurationManager.initialize(props);
        log.debug("initialized");
        return "successfully initialized";
    }
    
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
        throws ServletException {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        // now Spring knows how to handle multipart object and convert them
    }

    public static class FileUpload {
        private byte[] file;
        public void setFile(byte[] file) {
            this.file = file;
        }
        public byte[] getFile() {
            return file;
        }
    }

}




