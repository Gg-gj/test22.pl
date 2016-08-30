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
@XmlType(name = "tGroupPrincipalDesc", namespace = "http://www.otr.ru/eb/policystore/principal")
public class TGroupPrincipalDesc
{
    @XmlAttribute(name = "cn", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String cn;
    
    public String getCn() {
        return this.cn;
    }
    
    public void setCn(final String value) {
        this.cn = value;
    }
}
