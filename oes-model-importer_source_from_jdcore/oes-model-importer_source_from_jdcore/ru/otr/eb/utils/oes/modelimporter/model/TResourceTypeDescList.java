package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;




















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tResourceTypeDescList", namespace="http://www.otr.ru/eb/policystore/resourcetype", propOrder={"resourceType"})
public class TResourceTypeDescList
{
  protected List<TResourceTypeDesc> resourceType;
  
  public TResourceTypeDescList() {}
  
  public List<TResourceTypeDesc> getResourceType()
  {
    if (resourceType == null) {
      resourceType = new ArrayList();
    }
    return resourceType;
  }
}
