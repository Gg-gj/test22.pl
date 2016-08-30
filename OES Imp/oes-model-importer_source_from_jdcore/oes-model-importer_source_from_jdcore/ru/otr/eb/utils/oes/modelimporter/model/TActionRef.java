package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tActionRef", namespace="http://www.otr.ru/eb/policystore/resourcerefs")
public class TActionRef
{
  @XmlAttribute(name="name", required=true)
  protected String name;
  
  public TActionRef() {}
  
  public String getName()
  {
    return name;
  }
  







  public void setName(String value)
  {
    name = value;
  }
}
