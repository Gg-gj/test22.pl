package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;







































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tActionDesc", namespace="http://www.otr.ru/eb/policystore/resourcetype")
public class TActionDesc
{
  @XmlAttribute(name="name", required=true)
  protected String name;
  @XmlAttribute(name="displayedName")
  protected String displayedName;
  
  public TActionDesc() {}
  
  public String getName()
  {
    return name;
  }
  







  public void setName(String value)
  {
    name = value;
  }
  







  public String getDisplayedName()
  {
    return displayedName;
  }
  







  public void setDisplayedName(String value)
  {
    displayedName = value;
  }
}
