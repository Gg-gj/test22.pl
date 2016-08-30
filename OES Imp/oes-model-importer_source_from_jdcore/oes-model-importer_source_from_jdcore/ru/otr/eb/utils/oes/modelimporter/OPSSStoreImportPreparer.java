package ru.otr.eb.utils.oes.modelimporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;





public class OPSSStoreImportPreparer
  extends JdbcDaoSupport
{
  private static Logger log = LoggerFactory.getLogger(OPSSStoreImportPreparer.class);
  



  public OPSSStoreImportPreparer() {}
  



  public void doPrepare()
  {
    log.info("Deleting records from jps_changelog, older than 24 hours");
    int deletedCount = getJdbcTemplate().update("delete from jps_changelog where createdate < (select(max(createdate) - 1) from jps_changelog)");
    
    log.info("{} records are deleted", Integer.valueOf(deletedCount));
  }
}
