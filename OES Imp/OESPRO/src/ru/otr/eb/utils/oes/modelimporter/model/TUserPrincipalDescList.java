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
@XmlType(name = "tUserPrincipalDescList", namespace = "http://www.otr.ru/eb/policystore/principal", propOrder = { "user" })
public class TUserPrincipalDescList
{
    @XmlElement(required = true)
    protected List<TUserPrincipalDesc> user;
    
    public List<TUserPrincipalDesc> getUser() {
        if (this.user == null) {
            this.user = new ArrayList<TUserPrincipalDesc>();
        }
        return this.user;
    }
}
