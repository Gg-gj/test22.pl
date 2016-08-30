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
@XmlType(name = "tApplicationRoleDescList", propOrder = { "role" })
public class TApplicationRoleDescList
{
    protected List<TApplicationRoleDesc> role;
    
    public List<TApplicationRoleDesc> getRole() {
        if (this.role == null) {
            this.role = new ArrayList<TApplicationRoleDesc>();
        }
        return this.role;
    }
}
