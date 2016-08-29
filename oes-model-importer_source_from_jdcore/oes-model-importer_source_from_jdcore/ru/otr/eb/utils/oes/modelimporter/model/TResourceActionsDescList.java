package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;




















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tResourceActionsDescList", namespace="http://www.otr.ru/eb/policystore/resourcerefs", propOrder={"resource"})
public class TResourceActionsDescList
{
  protected List<TResourceActionsRef> resource;
  
  public TResourceActionsDescList() {}
  
  public List<TResourceActionsRef> getResource()
  {
    if (resource == null) {
      resource = new ArrayList();
    }
    return resource;
  }
}
