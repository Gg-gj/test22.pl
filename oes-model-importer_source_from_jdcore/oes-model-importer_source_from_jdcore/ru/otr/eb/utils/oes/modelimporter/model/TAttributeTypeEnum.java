package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;




























@XmlType(name="tAttributeTypeEnum", namespace="http://www.otr.ru/eb/policystore/attribute")
@XmlEnum
public enum TAttributeTypeEnum
{
  STRING("String"), 
  
  BOOLEAN("Boolean"), 
  
  INTEGER("Integer"), 
  
  DATE("Date"), 
  
  DOUBLE("Double");
  
  private final String value;
  
  private TAttributeTypeEnum(String v) {
    value = v;
  }
  
  public String value() {
    return value;
  }
  
  public static TAttributeTypeEnum fromValue(String v) {
    for (TAttributeTypeEnum c : ) {
      if (value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
