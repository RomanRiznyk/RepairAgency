package com.repair.agency.model.dao;

import com.repair.agency.model.dao.jdbc.JdbcDaoFactory;

import java.sql.SQLException;

public abstract class DaoFactory {
    private static volatile DaoFactory daoFactory;  // todo via InstanceHolder

    public abstract ManagerDao createManagerDao() throws SQLException;

    public abstract MasterDao createMasterDao() throws SQLException;

    public abstract UserDao createUserDao() throws SQLException;

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JdbcDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
