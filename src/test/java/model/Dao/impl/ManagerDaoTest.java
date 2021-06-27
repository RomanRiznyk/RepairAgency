package model.Dao.impl;

import com.repair.agency.model.dao.jdbc.impl.JdbcManagerDao;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ManagerDaoTest {
    Connection con;
    JdbcManagerDao adminDao;

    @BeforeClass
    public static void dbCreate() throws SQLException, FileNotFoundException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        String mysqlUrl = "jdbc:mysql://localhost:3306?serverTimezone=EET";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "root");
        ScriptRunner sr = new ScriptRunner(con);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/db-test.sql"));
        sr.runScript(reader);
    }

    @Before
    public void jdbcCreate() throws SQLException {
        con = DBConnector.getDataSource().getConnection();
        adminDao = new JdbcManagerDao(con);
    }

    @Test
    public void getInvoiceByStatusTest() {
        List<Receipt> receiptList = adminDao.getReceiptListByStatus("Done");
        assertEquals(4, receiptList.size());
    }

    /*@Test
    public void testGetInvoiceByIdTest() {
        Receipt receipt = adminDao.getInvoiceById(3);
        assertNotNull(receipt);
    }*/

    @Test
    public void updateInvoiceEngineerTest() {
        boolean result = adminDao.updateReceiptMaster(2, 27);
        assertTrue(result);
    }

    @Test
    public void updateInvoicePriceTest() {
        boolean result = adminDao.updateReceiptPrice(2, "777");
        assertTrue(result);
    }

    @Test
    public void updateInvoiceStatusTest() {
        boolean result = adminDao.updateReceiptStatus(2, "DONE");
        assertTrue(result);
    }

    @Test
    public void selectAllInvoicesTest() {
        List<Receipt> receipts = adminDao.getAllReceipts();
        assertEquals(15, receipts.size());
    }

    @Test
    public void getAllUsersTest() {
        List<User> list = adminDao.getAllUsers();
        assertEquals(6, list.size());
    }

    @Test
    public void setUsersWalletTest() {
        boolean result = adminDao.setUserBalance("25", "70.00");
        assertTrue(result);
    }
}
