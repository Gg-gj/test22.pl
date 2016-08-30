package ru.otr.eb.utils.oes.modelimporter;

import java.io.File;
import java.util.Properties;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathExecutable;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmSequenceIterator;
import net.sf.saxon.s9api.XdmValue;

public class OPSSStorePropertiesExtractor
{
  private File jpsConfigFile;
  
  public OPSSStorePropertiesExtractor() {}
  
  public void init() throws Exception
  {}
  
  public Properties getProperties() throws Exception
  {
    Properties properties = new Properties();
    properties.put("jps.jdbc.driver", getParam("jdbc.driver"));
    properties.put("jps.jdbc.url", getParam("jdbc.url"));
    properties.put("jps.jdbc.username", getParam("jdbc.user"));
    properties.put("jps.jdbc.password", getParam("jdbc.password"));
    return properties;
  }
  
  private String getParam(String paramName) throws Exception
  {
    try {
      Processor proc = new Processor(false);
      XPathCompiler xpath = proc.newXPathCompiler();
      xpath.declareNamespace("", "http://xmlns.oracle.com/oracleas/schema/11/jps-config-11_1.xsd");
      DocumentBuilder builder = proc.newDocumentBuilder();
      


      XdmNode doc = builder.build(jpsConfigFile);
      


      XPathSelector selector = xpath.compile("/jpsConfig/serviceInstances/serviceInstance[@provider='policy.rdbms']/property[@name='" + paramName + "']/@value").load();
      selector.setContextItem(doc);
      

      XdmValue children = selector.evaluate();
      return children.iterator().next().getStringValue();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  



  public void setJpsFilePath(File jpsConfigFile)
  {
    this.jpsConfigFile = jpsConfigFile;
  }
}
