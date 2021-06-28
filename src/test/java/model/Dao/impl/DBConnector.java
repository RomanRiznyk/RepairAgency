package model.Dao.impl;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
    public static DataSource getDataSourceMSQL(){
        MysqlDataSource mds = new MysqlDataSource();
        mds.setURL("jdbc:mysql://localhost:3306/repair?allowPublicKeyRetrieval=true&useSSL=false");
        mds.setDatabaseName("repair");
        mds.setUser("root");
        mds.setPassword("123456");
        return mds;
    }

    public static DataSource getDataSourceH2() throws SQLException {
//        JdbcDataSource ds = new JdbcDataSource();
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:h2:mem:repair;DB_CLOSE_DELAY=-1;MODE=MYSQL;DATABASE_TO_LOWER=TRUE;CASE_INSENSITIVE_IDENTIFIERS=TRUE");
        //ds.setDefaultAutoCommit(false);
//        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("sa");
        ds.setPassword("sa");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
        //ds.//allowPublicKeyRetrieval=true
//        dataSource = ds;
//        ds.setURL("jdbc:h2://localhost:9092/repair;AUTO_SERVER=TRUE");
//        ds.setURL("jdbc:h2:mem:repair;DB_CLOSE_DELAY=-1;MODE=MYSQL;DATABASE_TO_LOWER=TRUE;CASE_INSENSITIVE_IDENTIFIERS=TRUE");
//        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }


}
