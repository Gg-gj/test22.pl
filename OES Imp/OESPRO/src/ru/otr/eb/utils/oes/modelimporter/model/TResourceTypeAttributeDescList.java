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
@XmlType(name = "tResourceTypeAttributeDescList", namespace = "http://www.otr.ru/eb/policystore/resourcetype", propOrder = { "attribute" })
public class TResourceTypeAttributeDescList
{
    protected List<TResourceTypeAttributeDesc> attribute;
    
    public List<TResourceTypeAttributeDesc> getAttribute() {
        if (this.attribute == null) {
            this.attribute = new ArrayList<TResourceTypeAttributeDesc>();
        }
        return this.attribute;
    }
}
