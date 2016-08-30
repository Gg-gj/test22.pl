// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _PolicyStore_QNAME;
    
    public TApplicationRoleNameDescList createTApplicationRoleNameDescList() {
        return new TApplicationRoleNameDescList();
    }
    
    public TResourceActionsDescList createTResourceActionsDescList() {
        return new TResourceActionsDescList();
    }
    
    public TRolePrincipalDescList createTRolePrincipalDescList() {
        return new TRolePrincipalDescList();
    }
    
    public TResourceTypeActionDescList createTResourceTypeActionDescList() {
        return new TResourceTypeActionDescList();
    }
    
    public TResourceDescList createTResourceDescList() {
        return new TResourceDescList();
    }
    
    public TActionRefList createTActionRefList() {
        return new TActionRefList();
    }
    
    public TActionRef createTActionRef() {
        return new TActionRef();
    }
    
    public TResourceDesc createTResourceDesc() {
        return new TResourceDesc();
    }
    
    public TList createTList() {
        return new TList();
    }
    
    public TApplicationRoleDescList createTApplicationRoleDescList() {
        return new TApplicationRoleDescList();
    }
    
    public TGroupPrincipalDescList createTGroupPrincipalDescList() {
        return new TGroupPrincipalDescList();
    }
    
    public TResourceTypeAttributeDesc createTResourceTypeAttributeDesc() {
        return new TResourceTypeAttributeDesc();
    }
    
    public TResourceTypeAttributeDescList createTResourceTypeAttributeDescList() {
        return new TResourceTypeAttributeDescList();
    }
    
    public TObligationConditionAttribute createTObligationConditionAttribute() {
        return new TObligationConditionAttribute();
    }
    
    public TApplicationRoleName createTApplicationRoleName() {
        return new TApplicationRoleName();
    }
    
    public TParameter createTParameter() {
        return new TParameter();
    }
    
    public TUserPrincipalDesc createTUserPrincipalDesc() {
        return new TUserPrincipalDesc();
    }
    
    public TPolicyStoreDesc createTPolicyStoreDesc() {
        return new TPolicyStoreDesc();
    }
    
    public TActionDesc createTActionDesc() {
        return new TActionDesc();
    }
    
    public TAutorizationPolicyDesc createTAutorizationPolicyDesc() {
        return new TAutorizationPolicyDesc();
    }
    
    public TObligations createTObligations() {
        return new TObligations();
    }
    
    public TAttributeDesc createTAttributeDesc() {
        return new TAttributeDesc();
    }
    
    public TApplicationDesc createTApplicationDesc() {
        return new TApplicationDesc();
    }
    
    public TUserPrincipalDescList createTUserPrincipalDescList() {
        return new TUserPrincipalDescList();
    }
    
    public TGroupPrincipalDesc createTGroupPrincipalDesc() {
        return new TGroupPrincipalDesc();
    }
    
    public TAutorizationPolicyDescList createTAutorizationPolicyDescList() {
        return new TAutorizationPolicyDescList();
    }
    
    public TFunction createTFunction() {
        return new TFunction();
    }
    
    public TResourceTypeDescList createTResourceTypeDescList() {
        return new TResourceTypeDescList();
    }
    
    public TApplicationRoleDesc createTApplicationRoleDesc() {
        return new TApplicationRoleDesc();
    }
    
    public TObligationAttribute createTObligationAttribute() {
        return new TObligationAttribute();
    }
    
    public TAttributeDescList createTAttributeDescList() {
        return new TAttributeDescList();
    }
    
    public TResourceTypeDesc createTResourceTypeDesc() {
        return new TResourceTypeDesc();
    }
    
    public TResourceActionsRef createTResourceActionsRef() {
        return new TResourceActionsRef();
    }
    
    public TRolePrincipalDesc createTRolePrincipalDesc() {
        return new TRolePrincipalDesc();
    }
    
    public TObligation createTObligation() {
        return new TObligation();
    }
    
    public TCondition createTCondition() {
        return new TCondition();
    }
    
    @XmlElementDecl(namespace = "http://www.otr.ru/eb/policystore", name = "policyStore")
    public JAXBElement<TPolicyStoreDesc> createPolicyStore(final TPolicyStoreDesc value) {
        return new JAXBElement<TPolicyStoreDesc>(ObjectFactory._PolicyStore_QNAME, TPolicyStoreDesc.class, null, value);
    }
    
    static {
        _PolicyStore_QNAME = new QName("http://www.otr.ru/eb/policystore", "policyStore");
    }
}
