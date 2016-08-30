// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tAutorizationPolicyDesc", propOrder = { "roles", "users", "groups", "resources", "condition", "obligations" })
public class TAutorizationPolicyDesc
{
    protected TApplicationRoleNameDescList roles;
    protected TUserPrincipalDescList users;
    protected TGroupPrincipalDescList groups;
    protected TResourceActionsDescList resources;
    protected TCondition condition;
    protected TObligations obligations;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "displayedName", required = true)
    protected String displayedName;
    
    public TApplicationRoleNameDescList getRoles() {
        return this.roles;
    }
    
    public void setRoles(final TApplicationRoleNameDescList value) {
        this.roles = value;
    }
    
    public TUserPrincipalDescList getUsers() {
        return this.users;
    }
    
    public void setUsers(final TUserPrincipalDescList value) {
        this.users = value;
    }
    
    public TGroupPrincipalDescList getGroups() {
        return this.groups;
    }
    
    public void setGroups(final TGroupPrincipalDescList value) {
        this.groups = value;
    }
    
    public TResourceActionsDescList getResources() {
        return this.resources;
    }
    
    public void setResources(final TResourceActionsDescList value) {
        this.resources = value;
    }
    
    public TCondition getCondition() {
        return this.condition;
    }
    
    public void setCondition(final TCondition value) {
        this.condition = value;
    }
    
    public TObligations getObligations() {
        return this.obligations;
    }
    
    public void setObligations(final TObligations value) {
        this.obligations = value;
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
