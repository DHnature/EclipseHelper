package config;

import org.apache.commons.configuration.PropertiesConfiguration;

public interface HelperConfigurationManager {
	public  PropertiesConfiguration getStaticConfiguration() ;
	public  void setStaticConfiguration(
			PropertiesConfiguration staticConfiguration) ;
	public  PropertiesConfiguration getDynamicConfiguration() ;
	public  void setDynamicConfiguration(
			PropertiesConfiguration dynamicConfiguration) ;
	void init();
	String getRecorderJavaPath();

}
