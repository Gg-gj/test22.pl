// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tFunction", namespace = "http://www.otr.ru/eb/policystore/condition", propOrder = { "parOrListOrF" })
public class TFunction
{
    @XmlElements({ @XmlElement(name = "Par", type = TParameter.class), @XmlElement(name = "F", type = TFunction.class), @XmlElement(name = "List", type = TList.class) })
    protected List<Object> parOrListOrF;
    @XmlAttribute(name = "nm")
    protected TFunctionType nm;
    @XmlAttribute(name = "exp", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String exp;
    @XmlAttribute(name = "params", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String params;
    
    public List<Object> getParOrListOrF() {
        if (this.parOrListOrF == null) {
            this.parOrListOrF = new ArrayList<Object>();
        }
        return this.parOrListOrF;
    }
    
    public TFunctionType getNm() {
        return this.nm;
    }
    
    public void setNm(final TFunctionType value) {
        this.nm = value;
    }
    
    public String getExp() {
        if (this.exp == null) {
            return "bool";
        }
        return this.exp;
    }
    
    public void setExp(final String value) {
        this.exp = value;
    }
    
    public String getParams() {
        if (this.params == null) {
            return "*";
        }
        return this.params;
    }
    
    public void setParams(final String value) {
        this.params = value;
    }
}
