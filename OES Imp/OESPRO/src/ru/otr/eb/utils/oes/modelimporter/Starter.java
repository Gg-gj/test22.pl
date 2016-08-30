// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter;

import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.commons.lang.StringUtils;
import java.util.Arrays;
import org.slf4j.Logger;

public class Starter
{
    private static Logger log;
    
    public static void main(final String[] args) throws IOException {
        Starter.log.info("**************************************************************************************************\n*************************************************************************************************************************************************");
        Starter.log.info("Starting OES Model Importer with params " + Arrays.toString(args));
        logOutConfigContent("config/jps-config.xml");
        logOutConfigContent("config/ldap.properties");
        logOutConfigContent("config/import.properties");
        logOutConfigContent("config/model.properties");
        logOutConfigContent("config/ssl.properties");
        try {
            String registerOnlyResources = null;
            String distributeOnly = null;
            for (final String arg : args) {
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
            final ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "classpath:main-context.xml" });
            final OesModelImporter oesModelImporter = (OesModelImporter)appContext.getBean("oesModelImporter");
            oesModelImporter.doImport();
        }
        catch (Exception e) {
            Starter.log.error(e.getMessage(), (Throwable)e);
        }
        Starter.log.info("Finishing OES Model Importer with params " + Arrays.toString(args));
    }
    
    private static void logOutConfigContent(final String path) throws IOException {
        final File file = new File(path);
        final String content = FileUtils.readFileToString(file);
        Starter.log.info("Content of config file {}: {}", (Object)path, (Object)content);
    }
    
    static {
        Starter.log = LoggerFactory.getLogger((Class)Starter.class);
    }
}
