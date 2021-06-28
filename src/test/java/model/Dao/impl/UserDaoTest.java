package model.Dao.impl;

import com.repair.agency.model.dao.jdbc.impl.JdbcUserDao;
import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class UserDaoTest {
    Connection con;
    JdbcUserDao userDao;
    User expectedUser;

    @BeforeClass
    public static void dbCreate() throws SQLException, FileNotFoundException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        String mysqlUrl = "jdbc:mysql://localhost:3306/repair?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=EET";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "123456");
        ScriptRunner sr = new ScriptRunner(con);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/db-test.sql"));
        sr.runScript(reader);
    }

    @Before
    public void jdbcCreate() throws SQLException {
        con = DBConnector.getDataSourceMSQL().getConnection();
        userDao = new JdbcUserDao(con);

        expectedUser = new User();
        expectedUser.setEmail("user@gmail.com");
        expectedUser.setPassword("1");
        expectedUser.setRole("USER");
        expectedUser.setLogin("user");
    }

    @Test
    public void findUserTest() {
        User actualUser = userDao.getUserByLoginPassword("user@gmail.com", "1");
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

    /*@Test
    public void findUserByLoginTest() {
        User actualUser = userDao.findUserByLogin("user");
        assertEquals(expectedUser.getLogin(), actualUser.getLogin());
    }*/

    @Test
    public void insertUserTest() {
        boolean result = userDao.insertUser("user1@gmail.com", "user1", "123");
        assertTrue(result);
    }

    @Test
    public void selectInvoicesByEmailTest() {
        List<Receipt> receiptList = userDao.getReceiptsByUserEmail("user@gmail.com");
        assertEquals(9, receiptList.size());
    }

    @Test
    public void insertFeedbackTest() {
        boolean result = userDao.addFeedback(2, "Nice job");
        assertTrue(result);
    }

    @Test
    public void updateWalletByLoginTest() {
        boolean result = userDao.updateUserBalance("user", new BigDecimal("777"));
        assertTrue(result);
    }

    @Test
    public void getUserByEmailTest() {
        User actualUser = userDao.getUserByEmail("user@gmail.com");
        assertEquals(expectedUser.getLogin(), actualUser.getLogin());
    }
}
