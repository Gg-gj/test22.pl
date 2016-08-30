// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter;

import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.Map;

public class ImportDataInfoProvider
{
    private Map<String, String> params;
    
    public String getImportDataInfo() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Params: ").append(this.params).append("\nUser: ").append(System.getProperty("user.name")).append("\nComputer: ");
        try {
            sb.append(InetAddress.getLocalHost().getHostName());
        }
        catch (UnknownHostException e) {
            sb.append("Cant get host");
        }
        return sb.toString();
    }
    
    public void setParams(final Map<String, String> params) {
        this.params = params;
    }
}
