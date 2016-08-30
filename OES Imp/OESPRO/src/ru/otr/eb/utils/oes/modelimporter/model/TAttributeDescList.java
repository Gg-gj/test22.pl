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
@XmlType(name = "tAttributeDescList", namespace = "http://www.otr.ru/eb/policystore/attribute", propOrder = { "attribute" })
public class TAttributeDescList
{
    protected List<TAttributeDesc> attribute;
    
    public List<TAttributeDesc> getAttribute() {
        if (this.attribute == null) {
            this.attribute = new ArrayList<TAttributeDesc>();
        }
        return this.attribute;
    }
}
