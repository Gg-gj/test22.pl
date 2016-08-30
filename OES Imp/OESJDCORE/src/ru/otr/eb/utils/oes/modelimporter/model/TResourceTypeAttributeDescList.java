package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;






















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tResourceTypeAttributeDescList", namespace="http://www.otr.ru/eb/policystore/resourcetype", propOrder={"attribute"})
public class TResourceTypeAttributeDescList
{
  protected List<TResourceTypeAttributeDesc> attribute;
  
  public TResourceTypeAttributeDescList() {}
  
  public List<TResourceTypeAttributeDesc> getAttribute()
  {
    if (attribute == null) {
      attribute = new ArrayList();
    }
    return attribute;
  }
}
