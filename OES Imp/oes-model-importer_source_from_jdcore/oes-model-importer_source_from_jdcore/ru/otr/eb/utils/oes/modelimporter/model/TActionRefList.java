package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;




















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tActionRefList", namespace="http://www.otr.ru/eb/policystore/resourcerefs", propOrder={"action"})
public class TActionRefList
{
  @XmlElement(required=true)
  protected List<TActionRef> action;
  
  public TActionRefList() {}
  
  public List<TActionRef> getAction()
  {
    if (action == null) {
      action = new ArrayList();
    }
    return action;
  }
}
