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
@XmlType(name = "tActionRefList", namespace = "http://www.otr.ru/eb/policystore/resourcerefs", propOrder = { "action" })
public class TActionRefList
{
    @XmlElement(required = true)
    protected List<TActionRef> action;
    
    public List<TActionRef> getAction() {
        if (this.action == null) {
            this.action = new ArrayList<TActionRef>();
        }
        return this.action;
    }
}
