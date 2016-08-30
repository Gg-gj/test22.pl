package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;




















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tGroupPrincipalDescList", namespace="http://www.otr.ru/eb/policystore/principal", propOrder={"group"})
public class TGroupPrincipalDescList
{
  protected List<TGroupPrincipalDesc> group;
  
  public TGroupPrincipalDescList() {}
  
  public List<TGroupPrincipalDesc> getGroup()
  {
    if (group == null) {
      group = new ArrayList();
    }
    return group;
  }
}
