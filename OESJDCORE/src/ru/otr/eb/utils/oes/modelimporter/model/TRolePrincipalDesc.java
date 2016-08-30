package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;




































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tRolePrincipalDesc", namespace="http://www.otr.ru/eb/policystore/principal")
public class TRolePrincipalDesc
{
  @XmlAttribute(name="name")
  protected String name;
  
  public TRolePrincipalDesc() {}
  
  public String getName()
  {
    return name;
  }
  







  public void setName(String value)
  {
    name = value;
  }
}
