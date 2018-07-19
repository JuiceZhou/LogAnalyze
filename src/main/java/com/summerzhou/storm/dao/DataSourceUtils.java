package com.summerzhou.storm.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;

/**
 * 单例模式
 */
public class DataSourceUtils {
    private Logger logger = Logger.getLogger(DataSourceUtils.class);
    private static ComboPooledDataSource dataSource;
    public static DataSource getDataSource(){
        if(dataSource == null){
            dataSource = new ComboPooledDataSource();
        }
        return dataSource;
    }
}
