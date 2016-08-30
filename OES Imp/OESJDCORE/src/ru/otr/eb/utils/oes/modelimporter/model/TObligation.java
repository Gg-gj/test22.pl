package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tObligation", namespace="http://www.otr.ru/eb/policystore/obligations", propOrder={"conditionatribute", "attribute"})
public class TObligation
{
  protected List<TObligationConditionAttribute> conditionatribute;
  protected List<TObligationAttribute> attribute;
  @XmlAttribute(name="name", required=true)
  protected String name;
  @XmlAttribute(name="displayedName", required=true)
  protected String displayedName;
  
  public TObligation() {}
  
  public List<TObligationConditionAttribute> getConditionatribute()
  {
    if (conditionatribute == null) {
      conditionatribute = new ArrayList();
    }
    return conditionatribute;
  }
  





















  public List<TObligationAttribute> getAttribute()
  {
    if (attribute == null) {
      attribute = new ArrayList();
    }
    return attribute;
  }
  







  public String getName()
  {
    return name;
  }
  







  public void setName(String value)
  {
    name = value;
  }
  







  public String getDisplayedName()
  {
    return displayedName;
  }
  







  public void setDisplayedName(String value)
  {
    displayedName = value;
  }
}
