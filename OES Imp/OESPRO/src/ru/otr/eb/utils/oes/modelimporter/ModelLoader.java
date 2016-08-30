// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter;

import org.slf4j.LoggerFactory;
import java.io.Writer;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.StringWriter;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import java.io.InputStream;
import org.xml.sax.InputSource;
import java.io.FileInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.io.FileUtils;
import javax.xml.validation.Validator;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerFactory;
import org.apache.commons.io.FilenameUtils;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;
import ru.otr.eb.utils.oes.modelimporter.model.TPolicyStoreDesc;
import java.io.File;
import org.slf4j.Logger;

public class ModelLoader
{
    private static Logger log;
    private File templatedPolicyStoreModelFile;
    private File templatedPolicyStoreVerifyXSLTFile;
    private File[] templatedPolicyStoreToPolicyStoreConvertXSLTFile;
    private File templatedPolicyStoreXSDFile;
    private File policyStoreModelFile;
    private File policyStoreVerifyXSLTFile;
    private File policyStoreXSDFile;
    
    public TPolicyStoreDesc getPolicyStoreDesc() {
        if (this.templatedPolicyStoreModelFile != null) {
            ModelLoader.log.info("Templated policy store file '{}' defined, trying converting templated policy store to policy store", (Object)this.templatedPolicyStoreModelFile);
            if (!this.checkTemlatedPolicyStoreAndConvertToPolicyStore()) {
                return null;
            }
        }
        else {
            ModelLoader.log.info("No templated policy store file defined, loading policy store");
        }
        if (!this.checkPolicyStoreDesc()) {
            return null;
        }
        Unmarshaller jaxbUnmarshaller;
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance("ru.otr.eb.utils.oes.modelimporter.model");
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        }
        catch (JAXBException e) {
            ModelLoader.log.error("Cant create jaxb unmarshaller", (Throwable)e);
            return null;
        }
        TPolicyStoreDesc policyStoreDesc;
        try {
            policyStoreDesc = jaxbUnmarshaller.unmarshal(new StreamSource(this.policyStoreModelFile), TPolicyStoreDesc.class).getValue();
        }
        catch (JAXBException e2) {
            ModelLoader.log.error("Cant load model from file '{}'", (Object)this.policyStoreModelFile.getAbsolutePath(), (Object)e2);
            return null;
        }
        return policyStoreDesc;
    }
    
    private boolean checkTemlatedPolicyStoreAndConvertToPolicyStore() {
        return checkPolicyStoreDescWithXSD(this.templatedPolicyStoreXSDFile, this.templatedPolicyStoreModelFile) && checkPolicyStoreDescWithXSLT(this.templatedPolicyStoreVerifyXSLTFile, this.templatedPolicyStoreModelFile, "xslt_check_result_templatedpolicystore.txt") && this.convertTemplatedPolicyStoreToPolicyStore();
    }
    
    private boolean convertTemplatedPolicyStoreToPolicyStore() {
        if (this.templatedPolicyStoreToPolicyStoreConvertXSLTFile == null || this.templatedPolicyStoreToPolicyStoreConvertXSLTFile.length == 0) {
            ModelLoader.log.error("Xslt transformer(s) is not specified");
            return false;
        }
        final File convertedModelsFolder = new File("ConvertedModels");
        if (!convertedModelsFolder.exists()) {
            if (!convertedModelsFolder.mkdirs()) {
                ModelLoader.log.error("Cant create converted models folder '{}'", (Object)convertedModelsFolder.getAbsolutePath());
                return false;
            }
            ModelLoader.log.info("Converted models folder '{}' successfully created", (Object)convertedModelsFolder.getAbsolutePath());
        }
        else {
            ModelLoader.log.info("Converted models folder '{}' exist already", (Object)convertedModelsFolder.getAbsolutePath());
        }
        File sourceFile = this.templatedPolicyStoreModelFile;
        File resultFile = null;
        final String baseFileName = FilenameUtils.getBaseName(sourceFile.getName());
        final String baseFileExt = FilenameUtils.getExtension(sourceFile.getName());
        for (int i = 0; i < this.templatedPolicyStoreToPolicyStoreConvertXSLTFile.length; ++i) {
            final String convertedPolicyStoreFileName = baseFileName + "-" + i + "-" + System.currentTimeMillis() + "." + baseFileExt;
            resultFile = new File(convertedModelsFolder, convertedPolicyStoreFileName);
            if (!doConvertation(this.templatedPolicyStoreToPolicyStoreConvertXSLTFile[i], sourceFile, resultFile)) {
                return false;
            }
            sourceFile = resultFile;
        }
        this.policyStoreModelFile = resultFile;
        ModelLoader.log.info("Resulting model for import saved to file '{}'", (Object)this.policyStoreModelFile.getAbsolutePath());
        return true;
    }
    
    private static boolean doConvertation(final File xsltFile, final File sourceFile, final File resultFile) {
        try {
            final TransformerFactory factory = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", null);
            final Source xsltSource = new StreamSource(xsltFile);
            final Transformer transformer = factory.newTransformer(xsltSource);
            final Source xmlSource = new StreamSource(sourceFile);
            transformer.transform(xmlSource, new StreamResult(resultFile));
            ModelLoader.log.info("Templated policy store '{}' successfully transformed to policy store file '{}' with xslt file '{}'", new Object[] { sourceFile.getAbsolutePath(), resultFile.getAbsolutePath(), xsltFile.getAbsolutePath() });
            return true;
        }
        catch (Exception e) {
            ModelLoader.log.error("Xslt error of transformation model '{}' to model '{}' with xslt '{}'", new Object[] { sourceFile.getAbsolutePath(), resultFile.getAbsolutePath(), xsltFile.getAbsolutePath(), e });
            return false;
        }
    }
    
    private boolean checkPolicyStoreDesc() {
        return checkPolicyStoreDescWithXSD(this.policyStoreXSDFile, this.policyStoreModelFile) && checkPolicyStoreDescWithXSLT(this.policyStoreVerifyXSLTFile, this.policyStoreModelFile, "xslt_check_result_policystore.txt");
    }
    
    private static boolean checkPolicyStoreDescWithXSD(final File policyStoreXSDFile, final File policyStoreModelFile) {
        try {
            final Source streamSource = new StreamSource(policyStoreXSDFile);
            final Source xmlSource = new StreamSource(policyStoreModelFile);
            final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            final Schema schema = schemaFactory.newSchema(streamSource);
            final Validator validator = schema.newValidator();
            validator.validate(xmlSource);
            ModelLoader.log.info("Policy model '{}' passed xsd-checking with '{}'", (Object)policyStoreModelFile.getName(), (Object)policyStoreXSDFile.getName());
            return true;
        }
        catch (Exception e) {
            ModelLoader.log.error("Policy model '{}' does NOT pass xsd-checking with '{}'", new Object[] { policyStoreModelFile.getName(), policyStoreXSDFile.getName(), e });
            return false;
        }
    }
    
    private static boolean checkPolicyStoreDescWithXSLT(final File policyStoreVerifyXSLTFile, final File policyStoreModelFile, final String resultFileName) {
        try {
            final TransformerFactory factory = TransformerFactory.newInstance();
            final Source xsltSource = new StreamSource(policyStoreVerifyXSLTFile);
            final Transformer transformer = factory.newTransformer(xsltSource);
            final Source xmlSource = new StreamSource(policyStoreModelFile);
            final File checkResultFile = new File(resultFileName);
            transformer.transform(xmlSource, new StreamResult(checkResultFile));
            if (checkResultFile.length() == 0L) {
                ModelLoader.log.info("Policy model '{}' passed xslt-checking with '{}'", (Object)policyStoreModelFile.getName(), (Object)policyStoreVerifyXSLTFile.getName());
                return true;
            }
            ModelLoader.log.error("Policy model '{}' does NOT pass xslt-checking with '{}':\n{}", new Object[] { policyStoreModelFile.getName(), policyStoreVerifyXSLTFile.getName(), FileUtils.readFileToString(checkResultFile) });
        }
        catch (Exception e) {
            ModelLoader.log.error("XSLT check error. Xslt file: '{}', policy store file: {}", new Object[] { policyStoreVerifyXSLTFile.getAbsolutePath(), policyStoreModelFile.getAbsolutePath(), e });
        }
        return false;
    }
    
    private static String loadXmlFromFileToString(final File file) {
        try {
            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setXIncludeAware(true);
            dbf.setFeature("http://apache.org/xml/features/xinclude", true);
            final DocumentBuilder db = dbf.newDocumentBuilder();
            final InputSource is = new InputSource(new FileInputStream(file));
            final Document document = db.parse(is);
            final OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            final Writer out = new StringWriter();
            final XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        }
        catch (Exception e) {
            ModelLoader.log.error("Erro: {}", (Throwable)e);
            throw new RuntimeException(e);
        }
    }
    
    public void setPolicyStoreModelFile(final File policyStoreModelFile) {
        this.policyStoreModelFile = policyStoreModelFile;
    }
    
    public void setPolicyStoreVerifyXSLTFile(final File policyStoreVerifyXSLTFile) {
        this.policyStoreVerifyXSLTFile = policyStoreVerifyXSLTFile;
    }
    
    public void setPolicyStoreXSDFile(final File policyStoreXSDFile) {
        this.policyStoreXSDFile = policyStoreXSDFile;
    }
    
    public void setTemplatedPolicyStoreModelFile(final File templatedPolicyStoreModelFile) {
        this.templatedPolicyStoreModelFile = templatedPolicyStoreModelFile;
    }
    
    public void setTemplatedPolicyStoreVerifyXSLTFile(final File templatedPolicyStoreVerifyXSLTFile) {
        this.templatedPolicyStoreVerifyXSLTFile = templatedPolicyStoreVerifyXSLTFile;
    }
    
    public void setTemplatedPolicyStoreToPolicyStoreConvertXSLTFile(final File[] templatedPolicyStoreToPolicyStoreConvertXSLTFile) {
        this.templatedPolicyStoreToPolicyStoreConvertXSLTFile = templatedPolicyStoreToPolicyStoreConvertXSLTFile;
    }
    
    public void setTemplatedPolicyStoreXSDFile(final File templatedPolicyStoreXSDFile) {
        this.templatedPolicyStoreXSDFile = templatedPolicyStoreXSDFile;
    }
    
    static {
        ModelLoader.log = LoggerFactory.getLogger((Class)ModelLoader.class);
    }
}
