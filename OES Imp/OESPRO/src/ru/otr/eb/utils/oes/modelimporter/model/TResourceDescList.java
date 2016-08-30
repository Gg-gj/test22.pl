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
@XmlType(name = "tResourceDescList", namespace = "http://www.otr.ru/eb/policystore/resourcetype", propOrder = { "resource" })
public class TResourceDescList
{
    protected List<TResourceDesc> resource;
    
    public List<TResourceDesc> getResource() {
        if (this.resource == null) {
            this.resource = new ArrayList<TResourceDesc>();
        }
        return this.resource;
    }
}
