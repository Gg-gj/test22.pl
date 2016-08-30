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
@XmlType(name = "tApplicationRoleNameDescList", propOrder = { "rolename" })
public class TApplicationRoleNameDescList
{
    protected List<TApplicationRoleName> rolename;
    
    public List<TApplicationRoleName> getRolename() {
        if (this.rolename == null) {
            this.rolename = new ArrayList<TApplicationRoleName>();
        }
        return this.rolename;
    }
}
