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
@XmlType(name = "tResourceActionsRef", namespace = "http://www.otr.ru/eb/policystore/resourcerefs", propOrder = { "actions" })
public class TResourceActionsRef
{
    @XmlElement(required = true)
    protected TActionRefList actions;
    @XmlAttribute(name = "resourceTypeName", required = true)
    protected String resourceTypeName;
    @XmlAttribute(name = "resourceName")
    protected String resourceName;
    
    public TActionRefList getActions() {
        return this.actions;
    }
    
    public void setActions(final TActionRefList value) {
        this.actions = value;
    }
    
    public String getResourceTypeName() {
        return this.resourceTypeName;
    }
    
    public void setResourceTypeName(final String value) {
        this.resourceTypeName = value;
    }
    
    public String getResourceName() {
        return this.resourceName;
    }
    
    public void setResourceName(final String value) {
        this.resourceName = value;
    }
}
