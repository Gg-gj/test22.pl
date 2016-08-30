// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tValueType", namespace = "http://www.otr.ru/eb/policystore/condition")
@XmlEnum
public enum TValueType
{
    STR("str"), 
    DBL("dbl"), 
    INT("int"), 
    BOOL("bool"), 
    DATE("date");
    
    private final String value;
    
    private TValueType(final String v) {
        this.value = v;
    }
    
    public String value() {
        return this.value;
    }
    
    public static TValueType fromValue(final String v) {
        for (final TValueType c : values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
