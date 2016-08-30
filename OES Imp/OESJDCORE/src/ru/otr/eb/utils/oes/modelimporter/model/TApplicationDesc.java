package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;













































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tApplicationDesc", propOrder={"attributeList", "roleList", "resourceTypeList", "policyList"})
public class TApplicationDesc
{
  protected TAttributeDescList attributeList;
  protected TApplicationRoleDescList roleList;
  protected TResourceTypeDescList resourceTypeList;
  protected TAutorizationPolicyDescList policyList;
  @XmlAttribute(name="name", required=true)
  protected String name;
  @XmlAttribute(name="displayedName", required=true)
  protected String displayedName;
  
  public TApplicationDesc() {}
  
  public TAttributeDescList getAttributeList()
  {
    return attributeList;
  }
  







  public void setAttributeList(TAttributeDescList value)
  {
    attributeList = value;
  }
  







  public TApplicationRoleDescList getRoleList()
  {
    return roleList;
  }
  







  public void setRoleList(TApplicationRoleDescList value)
  {
    roleList = value;
  }
  







  public TResourceTypeDescList getResourceTypeList()
  {
    return resourceTypeList;
  }
  







  public void setResourceTypeList(TResourceTypeDescList value)
  {
    resourceTypeList = value;
  }
  







  public TAutorizationPolicyDescList getPolicyList()
  {
    return policyList;
  }
  







  public void setPolicyList(TAutorizationPolicyDescList value)
  {
    policyList = value;
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
