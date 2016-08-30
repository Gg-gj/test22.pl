package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tPolicyStoreDesc", propOrder={"application"})
public class TPolicyStoreDesc
{
  protected List<TApplicationDesc> application;
  
  public TPolicyStoreDesc() {}
  
  public List<TApplicationDesc> getApplication()
  {
    if (application == null) {
      application = new ArrayList();
    }
    return application;
  }
}
