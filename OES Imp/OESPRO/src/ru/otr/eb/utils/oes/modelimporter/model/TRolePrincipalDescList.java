// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tRolePrincipalDescList", namespace = "http://www.otr.ru/eb/policystore/principal", propOrder = { "role" })
public class TRolePrincipalDescList
{
    @XmlElement(required = true)
    protected List<TRolePrincipalDesc> role;
    
    public List<TRolePrincipalDesc> getRole() {
        if (this.role == null) {
            this.role = new ArrayList<TRolePrincipalDesc>();
        }
        return this.role;
    }
}
