package ru.otr.eb.utils.oes.modelimporter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;






public class ImportDataInfoProvider
{
  private Map<String, String> params;
  
  public ImportDataInfoProvider() {}
  
  public String getImportDataInfo()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Params: ").append(params).append("\nUser: ").append(System.getProperty("user.name")).append("\nComputer: ");
    
    try
    {
      sb.append(InetAddress.getLocalHost().getHostName());
    } catch (UnknownHostException e) {
      sb.append("Cant get host");
    }
    
    return sb.toString();
  }
  
  public void setParams(Map<String, String> params) {
    this.params = params;
  }
}
