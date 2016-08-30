// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.actions;

public class Action
{
    private long id;
    private int version;
    private String applicationCode;
    private String resourceTypeCode;
    private String actionCode;
    private String actionName;
    
    public long getId() {
        return this.id;
    }
    
    public void setId(final long id) {
        this.id = id;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public void setVersion(final int version) {
        this.version = version;
    }
    
    public String getApplicationCode() {
        return this.applicationCode;
    }
    
    public void setApplicationCode(final String applicationCode) {
        this.applicationCode = applicationCode;
    }
    
    public String getResourceTypeCode() {
        return this.resourceTypeCode;
    }
    
    public void setResourceTypeCode(final String resourceTypeCode) {
        this.resourceTypeCode = resourceTypeCode;
    }
    
    public String getActionCode() {
        return this.actionCode;
    }
    
    public void setActionCode(final String actionCode) {
        this.actionCode = actionCode;
    }
    
    public String getActionName() {
        return this.actionName;
    }
    
    public void setActionName(final String actionName) {
        this.actionName = actionName;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Action action = (Action)o;
        if (this.actionCode != null) {
            if (!this.actionCode.equals(action.actionCode)) {
                return false;
            }
        }
        else if (action.actionCode != null) {
            return false;
        }
        if (this.applicationCode != null) {
            if (!this.applicationCode.equals(action.applicationCode)) {
                return false;
            }
        }
        else if (action.applicationCode != null) {
            return false;
        }
        if (this.resourceTypeCode != null) {
            if (!this.resourceTypeCode.equals(action.resourceTypeCode)) {
                return false;
            }
        }
        else if (action.resourceTypeCode != null) {
            return false;
        }
        return true;
        //b = false;
        //return b;
    }
    
    @Override
    public int hashCode() {
        int result = (this.applicationCode != null) ? this.applicationCode.hashCode() : 0;
        result = 31 * result + ((this.resourceTypeCode != null) ? this.resourceTypeCode.hashCode() : 0);
        result = 31 * result + ((this.actionCode != null) ? this.actionCode.hashCode() : 0);
        return result;
    }
}
