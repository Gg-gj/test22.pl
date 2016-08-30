// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tObligation", namespace = "http://www.otr.ru/eb/policystore/obligations", propOrder = { "conditionatribute", "attribute" })
public class TObligation
{
    protected List<TObligationConditionAttribute> conditionatribute;
    protected List<TObligationAttribute> attribute;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "displayedName", required = true)
    protected String displayedName;
    
    public List<TObligationConditionAttribute> getConditionatribute() {
        if (this.conditionatribute == null) {
            this.conditionatribute = new ArrayList<TObligationConditionAttribute>();
        }
        return this.conditionatribute;
    }
    
    public List<TObligationAttribute> getAttribute() {
        if (this.attribute == null) {
            this.attribute = new ArrayList<TObligationAttribute>();
        }
        return this.attribute;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String value) {
        this.name = value;
    }
    
    public String getDisplayedName() {
        return this.displayedName;
    }
    
    public void setDisplayedName(final String value) {
        this.displayedName = value;
    }
}
