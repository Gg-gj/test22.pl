package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;











































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tApplicationRoleDesc", propOrder={"roles", "users", "groups"})
public class TApplicationRoleDesc
{
  protected TRolePrincipalDescList roles;
  protected TUserPrincipalDescList users;
  protected TGroupPrincipalDescList groups;
  @XmlAttribute(name="name", required=true)
  protected String name;
  @XmlAttribute(name="displayedName", required=true)
  protected String displayedName;
  
  public TApplicationRoleDesc() {}
  
  public TRolePrincipalDescList getRoles()
  {
    return roles;
  }
  







  public void setRoles(TRolePrincipalDescList value)
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
