package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tAttributeDescList", namespace="http://www.otr.ru/eb/policystore/attribute", propOrder={"attribute"})
public class TAttributeDescList
{
  protected List<TAttributeDesc> attribute;
  
  public TAttributeDescList() {}
  
  public List<TAttributeDesc> getAttribute()
  {
    if (attribute == null) {
      attribute = new ArrayList();
    }
    return attribute;
  }
}
