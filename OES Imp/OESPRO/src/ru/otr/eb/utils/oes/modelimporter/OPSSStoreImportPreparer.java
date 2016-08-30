// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class OPSSStoreImportPreparer extends JdbcDaoSupport
{
    private static Logger log;
    
    public void doPrepare() {
        OPSSStoreImportPreparer.log.info("Deleting records from jps_changelog, older than 24 hours");
        final int deletedCount = this.getJdbcTemplate().update("delete from jps_changelog where createdate < (select(max(createdate) - 1) from jps_changelog)");
        OPSSStoreImportPreparer.log.info("{} records are deleted", (Object)deletedCount);
    }
    
    static {
        OPSSStoreImportPreparer.log = LoggerFactory.getLogger((Class)OPSSStoreImportPreparer.class);
    }
}
