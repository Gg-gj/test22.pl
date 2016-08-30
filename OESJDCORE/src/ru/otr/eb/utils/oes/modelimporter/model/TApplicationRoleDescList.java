package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;




















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tApplicationRoleDescList", propOrder={"role"})
public class TApplicationRoleDescList
{
  protected List<TApplicationRoleDesc> role;
  
  public TApplicationRoleDescList() {}
  
  public List<TApplicationRoleDesc> getRole()
  {
    if (role == null) {
      role = new ArrayList();
    }
    return role;
  }
}
