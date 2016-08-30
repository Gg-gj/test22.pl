package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;






















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tObligations", namespace="http://www.otr.ru/eb/policystore/obligations", propOrder={"obligation"})
public class TObligations
{
  protected List<TObligation> obligation;
  
  public TObligations() {}
  
  public List<TObligation> getObligation()
  {
    if (obligation == null) {
      obligation = new ArrayList();
    }
    return obligation;
  }
}
