package com.repair.agency.model.service;

import com.repair.agency.model.dao.ManagerDao;
import com.repair.agency.model.dao.DaoFactory;
import com.repair.agency.model.dao.jdbc.impl.JdbcManagerDao;
import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;
import com.repair.agency.model.exceptionhandler.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerService {
    DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger logger = LogManager.getLogger(JdbcManagerDao.class.getName());

    public boolean updateReceiptMaster(int engineerId, int invoiceId) {
        boolean result = false;
        try (ManagerDao managerDao = daoFactory.createManagerDao()) {
            result = managerDao.updateReceiptMaster(invoiceId, engineerId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public boolean updateReceiptPrice(String price, int receiptId) {
        boolean result = false;
        try (ManagerDao managerDao = daoFactory.createManagerDao()) {
            result = managerDao.updateReceiptPrice(receiptId, price);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public boolean updateReceiptStatus(int receiptId, String newStatus) throws DBException{
        boolean result = false;
        try (ManagerDao managerDao = daoFactory.createManagerDao()) {
            result = managerDao.updateReceiptStatus(receiptId, newStatus);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public List<Receipt> getAllReceipts() {
        List<Receipt> invoicesList = new ArrayList<>();
        try (ManagerDao managerDao = daoFactory.createManagerDao()) {
            invoicesList = managerDao.selectAllReceipts();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return invoicesList;
    }

    public Receipt getReceipt(int id) {
        Receipt receipt = new Receipt();
        try (ManagerDao managerDao = daoFactory.createManagerDao()) {
            receipt = managerDao.getReceiptById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return receipt;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (ManagerDao managerDao = daoFactory.createManagerDao()) {
            userList = managerDao.getAllUsers();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return userList;
    }

    public boolean setUsersWallet(String userId, String walletValue) {
        boolean result = false;
        try (ManagerDao managerDao = daoFactory.createManagerDao()) {
            result = managerDao.setUserBalance(userId, walletValue);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public List<Receipt> getReceiptsByStatus(String status) {
        List<Receipt> invoicesList = new ArrayList<>();
        try (ManagerDao managerDao = daoFactory.createManagerDao()) {
            invoicesList = managerDao.getReceiptListByStatus(status);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return invoicesList;
    }

    public boolean updateUserAddBalance(String userLogin, BigDecimal oldBalance, BigDecimal addBalance) {
        logger.info("ManagerService updateUserAddBalance starts");
        boolean result = false;
        try (ManagerDao managerDao = daoFactory.createManagerDao()) {
            result = managerDao.updateUserAddBalanceTest(userLogin, oldBalance, addBalance);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }


    public boolean updateStatusAndReturnMoney(int receiptID, String status) throws DBException {
        logger.info("ManagerService updateStatusAndReturnMoney starts");
        boolean isSuccessful = false;
        try (ManagerDao managerDao = daoFactory.createManagerDao()) {
            isSuccessful = managerDao.updateReceiptStatusAndReturnMoney1Method(receiptID, status);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DBException("Cant updateStatusAndReturnMoney", e);
        }
        return isSuccessful;
    }
}