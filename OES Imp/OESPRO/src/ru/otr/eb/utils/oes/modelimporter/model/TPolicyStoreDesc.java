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
@XmlType(name = "tPolicyStoreDesc", propOrder = { "application" })
public class TPolicyStoreDesc
{
    protected List<TApplicationDesc> application;
    
    public List<TApplicationDesc> getApplication() {
        if (this.application == null) {
            this.application = new ArrayList<TApplicationDesc>();
        }
        return this.application;
    }
}
