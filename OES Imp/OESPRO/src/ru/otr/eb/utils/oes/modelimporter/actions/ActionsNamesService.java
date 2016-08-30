// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter.actions;

import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.slf4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ActionsNamesService extends HibernateDaoSupport
{
    private static Logger log;
    
    public int deleteAll(final String applicationCode) {
        return (Integer) this.getHibernateTemplate().execute((HibernateCallback)new HibernateCallback<Object>() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                ActionsNamesService.log.debug("Deleting all action names for application '{}'", (Object)applicationCode);
                final int deletedCount = session.createQuery("delete Action where applicationCode = :applicationCode").setString("applicationCode", applicationCode).executeUpdate();
                ActionsNamesService.log.info("All [{}] action names deleted for application '{}'", (Object)deletedCount, (Object)applicationCode);
                return deletedCount;
            }
        });
    }
    
    public int deleteAll(final String applicationCode, final String resourceTypeCode) {
        return (Integer) this.getHibernateTemplate().execute((HibernateCallback)new HibernateCallback<Object>() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                ActionsNamesService.log.debug("Deleting all action names for application/resourceType '{}/{}'", (Object)applicationCode, (Object)resourceTypeCode);
                final int deletedCount = session.createQuery("delete Action where applicationCode = :applicationCode and resourceTypeCode=:resourceTypeCode").setString("applicationCode", applicationCode).setString("resourceTypeCode", resourceTypeCode).executeUpdate();
                ActionsNamesService.log.info("All [{}] action names deleted for application/resourceType '{}/{}'", new Object[] { deletedCount, applicationCode, resourceTypeCode });
                return deletedCount;
            }
        });
    }
    
    public int delete(final String applicationCode, final String resourceTypeCode, final String actionCode) {
        return (Integer) this.getHibernateTemplate().execute( new HibernateCallback<Object>() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                ActionsNamesService.log.debug("Deleting action name [{}/{}/{}]", new Object[] { applicationCode, resourceTypeCode, actionCode });
                final int deletedCount = session.createQuery("delete Action where applicationCode = :applicationCode and resourceTypeCode=:resourceTypeCode and actionCode=:actionCode").setString("applicationCode", applicationCode).setString("resourceTypeCode", resourceTypeCode).setString("actionCode", actionCode).executeUpdate();
                ActionsNamesService.log.info("Action name [{}/{}/{}] deleted", new Object[] { deletedCount, applicationCode, resourceTypeCode, actionCode });
                return deletedCount;
            }
        });
    }
    
    public void createOrUpdate(final String applicationCode, final String resourceTypeCode, final String actionCode, final String actionName) {
        this.getHibernateTemplate().execute((HibernateCallback)new HibernateCallback<Object>() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                final boolean actionNameExist = 0L != (Long)session.createQuery("select count(*) from Action where applicationCode = :applicationCode and resourceTypeCode=:resourceTypeCode and actionCode=:actionCode and actionName=:actionName").setString("actionName", actionName).setString("applicationCode", applicationCode).setString("resourceTypeCode", resourceTypeCode).setString("actionCode", actionCode).uniqueResult();
                if (actionNameExist) {
                    ActionsNamesService.log.debug("Action name exists [{}/{}/{} - {}]", new Object[] { applicationCode, resourceTypeCode, actionCode, actionName });
                }
                else {
                    final int updateCount = session.createQuery("update Action set actionName=:actionName where applicationCode = :applicationCode and resourceTypeCode=:resourceTypeCode and actionCode=:actionCode").setString("actionName", actionName).setString("applicationCode", applicationCode).setString("resourceTypeCode", resourceTypeCode).setString("actionCode", actionCode).executeUpdate();
                    if (updateCount == 0) {
                        final Action action = new Action();
                        action.setApplicationCode(applicationCode);
                        action.setResourceTypeCode(resourceTypeCode);
                        action.setActionCode(actionCode);
                        action.setActionName(actionName);
                        ActionsNamesService.this.getHibernateTemplate().saveOrUpdate((Object)action);
                        ActionsNamesService.log.info("Action name added [{}/{}/{} - {}]", new Object[] { applicationCode, resourceTypeCode, actionCode, actionName });
                    }
                    else {
                        ActionsNamesService.log.info("Action name updated [{}/{}/{} - {}]", new Object[] { applicationCode, resourceTypeCode, actionCode, actionName });
                    }
                }
                return null;
            }
        });
    }
    
    static {
        ActionsNamesService.log = LoggerFactory.getLogger((Class)ActionsNamesService.class);
    }
}
