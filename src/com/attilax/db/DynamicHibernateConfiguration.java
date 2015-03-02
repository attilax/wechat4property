package com.attilax.db;
import org.hibernate.HibernateException; 
import org.hibernate.cfg.Configuration; 
 
/**
 * 加入了连接池
 * @author 陈道俊
 *
 */ 
public class DynamicHibernateConfiguration extends Configuration { 
    public DynamicHibernateConfiguration() { 
        super(); 
    } 
 
    public void reset() { 
        super.reset(); 
    } 
 
    public DynamicHibernateConfiguration(String dialect, String driverClass, 
            String ipAddress, String port, String dataBaseName, 
            String username, String password) throws HibernateException { 
        String connection_url = ""; 
        if (dialect.indexOf("MySQL") > -1) { 
            connection_url = "jdbc:mysql://" + ipAddress + "/" + dataBaseName; 
        } else if (dialect.indexOf("SQLServer") > -1) { 
            connection_url = "jdbc:sqlserver://" + ipAddress + ":" + port 
                    + ";DataBaseName=" + dataBaseName; 
        } else if (dialect.indexOf("Oracle") > -1) { 
            connection_url = "jdbc:oracle:thin:@" + ipAddress + ":" + port 
                    + ":" + dataBaseName; 
        } else { 
            throw new HibernateException("The dialect was not allowed.==fd==" 
                    + dialect); 
        } 
 
        super.setProperty("hibernate.dialect", dialect); 
        super.setProperty("hibernate.connection.url", connection_url); 
        super.setProperty("hibernate.connection.driver_class", driverClass); 
        super.setProperty("hibernate.connection.username", username); 
        super.setProperty("hibernate.connection.password", password); 
        super.setProperty("bonecp.idleMaxAge", "30"); 
        super.setProperty("bonecp.idleConnectionTestPeriod", "5"); 
        super.setProperty("bonecp.partitionCount", "3"); 
        super.setProperty("bonecp.acquireIncrement", "1"); 
        super.setProperty("bonecp.maxConnectionsPerPartition", "30"); 
        super.setProperty("bonecp.minConnectionsPerPartition", "10"); 
        super.setProperty("bonecp.statementsCacheSize", "50"); 
        super.setProperty("bonecp.releaseHelperThreads", "3"); 
        super.setProperty("bonecp.connectionTestStatement", "select 1 from dual"); 
//      super.setProperty("hibernate.show_sql", "true");      
    } 
 
    public DynamicHibernateConfiguration(String dialect, String driverClass, 
            String ipAddress, String port, String dataBaseName, 
            String username, String password, String schema, String catalog) 
            throws HibernateException { 
        String connection_url = ""; 
        if (dialect.indexOf("MySQL") > -1) { 
            connection_url = "jdbc:mysql://" + ipAddress + "/" + dataBaseName; 
        } else if (dialect.indexOf("SQLServer") > -1) { 
            connection_url = "jdbc:sqlserver://" + ipAddress + ":" + port 
                    + ";DataBaseName=" + dataBaseName; 
        } else if (dialect.indexOf("Oracle") > -1) { 
            connection_url = "jdbc:oracle:thin:@" + ipAddress + ":" + port 
                    + ":" + dataBaseName; 
        } else { 
            throw new HibernateException("The dialect was not allowed.==fd==" 
                    + dialect); 
        } 
 
        super.setProperty("hibernate.dialect", dialect); 
        super.setProperty("hibernate.connection.url", connection_url); 
        super.setProperty("hibernate.connection.driver_class", driverClass); 
        super.setProperty("hibernate.connection.username", username); 
        super.setProperty("hibernate.connection.password", password); 
        super.setProperty("hibernate.default_schema", schema); 
//      super.setProperty("hibernate.default_catalog", catalog);  
//      super.setProperty("hibernate.show_sql", "true");     
        super.setProperty("bonecp.idleMaxAge", "30"); 
        super.setProperty("bonecp.idleConnectionTestPeriod", "5"); 
        super.setProperty("bonecp.partitionCount", "3"); 
        super.setProperty("bonecp.acquireIncrement", "1"); 
        super.setProperty("bonecp.maxConnectionsPerPartition", "30"); 
        super.setProperty("bonecp.minConnectionsPerPartition", "10"); 
        super.setProperty("bonecp.statementsCacheSize", "50"); 
        super.setProperty("bonecp.releaseHelperThreads", "3"); 
        super.setProperty("bonecp.connectionTestStatement", "select 1 from dual"); 
    } 
 
} 