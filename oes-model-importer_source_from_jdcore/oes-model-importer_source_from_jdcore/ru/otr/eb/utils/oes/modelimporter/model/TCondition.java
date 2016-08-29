package ru.otr.eb.utils.oes.modelimporter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;












































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tCondition", namespace="http://www.otr.ru/eb/policystore/condition", propOrder={"f"})
@XmlSeeAlso({TObligationConditionAttribute.class})
public class TCondition
{
  @XmlElement(name="F")
  protected TFunction f;
  
  public TCondition() {}
  
  public TFunction getF()
  {
    return f;
  }
  







  public void setF(TFunction value)
  {
    f = value;
  }
}
