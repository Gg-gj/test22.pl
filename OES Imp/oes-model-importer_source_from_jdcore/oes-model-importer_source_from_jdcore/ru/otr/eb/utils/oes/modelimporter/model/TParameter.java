package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tParameter", namespace="http://www.otr.ru/eb/policystore/condition")
public class TParameter
{
  @XmlAttribute(name="tp")
  protected TValueType tp;
  @XmlAttribute(name="nm")
  protected String nm;
  @XmlAttribute(name="val")
  @XmlSchemaType(name="anySimpleType")
  protected String val;
  
  public TParameter() {}
  
  public TValueType getTp()
  {
    return tp;
  }
  







  public void setTp(TValueType value)
  {
    tp = value;
  }
  







  public String getNm()
  {
    return nm;
  }
  







  public void setNm(String value)
  {
    nm = value;
  }
  







  public String getVal()
  {
    return val;
  }
  







  public void setVal(String value)
  {
    val = value;
  }
}
