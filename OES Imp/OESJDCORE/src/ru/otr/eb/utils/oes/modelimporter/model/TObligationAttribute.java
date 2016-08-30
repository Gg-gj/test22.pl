package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tObligationAttribute", namespace="http://www.otr.ru/eb/policystore/obligations")
public class TObligationAttribute
  extends TAttributeDesc
{
  @XmlAttribute(name="value")
  protected String value;
  
  public TObligationAttribute() {}
  
  public String getValue()
  {
    return value;
  }
  







  public void setValue(String value)
  {
    this.value = value;
  }
}
