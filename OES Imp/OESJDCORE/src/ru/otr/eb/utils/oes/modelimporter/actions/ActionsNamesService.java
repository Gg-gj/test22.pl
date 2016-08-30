package ru.otr.eb.utils.oes.modelimporter.actions;

import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;









public class ActionsNamesService
  extends HibernateDaoSupport
{
  public ActionsNamesService() {}
  
  private static Logger log = LoggerFactory.getLogger(ActionsNamesService.class);
  
  public int deleteAll(final String applicationCode) {
    ((Integer)getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
        ActionsNamesService.log.debug("Deleting all action names for application '{}'", applicationCode);
        int deletedCount = session.createQuery("delete Action where applicationCode = :applicationCode").setString("applicationCode", applicationCode).executeUpdate();
        


        ActionsNamesService.log.info("All [{}] action names deleted for application '{}'", Integer.valueOf(deletedCount), applicationCode);
        return Integer.valueOf(deletedCount);
      }
    })).intValue();
  }
  
  public int deleteAll(final String applicationCode, final String resourceTypeCode) {
    ((Integer)getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
        ActionsNamesService.log.debug("Deleting all action names for application/resourceType '{}/{}'", applicationCode, resourceTypeCode);
        int deletedCount = session.createQuery("delete Action where applicationCode = :applicationCode and resourceTypeCode=:resourceTypeCode").setString("applicationCode", applicationCode).setString("resourceTypeCode", resourceTypeCode).executeUpdate();
        





        ActionsNamesService.log.info("All [{}] action names deleted for application/resourceType '{}/{}'", new Object[] { Integer.valueOf(deletedCount), applicationCode, resourceTypeCode });
        return Integer.valueOf(deletedCount);
      }
    })).intValue();
  }
  
  public int delete(final String applicationCode, final String resourceTypeCode, final String actionCode) {
    ((Integer)getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
        ActionsNamesService.log.debug("Deleting action name [{}/{}/{}]", new Object[] { applicationCode, resourceTypeCode, actionCode });
        int deletedCount = session.createQuery("delete Action where applicationCode = :applicationCode and resourceTypeCode=:resourceTypeCode and actionCode=:actionCode").setString("applicationCode", applicationCode).setString("resourceTypeCode", resourceTypeCode).setString("actionCode", actionCode).executeUpdate();
        







        ActionsNamesService.log.info("Action name [{}/{}/{}] deleted", new Object[] { Integer.valueOf(deletedCount), applicationCode, resourceTypeCode, actionCode });
        return Integer.valueOf(deletedCount);
      }
    })).intValue();
  }
  
  public void createOrUpdate(final String applicationCode, final String resourceTypeCode, final String actionCode, final String actionName)
  {
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
        boolean actionNameExist = 0L != ((Long)session.createQuery("select count(*) from Action where applicationCode = :applicationCode and resourceTypeCode=:resourceTypeCode and actionCode=:actionCode and actionName=:actionName").setString("actionName", actionName).setString("applicationCode", applicationCode).setString("resourceTypeCode", resourceTypeCode).setString("actionCode", actionCode).uniqueResult()).longValue();
        









        if (actionNameExist) {
          ActionsNamesService.log.debug("Action name exists [{}/{}/{} - {}]", new Object[] { applicationCode, resourceTypeCode, actionCode, actionName });
        } else {
          int updateCount = session.createQuery("update Action set actionName=:actionName where applicationCode = :applicationCode and resourceTypeCode=:resourceTypeCode and actionCode=:actionCode").setString("actionName", actionName).setString("applicationCode", applicationCode).setString("resourceTypeCode", resourceTypeCode).setString("actionCode", actionCode).executeUpdate();
          









          if (updateCount == 0) {
            Action action = new Action();
            action.setApplicationCode(applicationCode);
            action.setResourceTypeCode(resourceTypeCode);
            action.setActionCode(actionCode);
            action.setActionName(actionName);
            getHibernateTemplate().saveOrUpdate(action);
            ActionsNamesService.log.info("Action name added [{}/{}/{} - {}]", new Object[] { applicationCode, resourceTypeCode, actionCode, actionName });
          } else {
            ActionsNamesService.log.info("Action name updated [{}/{}/{} - {}]", new Object[] { applicationCode, resourceTypeCode, actionCode, actionName });
          }
        }
        return null;
      }
    });
  }
}
