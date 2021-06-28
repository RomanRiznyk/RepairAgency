package com.repair.agency.model.service;

import com.repair.agency.model.dao.DaoFactory;
import com.repair.agency.model.dao.UserDao;
import com.repair.agency.model.dao.jdbc.impl.JdbcManagerDao;
import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger logger = LogManager.getLogger(JdbcManagerDao.class.getName());

    public Optional<User> findUserByLogin(String login) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.getUserByLogin(login);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    public boolean createReceipt(String login, String item, String description) {
        boolean isSuccess = false;
        try (UserDao userDao = daoFactory.createUserDao()) {
            isSuccess = userDao.createReceipt(login, item, description);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    public boolean createReceiptWithMaster(String login, String item, String description, String masterLogin) {
        boolean isSuccess = false;
        try (UserDao userDao = daoFactory.createUserDao()) {
            isSuccess = userDao.createReceiptWithMaster(login , item, description, masterLogin);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    public boolean addFeedback(int receiptId, String feedback) {
        boolean isSuccess = false;
        try (UserDao userDao = daoFactory.createUserDao()) {
            isSuccess = userDao.addFeedback(receiptId, feedback);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    public boolean updateBalanceByLogin(String invoiceUser, BigDecimal price) {
        boolean isSuccess = false;
        try (UserDao userDao = daoFactory.createUserDao()) {
            isSuccess = userDao.updateUserBalance(invoiceUser, price);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    public BigDecimal getBalance(String login) {
        BigDecimal balance = null;  // todo Optional
        try (UserDao userDao = daoFactory.createUserDao()) {
            balance = userDao.getBalance(login);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return balance;
    }

    public List<Receipt> getReceiptsByLogin(String login) {
        List<Receipt> receipts = new ArrayList<>();
        try (UserDao userDao = daoFactory.createUserDao()) {
            receipts = userDao.getReceiptsByLogin(login);
        } catch (Exception e) {
            logger.error("Cannot get Receipts by login: " + e.getMessage());
        }
        return receipts;
    }

    public Optional<User> createUser(String login, String password) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.createUser(login, password);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<User> createUserWithMail(String login, String password, String email) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.createUserWithMail(login, password, email);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }
}
