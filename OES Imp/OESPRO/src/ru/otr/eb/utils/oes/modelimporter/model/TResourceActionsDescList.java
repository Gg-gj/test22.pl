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
@XmlType(name = "tResourceActionsDescList", namespace = "http://www.otr.ru/eb/policystore/resourcerefs", propOrder = { "resource" })
public class TResourceActionsDescList
{
    protected List<TResourceActionsRef> resource;
    
    public List<TResourceActionsRef> getResource() {
        if (this.resource == null) {
            this.resource = new ArrayList<TResourceActionsRef>();
        }
        return this.resource;
    }
}
