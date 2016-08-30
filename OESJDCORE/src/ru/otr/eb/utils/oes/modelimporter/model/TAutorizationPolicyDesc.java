package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tAutorizationPolicyDesc", propOrder={"roles", "users", "groups", "resources", "condition", "obligations"})
public class TAutorizationPolicyDesc
{
  protected TApplicationRoleNameDescList roles;
  protected TUserPrincipalDescList users;
  protected TGroupPrincipalDescList groups;
  protected TResourceActionsDescList resources;
  protected TCondition condition;
  protected TObligations obligations;
  @XmlAttribute(name="name", required=true)
  protected String name;
  @XmlAttribute(name="displayedName", required=true)
  protected String displayedName;
  
  public TAutorizationPolicyDesc() {}
  
  public TApplicationRoleNameDescList getRoles()
  {
    return roles;
  }
  







  public void setRoles(TApplicationRoleNameDescList value)
  {
    roles = value;
  }
  







  public TUserPrincipalDescList getUsers()
  {
    return users;
  }
  







  public void setUsers(TUserPrincipalDescList value)
  {
    users = value;
  }
  







  public TGroupPrincipalDescList getGroups()
  {
    return groups;
  }
  







  public void setGroups(TGroupPrincipalDescList value)
  {
    groups = value;
  }
  







  public TResourceActionsDescList getResources()
  {
    return resources;
  }
  







  public void setResources(TResourceActionsDescList value)
  {
    resources = value;
  }
  







  public TCondition getCondition()
  {
    return condition;
  }
  







  public void setCondition(TCondition value)
  {
    condition = value;
  }
  







  public TObligations getObligations()
  {
    return obligations;
  }
  







  public void setObligations(TObligations value)
  {
    obligations = value;
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
