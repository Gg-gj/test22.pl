package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;














































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tResourceTypeDesc", namespace="http://www.otr.ru/eb/policystore/resourcetype", propOrder={"actions", "attributes", "resources"})
public class TResourceTypeDesc
{
  @XmlElement(required=true)
  protected TResourceTypeActionDescList actions;
  protected TResourceTypeAttributeDescList attributes;
  protected TResourceDescList resources;
  @XmlAttribute(name="name", required=true)
  protected String name;
  @XmlAttribute(name="displayedName", required=true)
  protected String displayedName;
  
  public TResourceTypeDesc() {}
  
  public TResourceTypeActionDescList getActions()
  {
    return actions;
  }
  







  public void setActions(TResourceTypeActionDescList value)
  {
    actions = value;
  }
  







  public TResourceTypeAttributeDescList getAttributes()
  {
    return attributes;
  }
  







  public void setAttributes(TResourceTypeAttributeDescList value)
  {
    attributes = value;
  }
  







  public TResourceDescList getResources()
  {
    return resources;
  }
  







  public void setResources(TResourceDescList value)
  {
    resources = value;
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
