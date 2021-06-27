package com.repair.agency.model.dao.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/repair?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl(JDBC_URL);
                    //ds.setDefaultAutoCommit(false);
                    ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
                    ds.setUsername(JDBC_USERNAME);
                    ds.setPassword(JDBC_PASSWORD);
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    //ds.//allowPublicKeyRetrieval=true
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }
}
