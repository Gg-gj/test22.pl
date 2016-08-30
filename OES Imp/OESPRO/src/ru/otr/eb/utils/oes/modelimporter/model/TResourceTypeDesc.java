// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tResourceTypeDesc", namespace = "http://www.otr.ru/eb/policystore/resourcetype", propOrder = { "actions", "attributes", "resources" })
public class TResourceTypeDesc
{
    @XmlElement(required = true)
    protected TResourceTypeActionDescList actions;
    protected TResourceTypeAttributeDescList attributes;
    protected TResourceDescList resources;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "displayedName", required = true)
    protected String displayedName;
    
    public TResourceTypeActionDescList getActions() {
        return this.actions;
    }
    
    public void setActions(final TResourceTypeActionDescList value) {
        this.actions = value;
    }
    
    public TResourceTypeAttributeDescList getAttributes() {
        return this.attributes;
    }
    
    public void setAttributes(final TResourceTypeAttributeDescList value) {
        this.attributes = value;
    }
    
    public TResourceDescList getResources() {
        return this.resources;
    }
    
    public void setResources(final TResourceDescList value) {
        this.resources = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String value) {
        this.name = value;
    }
    
    public String getDisplayedName() {
        return this.displayedName;
    }
    
    public void setDisplayedName(final String value) {
        this.displayedName = value;
    }
}
