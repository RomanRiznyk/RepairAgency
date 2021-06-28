package com.repair.agency.model.dao;

import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;
import com.repair.agency.model.exceptionhandler.DBException;

import java.util.List;

public interface MasterDao extends GenericDao<Receipt> {
    List<User> getAllEngineers();
    List<Receipt> getAllReceipts(String login);
    boolean updateReceiptStatus(int receiptId, String newStatus) throws DBException;

}
