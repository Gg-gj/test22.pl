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
@XmlType(name = "tResourceTypeActionDescList", namespace = "http://www.otr.ru/eb/policystore/resourcetype", propOrder = { "action" })
public class TResourceTypeActionDescList
{
    protected List<TActionDesc> action;
    
    public List<TActionDesc> getAction() {
        if (this.action == null) {
            this.action = new ArrayList<TActionDesc>();
        }
        return this.action;
    }
}
