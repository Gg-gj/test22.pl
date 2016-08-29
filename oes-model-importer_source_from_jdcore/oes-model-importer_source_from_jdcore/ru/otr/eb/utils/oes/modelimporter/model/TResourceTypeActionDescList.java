package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;






















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tResourceTypeActionDescList", namespace="http://www.otr.ru/eb/policystore/resourcetype", propOrder={"action"})
public class TResourceTypeActionDescList
{
  protected List<TActionDesc> action;
  
  public TResourceTypeActionDescList() {}
  
  public List<TActionDesc> getAction()
  {
    if (action == null) {
      action = new ArrayList();
    }
    return action;
  }
}
