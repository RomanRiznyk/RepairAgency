package model.Dao.impl;

import com.repair.agency.model.dao.jdbc.impl.JdbcManagerDao;
import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.exceptionhandler.DBException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class H2Test {

    private JdbcManagerDao jdbcManagerDao;

//    @BeforeClass
//    public void startDB() throws SQLException {
//        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
//
//    }
//
//    @AfterClass
//    public void stopDB() throws SQLException {
//        Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
//    }


    @BeforeClass
    public static void jdbcCreate() throws SQLException, FileNotFoundException {
        Connection connection = DBConnector.getDataSourceH2().getConnection();
        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/db-testH2.sql"));
        sr.runScript(reader);
    }

    @Test(expected = DBException.class)
    public void test() throws SQLException {
        jdbcManagerDao = new JdbcManagerDao(DBConnector.getDataSourceH2().getConnection());
        Receipt receiptById = jdbcManagerDao.getReceiptById(-1);
//        System.out.println(receiptById);
//        assertEquals(1, receiptById.getId());
    }

}
