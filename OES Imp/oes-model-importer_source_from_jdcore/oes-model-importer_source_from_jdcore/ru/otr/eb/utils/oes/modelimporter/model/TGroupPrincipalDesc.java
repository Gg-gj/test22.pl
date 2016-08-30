package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tGroupPrincipalDesc", namespace="http://www.otr.ru/eb/policystore/principal")
public class TGroupPrincipalDesc
{
  @XmlAttribute(name="cn", required=true)
  @XmlSchemaType(name="anySimpleType")
  protected String cn;
  
  public TGroupPrincipalDesc() {}
  
  public String getCn()
  {
    return cn;
  }
  







  public void setCn(String value)
  {
    cn = value;
  }
}
