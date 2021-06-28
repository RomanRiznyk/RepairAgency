package com.repair.agency.model.dao;

import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> getUserByLogin(String login);

    boolean createReceipt(String login, String item, String description);

    boolean addFeedback(int id, String feedback);

    boolean updateUserBalance(String invoiceUser, BigDecimal price);

    BigDecimal getBalance(String login);

    List<Receipt> getReceiptsByLogin(String login);

    boolean createReceiptWithMaster(String login, String item, String description, String masterLogin);

    Optional<User> createUser(String login, String password);

    Optional<User> createUserWithMail(String login, String password, String email);
}