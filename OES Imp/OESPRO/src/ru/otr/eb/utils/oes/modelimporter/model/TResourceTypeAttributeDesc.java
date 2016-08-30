// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tResourceTypeAttributeDesc", namespace = "http://www.otr.ru/eb/policystore/resourcetype")
public class TResourceTypeAttributeDesc
{
    @XmlAttribute(name = "name", required = true)
    protected String name;
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String value) {
        this.name = value;
    }
}
