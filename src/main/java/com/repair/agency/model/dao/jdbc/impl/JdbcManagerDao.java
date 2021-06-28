package com.repair.agency.model.dao.jdbc.impl;

import com.repair.agency.model.dao.ManagerDao;
import com.repair.agency.model.dao.jdbc.constants.SqlConstants;
import com.repair.agency.model.dao.maper.ReceiptMapper;
import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.repair.agency.model.exceptionhandler.DBException;
import com.repair.agency.model.exceptionhandler.DBWasChangedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcManagerDao implements ManagerDao {
    private static final Logger logger = LogManager.getLogger(JdbcManagerDao.class.getName());
    private final Connection connection;
    ReceiptMapper receiptMapper = new ReceiptMapper();

    public JdbcManagerDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean updateReceiptMaster(int receiptId, int masterId) {
        boolean isSuccessful = false;
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.UPDATE_INVOICE_ENGINEER)) {
            prepStmt.setInt(1, masterId);
            prepStmt.setInt(2, receiptId);
            isSuccessful = prepStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return isSuccessful;
    }

    @Override
    public boolean updateReceiptPrice(int receiptId, String price) {
        boolean isSuccessful = false;
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.UPDATE_INVOICE_PRICE)) {
            prepStmt.setBigDecimal(1, new BigDecimal(price));
            prepStmt.setInt(2, receiptId);
            isSuccessful = prepStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return isSuccessful;
    }

    @Override
    public boolean updateReceiptStatus(int receiptId, String newStatus) {
        boolean isSuccessful = false; // todo handle duplication
        logger.info("CONNECTION updateReceiptStatus = " + connection);
        try (/*Connection con = connection;*/
             PreparedStatement prepStmt = connection.prepareStatement(SqlConstants.UPDATE_RECEIPT_STATUS)) {
            if (isFeedbackAbsent(receiptId)) {
                throw new DBWasChangedException("Status has been already changed by Master or User has already added feedback. You can`t change status. Please, refresh the page");
            }
            prepStmt.setString(1, newStatus);
            prepStmt.setInt(2, receiptId);
            isSuccessful = prepStmt.executeUpdate() > 0;
            connection.commit();
        } catch (SQLException e) {
            System.out.println("SQLException -> UPDATE STATUS IS = " + isSuccessful);
            logger.debug("Cannot update receipt status" + e.getMessage());
        }
        System.out.println("UPDATE STATUS IS = " + isSuccessful);
        return isSuccessful;
    }

    @Override
    public List<Receipt> getAllReceipts() {
        List<Receipt> invoicesList = new ArrayList<>();
        try (Connection con = connection;
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SqlConstants.SELECT_ALL_RECEIPTS_WITH_MASTER_USER_LOGINS);
            while (rs.next()) {
                Receipt receipt = receiptMapper.extractFromResultSet(rs);
                receipt.setUserLogin(rs.getString("customer.login")); // todo into extractor
                receipt.setMasterLogin(rs.getString("masters.login"));
                invoicesList.add(receipt);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return invoicesList;
    }

    @Override
    public Receipt getReceiptById(int id) throws DBException {
        Receipt receipt = new Receipt();
        ResultSet rs = null;
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.FIND_RECEIPT_BY_ID_WITH_USER_MASTER_LOGINS)) {
            prepStmt.setInt(1, id);
            rs = prepStmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                throw new DBException("Receipt not found", null);
            }
            while (rs.next()) {
                receipt = receiptMapper.extractFromResultSet(rs);
                receipt.setUserLogin(rs.getString("cut"));
                receipt.setMasterLogin(rs.getString("mas"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DBException("Cannot get Receipt by ID",e);
        } finally {
            close(rs);
        }
        return receipt;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection con = connection;
            Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SqlConstants.SELECT_ALL_USERS);
            while (rs.next()) {
                User user = new User();
                user.setLogin(rs.getString("login"));
                user.setId(rs.getInt("id"));
                user.setRole(rs.getString("role"));
                user.setBalance(rs.getBigDecimal("balance"));
                userList.add(user);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return userList;
    }

    public boolean setUserBalance(String userId, String newBalance) {
        boolean isSuccessful = false;

        try (Connection con = connection;
            PreparedStatement prepStmt = con.prepareStatement(SqlConstants.SET_USERS_WALLET)) {
            BigDecimal wallet = new BigDecimal(newBalance);
            int id = Integer.parseInt(userId);
            prepStmt.setBigDecimal(1, wallet);
            prepStmt.setInt(2, id);
            isSuccessful = prepStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
        return isSuccessful;
    }

    @Override
    public List<Receipt> getReceiptListByStatus(String status) {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.SELECT_RECEIPTS_BY_STATUS_WITH_USER_MASTER_LOGINS)) {
            prepStmt.setString(1, status);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                Receipt receipt = receiptMapper.extractFromResultSet(rs);
                receipt.setUserLogin(rs.getString("customer.login"));
                receipt.setMasterLogin(rs.getString("masters.login"));
                receipts.add(receipt);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return receipts;
    }

    @Override
    public boolean updateUserAddBalanceTest(String userLogin, BigDecimal oldBalance, BigDecimal additionalBalance) {
        boolean isSuccessful = false;
        logger.info("JdbcManagerDao updateUserAddBalance starts");
        try {
            BigDecimal newBalance = oldBalance.add(additionalBalance);
            logger.info("New Balance = " + newBalance);
            isSuccessful = updateUserBalanceOnConnection(userLogin, newBalance);
            logger.info("JdbcManagerDao updateUserAddBalance success");
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(connection);
        }
        logger.info("JdbcManagerDao updateUserAddBalance ends");

        return isSuccessful;
    }

    @Override
    public boolean updateReceiptStatusAndReturnMoney1Method(int receiptId, String status) {
        boolean isSuccessful = false;
        try {
            connection.setAutoCommit(false);
            System.out.println("CONNECTION AUTOCOMMIT = " + connection.getAutoCommit());
        } catch (SQLException ex) {  // todo
            ex.printStackTrace();
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String login = "";
            pstmt = connection.prepareStatement(SqlConstants.GET_USER_LOGIN_BY_RECEIPT_ID);
            pstmt.setInt(1, receiptId);
            rs = pstmt.executeQuery();
            while (rs.next()){
                login = rs.getString("login");
            }

            BigDecimal additionalBalance = null;
            pstmt = connection.prepareStatement(SqlConstants.GET_RECEIPT_PRICE_BY_ID);
                pstmt.setInt(1, receiptId);
                rs = pstmt.executeQuery();
                while (rs.next()){
                    additionalBalance = rs.getBigDecimal("price");
                }

            BigDecimal oldBalance = getUserBalanceWithConnection(connection, login).get();
            isSuccessful = updateUserBalanceOnConnection(connection, login, additionalBalance.add(oldBalance));

            if (isFeedbackAbsent(receiptId)) {
                throw new DBWasChangedException("User has already added feedback. You can`t change status. Please, refresh the page");
            }
            pstmt = connection.prepareStatement(SqlConstants.UPDATE_RECEIPT_STATUS);
            pstmt.setString(1, status);
            pstmt.setInt(2, receiptId);
            pstmt.executeUpdate();
            connection.commit();
            isSuccessful = true;
        } catch (SQLException ex) {
            try {
                logger.debug("updateReceiptStatusAndReturnMoney => ROLLBACK"); //todo
                connection.rollback(); //
                throw new DBException(ex.getMessage(), ex);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
            close(connection);
        }
        return isSuccessful;
        }

    @Override
    public List<Receipt> getReceiptsSortedByDate(String ascType) {
        List<Receipt> receipts = new ArrayList<>();
        ResultSet rs = null;
        try (Connection con = connection;
             PreparedStatement pstmt = con.prepareStatement(SqlConstants.SELECT_ALL_RECEIPTS_WITH_MASTER_USER_LOGINS_BY_DATE + ascType)) {
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Receipt receipt = receiptMapper.extractFromResultSet(rs);
                receipt.setUserLogin(rs.getString("customer.login")); // todo into extractor
                receipt.setMasterLogin(rs.getString("masters.login"));
                receipts.add(receipt);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            close(rs);
        }
        return receipts;
    }

    @Override
    public List<Receipt> getReceiptsSortedByStatus(String ascType) {
        List<Receipt> receipts = new ArrayList<>();
        ResultSet rs = null;
        try (Connection con = connection;
             PreparedStatement pstmt = con.prepareStatement(SqlConstants.SELECT_ALL_RECEIPTS_WITH_MASTER_USER_LOGINS_BY_STATUS + ascType)) {
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Receipt receipt = receiptMapper.extractFromResultSet(rs);
                receipt.setUserLogin(rs.getString("customer.login")); // todo into extractor
                receipt.setMasterLogin(rs.getString("masters.login"));
                receipts.add(receipt);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            close(rs);
        }
        return receipts;
    }

    @Override
    public List<Receipt> getReceiptsSortedByPrice(String ascType) {
        List<Receipt> receipts = new ArrayList<>();
        ResultSet rs = null;
        try (Connection con = connection;
             PreparedStatement pstmt = con.prepareStatement(SqlConstants.SELECT_ALL_RECEIPTS_WITH_MASTER_USER_LOGINS_BY_PRICE + ascType)) {
            //pstmt.setString(1, ascType);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Receipt receipt = receiptMapper.extractFromResultSet(rs);
                receipt.setUserLogin(rs.getString("customer.login")); // todo into extractor
                receipt.setMasterLogin(rs.getString("masters.login"));
                receipts.add(receipt);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            close(rs);
        }
        return receipts;
    }

    private boolean isFeedbackAbsent(int receiptId) {
        boolean isAbsent = false;
        ResultSet rs = null;
        try (PreparedStatement pstmt = connection.prepareStatement(SqlConstants.GET_FEEDBACK_BY_ID)){
            pstmt.setInt(1, receiptId);
            String feedback = pstmt.executeQuery().getString("feedback");
            if (feedback == null || "".equals(feedback)) {
                isAbsent = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isAbsent;
    }

    public boolean updateUserBalanceOnConnection(String login, BigDecimal newBalance) {
        boolean result = false;
        try {
            connection.setAutoCommit(false);
            System.out.println("CONNECTION AUTOCOMMIT = " + connection.getAutoCommit());
        } catch (SQLException ex) {  // todo
            ex.printStackTrace();
        }
        try (/*Connection con = connection;*/
             PreparedStatement prepStmt = connection.prepareStatement(SqlConstants.UPDATE_BALANCE_BY_LOGIN)) {
            prepStmt.setBigDecimal(1, newBalance);
            prepStmt.setString(2, login);
            result = prepStmt.executeUpdate() > 0;
        } catch (SQLException | NumberFormatException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public Optional<BigDecimal> getUserBalance(String login) {
        Optional<BigDecimal> balance = Optional.empty();
        ResultSet rs = null;
        try(Connection con = connection;
            PreparedStatement pstmt = con.prepareStatement(SqlConstants.GET_USER_BALANCE_BY_LOGIN);
        ) {
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            while (rs.next()){
                balance = Optional.of(rs.getBigDecimal("balance"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // todo ex
        } finally {
            close(rs);
        }
        return balance;
    }

    private Optional<BigDecimal> getUserBalanceWithConnection(Connection con, String login) {
       Optional<BigDecimal> balance = Optional.empty();
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);
            System.out.println("CONNECTION AUTOCOMMIT = " + con.getAutoCommit());
        } catch (SQLException ex) {  // todo
            ex.printStackTrace();
        }
        try(PreparedStatement pstmt = con.prepareStatement(SqlConstants.GET_USER_BALANCE_BY_LOGIN);
        ) {
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            while (rs.next()){
                balance = Optional.of(rs.getBigDecimal("balance"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // todo ex
        } finally {
            close(rs);
        }
        return balance;
    }

    public boolean updateUserBalanceOnConnection(Connection con, String login, BigDecimal newBalance) {
        boolean result = false;
        try {
            con.setAutoCommit(false);
            System.out.println("CONNECTION AUTOCOMMIT = " + con.getAutoCommit());
        } catch (SQLException ex) {  // todo
            ex.printStackTrace();
        }
        try (PreparedStatement prepStmt = con.prepareStatement(SqlConstants.UPDATE_BALANCE_BY_LOGIN)) {
            prepStmt.setBigDecimal(1, newBalance);
            prepStmt.setString(2, login);
            result = prepStmt.executeUpdate() > 0;
        } catch (SQLException | NumberFormatException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e); // todo Ex handler
        }
    }

    private void close(AutoCloseable ac){
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                e.printStackTrace(); // todo ex
            }
        }
    }
}
