// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tApplicationDesc", propOrder = { "attributeList", "roleList", "resourceTypeList", "policyList" })
public class TApplicationDesc
{
    protected TAttributeDescList attributeList;
    protected TApplicationRoleDescList roleList;
    protected TResourceTypeDescList resourceTypeList;
    protected TAutorizationPolicyDescList policyList;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "displayedName", required = true)
    protected String displayedName;
    
    public TAttributeDescList getAttributeList() {
        return this.attributeList;
    }
    
    public void setAttributeList(final TAttributeDescList value) {
        this.attributeList = value;
    }
    
    public TApplicationRoleDescList getRoleList() {
        return this.roleList;
    }
    
    public void setRoleList(final TApplicationRoleDescList value) {
        this.roleList = value;
    }
    
    public TResourceTypeDescList getResourceTypeList() {
        return this.resourceTypeList;
    }
    
    public void setResourceTypeList(final TResourceTypeDescList value) {
        this.resourceTypeList = value;
    }
    
    public TAutorizationPolicyDescList getPolicyList() {
        return this.policyList;
    }
    
    public void setPolicyList(final TAutorizationPolicyDescList value) {
        this.policyList = value;
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
