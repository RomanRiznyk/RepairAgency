package model.Dao.impl;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public class DBConnector {
    public static DataSource getDataSource(){
        MysqlDataSource mds = new MysqlDataSource();
        mds.setURL("jdbc:mysql://localhost:3306/repair?useSSL=false");
        mds.setDatabaseName("repair");
        mds.setUser("root");
        mds.setPassword("root");
        return mds;
    }
}
