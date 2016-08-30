package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;



































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tUserPrincipalDesc", namespace="http://www.otr.ru/eb/policystore/principal")
public class TUserPrincipalDesc
{
  @XmlAttribute(name="sn")
  protected String sn;
  @XmlAttribute(name="cn")
  @XmlSchemaType(name="anySimpleType")
  protected String cn;
  
  public TUserPrincipalDesc() {}
  
  public String getSn()
  {
    return sn;
  }
  







  public void setSn(String value)
  {
    sn = value;
  }
  







  public String getCn()
  {
    return cn;
  }
  







  public void setCn(String value)
  {
    cn = value;
  }
}
