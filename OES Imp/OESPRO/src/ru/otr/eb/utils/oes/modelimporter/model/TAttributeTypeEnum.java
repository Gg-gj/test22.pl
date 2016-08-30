// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tAttributeTypeEnum", namespace = "http://www.otr.ru/eb/policystore/attribute")
@XmlEnum
public enum TAttributeTypeEnum
{
    STRING("String"), 
    BOOLEAN("Boolean"), 
    INTEGER("Integer"), 
    DATE("Date"), 
    DOUBLE("Double");
    
    private final String value;
    
    private TAttributeTypeEnum(final String v) {
        this.value = v;
    }
    
    public String value() {
        return this.value;
    }
    
    public static TAttributeTypeEnum fromValue(final String v) {
        for (final TAttributeTypeEnum c : values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
