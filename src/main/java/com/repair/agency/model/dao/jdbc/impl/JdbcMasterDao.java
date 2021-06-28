package com.repair.agency.model.dao.jdbc.impl;

import com.repair.agency.model.dao.MasterDao;
import com.repair.agency.model.dao.jdbc.constants.SqlConstants;
import com.repair.agency.model.dao.maper.ReceiptMapper;
import com.repair.agency.model.dao.maper.UserMapper;
import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;
import com.repair.agency.model.exceptionhandler.DBException;
import com.repair.agency.model.exceptionhandler.DBWasChangedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcMasterDao implements MasterDao {
    private static final Logger logger = LogManager.getLogger(JdbcManagerDao.class.getName());
    private final Connection connection;
    UserMapper userMapper = new UserMapper();
    ReceiptMapper receiptMapper = new ReceiptMapper();

    public JdbcMasterDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> getAllEngineers() {
        List<User> engineers = new ArrayList<>();
        try (Connection con = connection;
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SqlConstants.GET_ENGINEERS);
            while (rs.next()) {
                User user = userMapper.extractFromResultSet(rs);
                engineers.add(user);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return engineers;
    }

    public List<Receipt> getInvoicesByEmail(String engineerEmail) {
        List<Receipt> receiptList = new ArrayList<>();
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.GET_MASTER_INVOICES_BY_EMAIL)) {
            prepStmt.setString(1, engineerEmail);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                Receipt receipt = receiptMapper.extractFromResultSet(rs);
                receiptList.add(receipt);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return receiptList;
    }

    @Override
    public List<Receipt> getAllReceipts(String login) {
        List<Receipt> receiptList = new ArrayList<>();
        ResultSet rs = null;
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.GET_MASTER_RECEIPTS_BY_LOGIN)) { // todo with Users Login
            prepStmt.setString(1, login);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                Receipt receipt = receiptMapper.extractFromResultSet(rs);
                receipt.setMasterLogin(rs.getString("masters.login"));
                receipt.setUserLogin(rs.getString("customer.login"));
                receiptList.add(receipt);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            close(rs);
        }
        return receiptList;
    }

    @Override
    public boolean updateReceiptStatus(int receiptId, String newStatus) throws DBException {
        boolean isSuccessful = false;
        logger.info("CONNECTION updateReceiptStatus = " + connection);
        try (
                PreparedStatement prepStmt = connection.prepareStatement(SqlConstants.UPDATE_RECEIPT_STATUS)) {
            if (isFeedbackPresent(receiptId)) {
                logger.info("FEEDBACK IS PRESENT");
                throw new DBWasChangedException("User has already added feedback. You can`t change status. Please, refresh the page");
            }
            prepStmt.setString(1, newStatus);
            prepStmt.setInt(2, receiptId);
            isSuccessful = prepStmt.executeUpdate() > 0;
        } catch (DBWasChangedException e) {
            logger.error("Cannot update receipt status:" + e.getMessage());
            throw new DBException("Cannot update receipt status:" + e.getMessage(), e);
        }
        catch (SQLException e) {
            logger.error("Cannot update receipt status:" + e.getMessage());
            throw new DBException();
        }
        System.out.println("UPDATE STATUS IS = " + isSuccessful);
        return isSuccessful;
    }

    private boolean isFeedbackPresent(int receiptId) throws DBException {
        boolean isPresent = false;
        ResultSet rs = null;
        try (PreparedStatement pstmt = connection.prepareStatement(SqlConstants.GET_FEEDBACK_BY_ID)){
            pstmt.setInt(1, receiptId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String  feedback = rs.getString("feedback");
                if (feedback != null && !"".equals(feedback)) {
                    isPresent = true;
                }
                logger.info("FEEDBACK from RESULTSET = \"" + feedback + "\"");
            }

        } catch (SQLException ex) {
            logger.error("Cant read Feedback",  ex);
            ex.printStackTrace();
            throw new DBException("Cant read Feedback", ex);
        } finally {
            close(rs);
        }
        logger.info("Feedback is present = " + isPresent);
        return isPresent;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private void close(AutoCloseable ac){
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
