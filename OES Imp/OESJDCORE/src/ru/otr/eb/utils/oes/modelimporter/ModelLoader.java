package ru.otr.eb.utils.oes.modelimporter;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.FilenameUtils;

import org.slf4j.Logger;

import org.xml.sax.InputSource;

import ru.otr.eb.utils.oes.modelimporter.model.TPolicyStoreDesc;

public class ModelLoader
{
  private static Logger log = org.slf4j.LoggerFactory.getLogger(ModelLoader.class);
  private File templatedPolicyStoreModelFile;
  private File templatedPolicyStoreVerifyXSLTFile;
  private File[] templatedPolicyStoreToPolicyStoreConvertXSLTFile;
  private File templatedPolicyStoreXSDFile;
  private File policyStoreModelFile;
  private File policyStoreVerifyXSLTFile;
  private File policyStoreXSDFile;
  
  public ModelLoader() {}
  
  public TPolicyStoreDesc getPolicyStoreDesc() { if (templatedPolicyStoreModelFile != null) {
      log.info("Templated policy store file '{}' defined, trying converting templated policy store to policy store", templatedPolicyStoreModelFile);
      
      if (!checkTemlatedPolicyStoreAndConvertToPolicyStore()) {
        return null;
      }
    } else {
      log.info("No templated policy store file defined, loading policy store");
    }
    
    if (!checkPolicyStoreDesc()) {
      return null;
    }
    Unmarshaller jaxbUnmarshaller;
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance("ru.otr.eb.utils.oes.modelimporter.model");
      jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    } catch (JAXBException e) {
      log.error("Cant create jaxb unmarshaller", e);
      return null;
    }
    TPolicyStoreDesc policyStoreDesc;
    try
    {
      policyStoreDesc = (TPolicyStoreDesc)jaxbUnmarshaller.unmarshal(new StreamSource(policyStoreModelFile), TPolicyStoreDesc.class).getValue();
    } catch (JAXBException e) {
      log.error("Cant load model from file '{}'", policyStoreModelFile.getAbsolutePath(), e);
      return null;
    }
    
