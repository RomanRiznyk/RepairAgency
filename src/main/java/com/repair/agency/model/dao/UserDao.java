package com.repair.agency.model.dao;

import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao {
    User getUserByLoginPassword(String email, String password);

    Optional<User> getUserByLogin(String login);

    boolean insertUser(String email, String login, String password);

    List<Receipt> getReceiptsByUserEmail(String email);

    boolean createReceipt(String login, String item, String description);

    boolean addFeedback(int id, String feedback);

    boolean updateUserBalance(String invoiceUser, BigDecimal price);

    User getUserByEmail(String email);

    BigDecimal getBalance(String login);

    List<Receipt> getReceiptsByLogin(String login);

    boolean createReceiptWithMaster(String login, String item, String description, String masterLogin);

    Optional<User> createUser(String login, String password);

    Optional<User> createUserWithMail(String login, String password, String email);
}
