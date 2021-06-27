package com.repair.agency.model.dao.jdbc;

import com.repair.agency.model.dao.ManagerDao;
import com.repair.agency.model.dao.DaoFactory;
import com.repair.agency.model.dao.MasterDao;
import com.repair.agency.model.dao.UserDao;
import com.repair.agency.model.dao.jdbc.impl.JdbcManagerDao;
import com.repair.agency.model.dao.jdbc.impl.JdbcMasterDao;
import com.repair.agency.model.dao.jdbc.impl.JdbcUserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoFactory extends DaoFactory {
    private static final Logger logger = LogManager.getLogger(JdbcManagerDao.class.getName());
    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    private Connection getConnection() throws SQLException { // todo Handle ex
            Connection con = dataSource.getConnection();
            logger.info(" JdbcDaoFactory -> getConnection -> con = " + con);
            return con;
    }

    @Override
    public ManagerDao createManagerDao() throws SQLException {
        return new JdbcManagerDao(getConnection());
    }

    @Override
    public MasterDao createMasterDao() throws SQLException {
        return new JdbcMasterDao(getConnection());
    }

    @Override
    public UserDao createUserDao() throws SQLException {
        return new JdbcUserDao(getConnection());
    }


}
