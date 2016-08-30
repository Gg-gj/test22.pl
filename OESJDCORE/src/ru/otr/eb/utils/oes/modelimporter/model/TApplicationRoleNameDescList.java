package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;




















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tApplicationRoleNameDescList", propOrder={"rolename"})
public class TApplicationRoleNameDescList
{
  protected List<TApplicationRoleName> rolename;
  
  public TApplicationRoleNameDescList() {}
  
  public List<TApplicationRoleName> getRolename()
  {
    if (rolename == null) {
      rolename = new ArrayList();
    }
    return rolename;
  }
}
