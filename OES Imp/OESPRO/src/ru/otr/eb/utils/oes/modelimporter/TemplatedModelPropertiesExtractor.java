package ru.otr.eb.utils.oes.modelimporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;








public class TemplatedModelPropertiesExtractor
{
  private static Logger log = LoggerFactory.getLogger(TemplatedModelPropertiesExtractor.class);
  private static final String TEMPLATED_MODEL_CONFIG_FILENAME = "TemplatedPolicyStore.properties";
  private static final String TEMPLATED_MODEL_PATH_PARAM_NAME = "templatedpolicystore.modelfolder";
  
  public TemplatedModelPropertiesExtractor() {}
  
  public void init() throws Exception {
    Properties importProperties = loadFileToProperties(importPropertiesFile);
    String modelFolderPath = (String)importProperties.get("templatedpolicystore.modelfolder");
    
    if (StringUtils.isEmpty(modelFolderPath)) {
      log.info("Property {} is not specified in config '{}'", "templatedpolicystore.modelfolder", importPropertiesFile.getAbsolutePath());
      properties = new Properties();
      return;
    }
    
    File modelFolder = new File(modelFolderPath);
    
    if (!modelFolder.exists()) {
      log.error("Folder '{}' in not exist ", modelFolder.getAbsolutePath());
      throw new RuntimeException("Incorrect configuration");
    }
    
    if (!modelFolder.isDirectory()) {
      log.error("Path '{}' is not directory. You must specify directory path to '{}' property", modelFolder.getAbsolutePath(), "templatedpolicystore.modelfolder");
      throw new RuntimeException("Incorrect configuration");
    }
    
    File modelPropertiesFile = new File(modelFolder, "TemplatedPolicyStore.properties");
    
    properties = loadFileToProperties(modelPropertiesFile);
    for (String propertyName : properties.stringPropertyNames()) {
      String value = (String)properties.get(propertyName);
      if (!StringUtils.contains(value, ",")) {
        properties.setProperty(propertyName, modelFolderPath + "/" + value);
      } else {
        String[] values = StringUtils.split(value, ",");
        String[] updatedValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
          updatedValues[i] = (modelFolderPath + "/" + values[i]);
        }
        properties.setProperty(propertyName, StringUtils.join(updatedValues, ","));
      }
    }
  }
  
  private File importPropertiesFile;
  private Properties properties;
  public Properties getProperties() throws Exception { return properties; }
  

  public void setImportPropertiesFile(File importPropertiesFile) {
    this.importPropertiesFile = importPropertiesFile;
  }
  
  private Properties loadFileToProperties(File file) throws Exception {
    if (!file.exists()) {
      log.error("Config file '{}' in not found", file.getAbsolutePath());
      throw new RuntimeException("Incorrect configuration");
    }
    
    InputStream is = new FileInputStream(file);
    try {
      Properties properties = new Properties();
      properties.load(is);
      log.info("Successfully loaded properties '{}' from file '{}'", properties, file.getAbsolutePath());
      return properties;
    } catch (Exception e) {
      log.error("Cant load properties from file '" + file.getAbsolutePath() + "'", e);
      throw e;
    } finally {
      is.close();
    }
  }
}
