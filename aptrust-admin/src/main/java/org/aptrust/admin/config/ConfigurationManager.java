package org.aptrust.admin.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.aptrust.admin.exception.UninializedException;
import org.aptrust.client.impl.PropertiesClientConfig;
/**
 * 
 * @author danny
 *
 */
public class ConfigurationManager {
    private PropertiesClientConfig config;

    public ConfigurationManager(){
        String path = System.getProperty("client.config.file");

        if(!StringUtils.isBlank(path)){
            loadConfig(path);
        }        
    }
    
    private void loadConfig(String path) {
        Properties p = new Properties();

        File file = new File(path);
        if (!file.exists()){
            throw new RuntimeException("The specified config file could not be found: "
                + path);
        }else{
            try {
                p.load(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load properties: " + path, e);
            }
        }

        config = new PropertiesClientConfig(p);
    }


    public void initialize(Properties properties){
        this.config = new PropertiesClientConfig(properties);
    }

    public PropertiesClientConfig getConfig() throws UninializedException{
        if(!isInitialized()){
            throw new UninializedException();
        }
        
        return this.config;
    }
    
    public boolean isInitialized(){
        return this.config != null;
    }
    
    public void reset(){
        this.config = null;
    }
}
