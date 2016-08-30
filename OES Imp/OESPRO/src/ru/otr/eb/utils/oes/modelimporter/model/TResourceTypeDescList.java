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
@XmlType(name = "tResourceTypeDescList", namespace = "http://www.otr.ru/eb/policystore/resourcetype", propOrder = { "resourceType" })
public class TResourceTypeDescList
{
    protected List<TResourceTypeDesc> resourceType;
    
    public List<TResourceTypeDesc> getResourceType() {
        if (this.resourceType == null) {
            this.resourceType = new ArrayList<TResourceTypeDesc>();
        }
        return this.resourceType;
    }
}
