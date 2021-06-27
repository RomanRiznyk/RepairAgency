package com.repair.agency.model.service;

import com.repair.agency.model.dao.DaoFactory;
import com.repair.agency.model.dao.ManagerDao;
import com.repair.agency.model.dao.MasterDao;
import com.repair.agency.model.dao.jdbc.impl.JdbcManagerDao;
import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;
import com.repair.agency.model.exceptionhandler.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MasterService {
    DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger logger = LogManager.getLogger(JdbcManagerDao.class.getName());


    public List<User> getAllMasters() {  // todo refactor rename
        List<User> engineers = new ArrayList<>();
        try (MasterDao masterDao = daoFactory.createMasterDao()) {
            engineers = masterDao.getAllEngineers();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return engineers;
    }

    public List<Receipt> getInvoicesByEmail(String engineerEmail) {
        List<Receipt> receiptList = new ArrayList<>();
        try (MasterDao masterDao = daoFactory.createMasterDao()) {
            receiptList = masterDao.getInvoicesByEmail(engineerEmail);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return receiptList;
    }

    public List<Receipt> getReceiptsByMaster(String login) {
        List<Receipt> receiptList = new ArrayList<>();
        try (MasterDao masterDao = daoFactory.createMasterDao()) {
            receiptList = masterDao.getAllReceipts(login);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return receiptList;
    }

    public boolean updateStatus(int receiptId, String newStatus) throws DBException {
        boolean isSuccess = false;
        try (MasterDao masterDao = daoFactory.createMasterDao()) {

            isSuccess = masterDao.updateReceiptStatus(receiptId, newStatus);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }
}