    return policyStoreDesc;
  }
  

  private boolean checkTemlatedPolicyStoreAndConvertToPolicyStore()
  {
    return (checkPolicyStoreDescWithXSD(templatedPolicyStoreXSDFile, templatedPolicyStoreModelFile)) && (checkPolicyStoreDescWithXSLT(templatedPolicyStoreVerifyXSLTFile, templatedPolicyStoreModelFile, "xslt_check_result_templatedpolicystore.txt")) && (convertTemplatedPolicyStoreToPolicyStore());
  }
  

  private boolean convertTemplatedPolicyStoreToPolicyStore()
  {
    if ((templatedPolicyStoreToPolicyStoreConvertXSLTFile == null) || (templatedPolicyStoreToPolicyStoreConvertXSLTFile.length == 0)) {
      log.error("Xslt transformer(s) is not specified");
      return false;
    }
    
    File convertedModelsFolder = new File("ConvertedModels");
    if (!convertedModelsFolder.exists()) {
      if (!convertedModelsFolder.mkdirs()) {
        log.error("Cant create converted models folder '{}'", convertedModelsFolder.getAbsolutePath());
        return false;
      }
      log.info("Converted models folder '{}' successfully created", convertedModelsFolder.getAbsolutePath());
    }
    else {
      log.info("Converted models folder '{}' exist already", convertedModelsFolder.getAbsolutePath());
    }
    
    File sourceFile = templatedPolicyStoreModelFile;
    File resultFile = null;
    String baseFileName = FilenameUtils.getBaseName(sourceFile.getName());
    String baseFileExt = FilenameUtils.getExtension(sourceFile.getName());
    for (int i = 0; i < templatedPolicyStoreToPolicyStoreConvertXSLTFile.length; i++) {
      String convertedPolicyStoreFileName = baseFileName + "-" + i + "-" + System.currentTimeMillis() + "." + baseFileExt;
      resultFile = new File(convertedModelsFolder, convertedPolicyStoreFileName);
      if (!doConvertation(templatedPolicyStoreToPolicyStoreConvertXSLTFile[i], sourceFile, resultFile)) {
        return false;
      }
      sourceFile = resultFile;
    }
    policyStoreModelFile = resultFile;
    log.info("Resulting model for import saved to file '{}'", policyStoreModelFile.getAbsolutePath());
    return true;
  }
  
  private static boolean doConvertation(File xsltFile, File sourceFile, File resultFile)
  {
    try {
      TransformerFactory factory = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", null);
      
      Source xsltSource = new StreamSource(xsltFile);
      Transformer transformer = factory.newTransformer(xsltSource);
      
      Source xmlSource = new StreamSource(sourceFile);
      
      transformer.transform(xmlSource, new StreamResult(resultFile));
      log.info("Templated policy store '{}' successfully transformed to policy store file '{}' with xslt file '{}'", new Object[] { sourceFile.getAbsolutePath(), resultFile.getAbsolutePath(), xsltFile.getAbsolutePath() });
      
      return true;
    } catch (Exception e) {
      log.error("Xslt error of transformation model '{}' to model '{}' with xslt '{}'", new Object[] { sourceFile.getAbsolutePath(), resultFile.getAbsolutePath(), xsltFile.getAbsolutePath(), e });
    }
    return false;
  }
  
  private boolean checkPolicyStoreDesc()
  {
    return (checkPolicyStoreDescWithXSD(policyStoreXSDFile, policyStoreModelFile)) && (checkPolicyStoreDescWithXSLT(policyStoreVerifyXSLTFile, policyStoreModelFile, "xslt_check_result_policystore.txt"));
  }
  




  private static boolean checkPolicyStoreDescWithXSD(File policyStoreXSDFile, File policyStoreModelFile)
  {
    try
    {
      Source streamSource = new StreamSource(policyStoreXSDFile);
      Source xmlSource = new StreamSource(policyStoreModelFile);
      
      SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
      Schema schema = schemaFactory.newSchema(streamSource);
      Validator validator = schema.newValidator();
      validator.validate(xmlSource);
      log.info("Policy model '{}' passed xsd-checking with '{}'", policyStoreModelFile.getName(), policyStoreXSDFile.getName());
      
      return true;
    } catch (Exception e) {
      log.error("Policy model '{}' does NOT pass xsd-checking with '{}'", new Object[] { policyStoreModelFile.getName(), policyStoreXSDFile.getName(), e });
    }
    return false;
  }
  




  private static boolean checkPolicyStoreDescWithXSLT(File policyStoreVerifyXSLTFile, File policyStoreModelFile, String resultFileName)
  {
    try
    {
      TransformerFactory factory = TransformerFactory.newInstance();
      Source xsltSource = new StreamSource(policyStoreVerifyXSLTFile);
      Transformer transformer = factory.newTransformer(xsltSource);
      
      Source xmlSource = new StreamSource(policyStoreModelFile);
      
      File checkResultFile = new File(resultFileName);
      transformer.transform(xmlSource, new StreamResult(checkResultFile));
      









      if (checkResultFile.length() == 0L) {
        log.info("Policy model '{}' passed xslt-checking with '{}'", policyStoreModelFile.getName(), policyStoreVerifyXSLTFile.getName());
        
        return true;
      }
      log.error("Policy model '{}' does NOT pass xslt-checking with '{}':\n{}", new Object[] { policyStoreModelFile.getName(), policyStoreVerifyXSLTFile.getName(), org.apache.commons.io.FileUtils.readFileToString(checkResultFile) });
    }
    catch (Exception e)
    {
      log.error("XSLT check error. Xslt file: '{}', policy store file: {}", new Object[] { policyStoreVerifyXSLTFile.getAbsolutePath(), policyStoreModelFile.getAbsolutePath(), e });
    }
    
    return false;
  }
  
  private static String loadXmlFromFileToString(File file) {
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setXIncludeAware(true);
      dbf.setFeature("http://apache.org/xml/features/xinclude", true);
      DocumentBuilder db = dbf.newDocumentBuilder();
      
      InputSource is = new InputSource(new FileInputStream(file));
      
      org.w3c.dom.Document document = db.parse(is);
      OutputFormat format = new OutputFormat(document);
      format.setLineWidth(65);
      format.setIndenting(true);
      format.setIndent(2);
      Writer out = new java.io.StringWriter();
      XMLSerializer serializer = new XMLSerializer(out, format);
      serializer.serialize(document);
      return out.toString();
    } catch (Exception e) {
      log.error("Erro: {}", e);
      
      throw new RuntimeException(e);
    }
  }
  
  public void setPolicyStoreModelFile(File policyStoreModelFile) {
    this.policyStoreModelFile = policyStoreModelFile;
  }
  
  public void setPolicyStoreVerifyXSLTFile(File policyStoreVerifyXSLTFile) {
    this.policyStoreVerifyXSLTFile = policyStoreVerifyXSLTFile;
  }
  
  public void setPolicyStoreXSDFile(File policyStoreXSDFile) {
    this.policyStoreXSDFile = policyStoreXSDFile;
  }
  
  public void setTemplatedPolicyStoreModelFile(File templatedPolicyStoreModelFile) {
    this.templatedPolicyStoreModelFile = templatedPolicyStoreModelFile;
  }
  
  public void setTemplatedPolicyStoreVerifyXSLTFile(File templatedPolicyStoreVerifyXSLTFile) {
    this.templatedPolicyStoreVerifyXSLTFile = templatedPolicyStoreVerifyXSLTFile;
  }
  
  public void setTemplatedPolicyStoreToPolicyStoreConvertXSLTFile(File[] templatedPolicyStoreToPolicyStoreConvertXSLTFile) {
    this.templatedPolicyStoreToPolicyStoreConvertXSLTFile = templatedPolicyStoreToPolicyStoreConvertXSLTFile;
  }
  
  public void setTemplatedPolicyStoreXSDFile(File templatedPolicyStoreXSDFile) {
    this.templatedPolicyStoreXSDFile = templatedPolicyStoreXSDFile;
  }
}
