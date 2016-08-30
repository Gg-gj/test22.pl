package ru.otr.eb.utils.oes.modelimporter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Starter
{
  public Starter() {}
  
  private static Logger log = org.slf4j.LoggerFactory.getLogger(Starter.class);
  
  public static void main(String[] args) throws IOException {
    log.info("**************************************************************************************************\n*************************************************************************************************************************************************");
    

    log.info("Starting OES Model Importer with params " + Arrays.toString(args));
    logOutConfigContent("config/jps-config.xml");
    logOutConfigContent("config/ldap.properties");
    logOutConfigContent("config/import.properties");
    logOutConfigContent("config/model.properties");
    logOutConfigContent("config/ssl.properties");
    try {
      String registerOnlyResources = null;
      String distributeOnly = null;
      for (String arg : args) {
        if (StringUtils.startsWith(arg, "-al=")) {
          System.setProperty("policystore.importing-applications", StringUtils.removeStart(arg, "-al="));
        }
        
        if (StringUtils.startsWith(arg, "-or=")) {
          registerOnlyResources = StringUtils.removeStart(arg, "-or=");
        }
        if (StringUtils.startsWith(arg, "-do=")) {
          distributeOnly = StringUtils.removeStart(arg, "-do=");
        }
      }
      if (StringUtils.isEmpty(registerOnlyResources)) {
        registerOnlyResources = "false";
      }
      if (StringUtils.isEmpty(distributeOnly)) {
        distributeOnly = "false";
      }
      System.setProperty("policystore.import-resources-only", registerOnlyResources);
      
      System.setProperty("policystore.distribute-only", distributeOnly);
      ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "classpath:main-context.xml" });
      OesModelImporter oesModelImporter = (OesModelImporter)appContext.getBean("oesModelImporter");
      oesModelImporter.doImport();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    log.info("Finishing OES Model Importer with params " + Arrays.toString(args));
  }
  
  private static void logOutConfigContent(String path) throws IOException {
    File file = new File(path);
    String content = org.apache.commons.io.FileUtils.readFileToString(file);
    log.info("Content of config file {}: {}", path, content);
  }
}
