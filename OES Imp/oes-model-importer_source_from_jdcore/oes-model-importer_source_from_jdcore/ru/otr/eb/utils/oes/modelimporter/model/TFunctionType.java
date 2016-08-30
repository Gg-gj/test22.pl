package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;























































@XmlType(name="tFunctionType", namespace="http://www.otr.ru/eb/policystore/condition")
@XmlEnum
public enum TFunctionType
{
  AND, 
  OR, 
  NOT, 
  BOOLEAN_EQUAL, 
  STRING_CONTAINS, 
  STRING_ENDS_WITH, 
  STRING_STARTS_WITH, 
  STRING_EQUAL, 
  STRING_EQUAL_IGNORE_CASE, 
  STRING_GREATER_THAN, 
  STRING_GREATER_THAN_OR_EQUAL, 
  STRING_LESS_THAN, 
  STRING_LESS_THAN_OR_EQUAL, 
  STRING_IS_IN, 
  DOUBLE_EQUAL, 
  DOUBLE_GREATER_THAN, 
  DOUBLE_GREATER_THAN_OR_EQUAL, 
  DOUBLE_LESS_THAN, 
  DOUBLE_LESS_THAN_OR_EQUAL, 
  DOUBLE_IS_IN, 
  INTEGER_EQUAL, 
  INTEGER_GREATER_THAN, 
  INTEGER_GREATER_THAN_OR_EQUAL, 
  INTEGER_LESS_THAN, 
  INTEGER_LESS_THAN_OR_EQUAL, 
  INTEGER_IS_IN, 
  DATE_EQUAL, 
  DATE_GREATER_THAN, 
  DATE_GREATER_THAN_OR_EQUAL, 
  DATE_LESS_THAN, 
  DATE_LESS_THAN_OR_EQUAL, 
  DATE_IS_IN;
  
  private TFunctionType() {}
  public String value() { return name(); }
  
  public static TFunctionType fromValue(String v)
  {
    return valueOf(v);
  }
}
