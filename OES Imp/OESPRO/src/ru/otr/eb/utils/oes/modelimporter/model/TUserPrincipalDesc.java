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
@XmlType(name = "tUserPrincipalDesc", namespace = "http://www.otr.ru/eb/policystore/principal")
public class TUserPrincipalDesc
{
    @XmlAttribute(name = "sn")
    protected String sn;
    @XmlAttribute(name = "cn")
    @XmlSchemaType(name = "anySimpleType")
    protected String cn;
    
    public String getSn() {
        return this.sn;
    }
    
    public void setSn(final String value) {
        this.sn = value;
    }
    
    public String getCn() {
        return this.cn;
    }
    
    public void setCn(final String value) {
        this.cn = value;
    }
}
