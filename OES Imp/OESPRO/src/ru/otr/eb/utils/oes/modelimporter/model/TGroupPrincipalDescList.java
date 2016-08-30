// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tGroupPrincipalDescList", namespace = "http://www.otr.ru/eb/policystore/principal", propOrder = { "group" })
public class TGroupPrincipalDescList
{
    protected List<TGroupPrincipalDesc> group;
    
    public List<TGroupPrincipalDesc> getGroup() {
        if (this.group == null) {
            this.group = new ArrayList<TGroupPrincipalDesc>();
        }
        return this.group;
    }
}
