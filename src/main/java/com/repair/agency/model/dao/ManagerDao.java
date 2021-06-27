package com.repair.agency.model.dao;

import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface ManagerDao extends GenericDao<Receipt> {
    boolean updateReceiptMaster(int receiptId, int masterId);

    boolean updateReceiptPrice(int receiptId, String price);

    boolean updateReceiptStatus(int receiptId, String newStatus);

    List<Receipt> getAllReceipts();

    Receipt getReceiptById(int id) throws SQLException;

    List<User> getAllUsers();

    boolean setUserBalance(String userId, String newBalance);

    List<Receipt> getReceiptListByStatus(String status);

    boolean updateUserAddBalance(String userLogin, BigDecimal addBalance);

    boolean updateUserAddBalanceTest(String userLogin, BigDecimal oldBalance, BigDecimal addBalance);

    boolean updateReceiptStatusAndReturnMoney(int receiptID, String status);

    boolean updateReceiptStatusAndReturnMoney1Method(int receiptID, String status);

    List<Receipt> getReceiptsSortedByDate(String ascType);

    List<Receipt> getReceiptsSortedByStatus(String ascType);

    List<Receipt> getReceiptsSortedByPrice(String ascType);
}
