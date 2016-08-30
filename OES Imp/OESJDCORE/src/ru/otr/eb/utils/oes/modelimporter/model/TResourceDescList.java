package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;






















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tResourceDescList", namespace="http://www.otr.ru/eb/policystore/resourcetype", propOrder={"resource"})
public class TResourceDescList
{
  protected List<TResourceDesc> resource;
  
  public TResourceDescList() {}
  
  public List<TResourceDesc> getResource()
  {
    if (resource == null) {
      resource = new ArrayList();
    }
    return resource;
  }
}
