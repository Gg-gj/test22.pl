package ru.otr.eb.utils.oes.modelimporter.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;








































































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tFunction", namespace="http://www.otr.ru/eb/policystore/condition", propOrder={"parOrListOrF"})
public class TFunction
{
  @XmlElements({@javax.xml.bind.annotation.XmlElement(name="Par", type=TParameter.class), @javax.xml.bind.annotation.XmlElement(name="F", type=TFunction.class), @javax.xml.bind.annotation.XmlElement(name="List", type=TList.class)})
  protected List<Object> parOrListOrF;
  @XmlAttribute(name="nm")
  protected TFunctionType nm;
  @XmlAttribute(name="exp", required=true)
  @XmlSchemaType(name="anySimpleType")
  protected String exp;
  @XmlAttribute(name="params", required=true)
  @XmlSchemaType(name="anySimpleType")
  protected String params;
  
  public TFunction() {}
  
  public List<Object> getParOrListOrF()
  {
    if (parOrListOrF == null) {
      parOrListOrF = new ArrayList();
    }
    return parOrListOrF;
  }
  







  public TFunctionType getNm()
  {
    return nm;
  }
  







  public void setNm(TFunctionType value)
  {
    nm = value;
  }
  







  public String getExp()
  {
    if (exp == null) {
      return "bool";
    }
    return exp;
  }
  








  public void setExp(String value)
  {
    exp = value;
  }
  







  public String getParams()
  {
    if (params == null) {
      return "*";
    }
    return params;
  }
  








  public void setParams(String value)
  {
    params = value;
  }
}
