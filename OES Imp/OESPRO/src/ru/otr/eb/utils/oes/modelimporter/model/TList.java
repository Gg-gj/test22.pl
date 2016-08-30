// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tList", namespace = "http://www.otr.ru/eb/policystore/condition", propOrder = { "par" })
public class TList
{
    @XmlElement(name = "Par")
    protected List<TParameter> par;
    
    public List<TParameter> getPar() {
        if (this.par == null) {
            this.par = new ArrayList<TParameter>();
        }
        return this.par;
    }
}
