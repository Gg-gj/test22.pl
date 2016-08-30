package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;








































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tResourceActionsRef", namespace="http://www.otr.ru/eb/policystore/resourcerefs", propOrder={"actions"})
public class TResourceActionsRef
{
  @XmlElement(required=true)
  protected TActionRefList actions;
  @XmlAttribute(name="resourceTypeName", required=true)
  protected String resourceTypeName;
  @XmlAttribute(name="resourceName")
  protected String resourceName;
  
  public TResourceActionsRef() {}
  
  public TActionRefList getActions()
  {
    return actions;
  }
  







  public void setActions(TActionRefList value)
  {
    actions = value;
  }
  







  public String getResourceTypeName()
  {
    return resourceTypeName;
  }
  







  public void setResourceTypeName(String value)
  {
    resourceTypeName = value;
  }
  







  public String getResourceName()
  {
    return resourceName;
  }
  







  public void setResourceName(String value)
  {
    resourceName = value;
  }
}
