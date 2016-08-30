// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCondition", namespace = "http://www.otr.ru/eb/policystore/condition", propOrder = { "f" })
@XmlSeeAlso({ TObligationConditionAttribute.class })
public class TCondition
{
    @XmlElement(name = "F")
    protected TFunction f;
    
    public TFunction getF() {
        return this.f;
    }
    
    public void setF(final TFunction value) {
        this.f = value;
    }
}
