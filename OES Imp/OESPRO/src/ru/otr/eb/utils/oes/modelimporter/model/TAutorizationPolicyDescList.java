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
@XmlType(name = "tAutorizationPolicyDescList", propOrder = { "policy" })
public class TAutorizationPolicyDescList
{
    protected List<TAutorizationPolicyDesc> policy;
    
    public List<TAutorizationPolicyDesc> getPolicy() {
        if (this.policy == null) {
            this.policy = new ArrayList<TAutorizationPolicyDesc>();
        }
        return this.policy;
    }
}
