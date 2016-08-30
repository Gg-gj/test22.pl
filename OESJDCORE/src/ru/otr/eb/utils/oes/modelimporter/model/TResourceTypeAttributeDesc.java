package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;




































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tResourceTypeAttributeDesc", namespace="http://www.otr.ru/eb/policystore/resourcetype")
public class TResourceTypeAttributeDesc
{
  @XmlAttribute(name="name", required=true)
  protected String name;
  
  public TResourceTypeAttributeDesc() {}
  
  public String getName()
  {
    return name;
  }
  







  public void setName(String value)
  {
    name = value;
  }
}
