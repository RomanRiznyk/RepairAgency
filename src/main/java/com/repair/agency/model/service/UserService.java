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

    public User findUser(String email, String password) {
        User user = new User();
        try (UserDao userDao = daoFactory.createUserDao()) {
            user = userDao.getUserByLoginPassword(email, password);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return user;
    }

    public Optional<User> findUserByLogin(String login) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.getUserByLogin(login);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    public boolean insertUser(String email, String login, String password) {
        boolean result = false;
        try (UserDao userDao = daoFactory.createUserDao()) {
            result = userDao.insertUser(email, login, password);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public List<Receipt> selectInvoicesByEmail(String email) {
        List<Receipt> userInvoicesList = new ArrayList<>();
        try (UserDao userDao = daoFactory.createUserDao()) {
            userInvoicesList = userDao.getReceiptsByUserEmail(email);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return userInvoicesList;
    }

    public boolean createReceipt(String login, String item, String description) {
        boolean result = false;
        try (UserDao userDao = daoFactory.createUserDao()) {
            result = userDao.createReceipt(login, item, description);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public boolean createReceiptWithMaster(String login, String item, String description, String masterLogin) {
        boolean result = false;
        try (UserDao userDao = daoFactory.createUserDao()) {
            result = userDao.createReceiptWithMaster(login , item, description, masterLogin);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public boolean addFeedback(int receiptId, String feedback) {
        boolean result = false;
        try (UserDao userDao = daoFactory.createUserDao()) {
            result = userDao.addFeedback(receiptId, feedback);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public boolean updateBalanceByLogin(String invoiceUser, BigDecimal price) {
        boolean result = false;
        try (UserDao userDao = daoFactory.createUserDao()) {
            result = userDao.updateUserBalance(invoiceUser, price);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public User getUserByEmail(String email) {
        User user = new User();
        try (UserDao userDao = daoFactory.createUserDao()) {
            user = userDao.getUserByEmail(email);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return user;
    }

    public User getUser(String login) {
        User user = new User();
        //todo
        return user;


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
