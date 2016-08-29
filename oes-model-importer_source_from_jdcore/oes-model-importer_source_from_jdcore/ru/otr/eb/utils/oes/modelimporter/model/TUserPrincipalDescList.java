package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;




















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tUserPrincipalDescList", namespace="http://www.otr.ru/eb/policystore/principal", propOrder={"user"})
public class TUserPrincipalDescList
{
  @XmlElement(required=true)
  protected List<TUserPrincipalDesc> user;
  
  public TUserPrincipalDescList() {}
  
  public List<TUserPrincipalDesc> getUser()
  {
    if (user == null) {
      user = new ArrayList();
    }
    return user;
  }
}
