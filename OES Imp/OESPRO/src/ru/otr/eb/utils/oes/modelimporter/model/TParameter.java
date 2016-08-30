// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tParameter", namespace = "http://www.otr.ru/eb/policystore/condition")
public class TParameter
{
    @XmlAttribute(name = "tp")
    protected TValueType tp;
    @XmlAttribute(name = "nm")
    protected String nm;
    @XmlAttribute(name = "val")
    @XmlSchemaType(name = "anySimpleType")
    protected String val;
    
    public TValueType getTp() {
        return this.tp;
    }
    
    public void setTp(final TValueType value) {
        this.tp = value;
    }
    
    public String getNm() {
        return this.nm;
    }
    
    public void setNm(final String value) {
        this.nm = value;
    }
    
    public String getVal() {
        return this.val;
    }
    
    public void setVal(final String value) {
        this.val = value;
    }
}
