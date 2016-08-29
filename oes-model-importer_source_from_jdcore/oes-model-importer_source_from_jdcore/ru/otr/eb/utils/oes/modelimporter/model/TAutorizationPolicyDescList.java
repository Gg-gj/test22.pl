package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;




















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tAutorizationPolicyDescList", propOrder={"policy"})
public class TAutorizationPolicyDescList
{
  protected List<TAutorizationPolicyDesc> policy;
  
  public TAutorizationPolicyDescList() {}
  
  public List<TAutorizationPolicyDesc> getPolicy()
  {
    if (policy == null) {
      policy = new ArrayList();
    }
    return policy;
  }
}
