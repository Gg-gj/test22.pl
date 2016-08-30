package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tList", namespace="http://www.otr.ru/eb/policystore/condition", propOrder={"par"})
public class TList
{
  @XmlElement(name="Par")
  protected List<TParameter> par;
  
  public TList() {}
  
  public List<TParameter> getPar()
  {
    if (par == null) {
      par = new ArrayList();
    }
    return par;
  }
}
