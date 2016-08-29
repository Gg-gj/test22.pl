package ru.otr.eb.utils.oes.modelimporter.actions;


public class Action
{
  private long id;
  
  private int version;
  
  private String applicationCode;
  private String resourceTypeCode;
  private String actionCode;
  private String actionName;
  
  public Action() {}
  
  public long getId()
  {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public int getVersion() {
    return version;
  }
  
  public void setVersion(int version) {
    this.version = version;
  }
  
  public String getApplicationCode() {
    return applicationCode;
  }
  
  public void setApplicationCode(String applicationCode) {
    this.applicationCode = applicationCode;
  }
  
  public String getResourceTypeCode() {
    return resourceTypeCode;
  }
  
  public void setResourceTypeCode(String resourceTypeCode) {
    this.resourceTypeCode = resourceTypeCode;
  }
  
  public String getActionCode() {
    return actionCode;
  }
  
  public void setActionCode(String actionCode) {
    this.actionCode = actionCode;
  }
  
  public String getActionName() {
    return actionName;
  }
  
  public void setActionName(String actionName) {
    this.actionName = actionName;
  }
  
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if ((o == null) || (getClass() != o.getClass())) { return false;
    }
    Action action = (Action)o;
    
    if (actionCode != null ? actionCode.equals(actionCode) : actionCode == null) if (applicationCode != null ? !applicationCode.equals(applicationCode) : applicationCode != null) {} return resourceTypeCode != null ? resourceTypeCode.equals(resourceTypeCode) : resourceTypeCode == null;
  }
  


  public int hashCode()
  {
    int result = applicationCode != null ? applicationCode.hashCode() : 0;
    result = 31 * result + (resourceTypeCode != null ? resourceTypeCode.hashCode() : 0);
    result = 31 * result + (actionCode != null ? actionCode.hashCode() : 0);
    return result;
  }
}
