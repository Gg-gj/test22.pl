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
@XmlType(name = "tObligations", namespace = "http://www.otr.ru/eb/policystore/obligations", propOrder = { "obligation" })
public class TObligations
{
    protected List<TObligation> obligation;
    
    public List<TObligation> getObligation() {
        if (this.obligation == null) {
            this.obligation = new ArrayList<TObligation>();
        }
        return this.obligation;
    }
}
