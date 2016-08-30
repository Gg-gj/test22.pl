package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tAttributeDesc", namespace="http://www.otr.ru/eb/policystore/attribute")
@XmlSeeAlso({TObligationAttribute.class})
public class TAttributeDesc
{
  @XmlAttribute(name="type", required=true)
  protected TAttributeTypeEnum type;
  @XmlAttribute(name="name", required=true)
  protected String name;
  @XmlAttribute(name="displayedName", required=true)
  protected String displayedName;
  
  public TAttributeDesc() {}
  
  public TAttributeTypeEnum getType()
  {
    return type;
  }
  







  public void setType(TAttributeTypeEnum value)
  {
    type = value;
  }
  







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
