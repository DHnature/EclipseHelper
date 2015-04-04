package config;


import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class AHelperConfigurationManager implements HelperConfigurationManager {

    public static final String CONFIG_DIR = "config";
    public static final String CONFIG_FILE = "config.properties";
    public static final String STATIC_CONFIGURATION_FILE_NAME = "helper-config/helper-config.properties";
    public static final String RECORDER_JAVA = "recorder.javalocation";
    protected static PropertiesConfiguration staticConfiguration;
    static File userPropsFile;

    PropertiesConfiguration dynamicConfiguration;

    public AHelperConfigurationManager() {
		init();

    }
    @Override
    public String getRecorderJavaPath() {
    	
       return staticConfiguration == null?null:staticConfiguration.getString(RECORDER_JAVA, "java");

    }

    public PropertiesConfiguration getDynamicConfiguration() {
        return dynamicConfiguration;
    }

    public void setDynamicConfiguration(
            PropertiesConfiguration dynamicConfiguration) {
        this.dynamicConfiguration = dynamicConfiguration;
    }

    public PropertiesConfiguration getStaticConfiguration() {
        return staticConfiguration;
    }

    public void setStaticConfiguration(PropertiesConfiguration staticConfiguration) {
        this.staticConfiguration = staticConfiguration;
    }

    public void init() {
        try {
//			 PropertiesConfiguration configuration = new PropertiesConfiguration("./config/config.properties");
//			 PropertiesConfiguration configuration = new PropertiesConfiguration(STATIC_CONFIGURATION_FILE_NAME);
//        	System.out.println ("Working directory ="+ System.getProperty("user.dir"));
//        	System.out.println ("Home directory ="+ System.getProperty("user.home"));

        	
            PropertiesConfiguration configuration = createStaticConfiguration();

//            StaticConfigurationFileRead.newCase(STATIC_CONFIGURATION_FILE_NAME, this);
            setStaticConfiguration(configuration);
//            if (configuration == null)
//            	return;
//            String dynamicConfigurationName = configuration.getString("helper.dynamicConfiguration", "dynamicconfig.properties");
//            
//            File file = new File(dynamicConfigurationName);
//            if (!file.exists()) {
//                file.createNewFile();
////                DynamicConfigurationFileCreated.newCase(dynamicConfigurationName, this);
////	         	convertToDynamicConfiguration();
//            }
//            dynamicConfiguration = new PropertiesConfiguration(dynamicConfigurationName);
//            DynamicConfigurationFileRead.newCase(dynamicConfigurationName, this);

//	         GraderSettings.get().convertToDynamicConfiguration();
        } catch (Exception e) {
//            StaticConfigurationFileNotRead.newCase(STATIC_CONFIGURATION_FILE_NAME, this);
            System.err.println("Error loading config file.");
            System.err.println(e.getMessage());
            e.printStackTrace();

//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
        }
    }

    PropertiesConfiguration createStaticConfiguration() {
    	try {
    		File file= new File (System.getProperty("user.home") + "/" + STATIC_CONFIGURATION_FILE_NAME);
    		if (!file.exists()) {
    			System.err.println("Configuration file not found at:" + file.getAbsolutePath());
			    file= new File (STATIC_CONFIGURATION_FILE_NAME);
    		}
    		if (!file.exists()) {
    			System.err.println("Configuration file not found at:" + file.getAbsolutePath());
    			return null;
    		} else {
    			System.out.println("Configuration file found at:" + file.getAbsolutePath());

    		}
//    		File file = new File (STATIC_CONFIGURATION_FILE_NAME);
//    		if (!file.exists())
//    			file= new File (System.getProperty("user.home") + "/" + STATIC_CONFIGURATION_FILE_NAME);
//    		if (!file.exists()) {
//    			System.err.println("Configuration file not found at:" + file.getAbsolutePath());
//    			return null;
//    		}
    		
			return new PropertiesConfiguration(file.getAbsolutePath());
		} catch (ConfigurationException e) {
			System.err.println("Could not getproperties configuration");
			return null;
		}
    }        
   
}
