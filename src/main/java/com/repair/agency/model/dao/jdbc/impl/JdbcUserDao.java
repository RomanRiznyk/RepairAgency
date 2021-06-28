package com.repair.agency.model.dao.jdbc.impl;

import com.repair.agency.model.dao.jdbc.constants.SqlConstants;
import com.repair.agency.model.dao.UserDao;
import com.repair.agency.model.dao.maper.ReceiptMapper;
import com.repair.agency.model.dao.maper.UserMapper;
import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcUserDao implements UserDao {
    private static final Logger logger = LogManager.getLogger(JdbcUserDao.class.getName());
    private final Connection connection;
    ReceiptMapper receiptMapper = new ReceiptMapper();

    public JdbcUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        ResultSet rs = null;
        UserMapper mapper = new UserMapper();  // todo DI
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.GET_USER_BY_LOGIN)) {
            prepStmt.setString(1, login);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                return Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            close(rs);
        }
        return Optional.empty();
    }

    public boolean insertUser(String email, String login, String password) {
        if (email == null || login == null || password == null) {
            return false;
        }
        boolean isSuccess = false;
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.INSERT_USER)) {
            prepStmt.setString(1, email);
            prepStmt.setString(2, login);
            prepStmt.setString(3, password);
            isSuccess = prepStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    @Override
    public boolean createReceipt(String login, String item, String description) {
        boolean isSucces = false;
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.INSERT_NEW_RECEIPT)) {
            logger.info("JDBCUserDao -> createReceipt insert starts ");
            prepStmt.setString(1, login);
            prepStmt.setString(2, item);
            prepStmt.setString(3, description); // todo handle if descr is null???
            isSucces = prepStmt.executeUpdate() > 0;
            logger.info("JDBCUserDao -> createReceipt insert: " + isSucces);

        } catch (SQLException e) {
            logger.error(e.getMessage()); //todo ex handling in every method here
        }
        return isSucces;
    }

    @Override
    public boolean createReceiptWithMaster(String login, String item, String description, String masterLogin) {
        boolean isSuccess = false;
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.INSERT_NEW_RECEIPT_WITH_MASTER)) {
            prepStmt.setString(1, login);
            prepStmt.setString(2, item);
            prepStmt.setString(3, description);
            prepStmt.setString(4, masterLogin);
            isSuccess = prepStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    @Override
    public Optional<User> createUser(String login, String password) {
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.INSERT_USER)) {
            prepStmt.setString(1, login);
            prepStmt.setString(2, password);
            prepStmt.executeUpdate();
            return getUserByLogin(login);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> createUserWithMail(String login, String password, String email) {
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.INSERT_USER_WITH_MAIL)) {
            prepStmt.setString(1, login);
            prepStmt.setString(2, password);
            prepStmt.setString(3, email);
            prepStmt.executeUpdate();
            return getUserByLogin(login);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    private String getMasterById(int id) throws SQLException { //  todo rename  ex handle
        String name = "";
        ResultSet rs = null;
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.GET_ENGINEER_BY_ID)){
            prepStmt.setInt(1, id);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                name = rs.getString("login");
            }
        } finally {
            close(rs);
        }
        return name;
    }

    @Override
    public boolean addFeedback(int id, String feedback) {
        boolean isSuccess = false;
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.INSERT_FEEDBACK)) {
            prepStmt.setString(1, feedback);
            prepStmt.setInt(2, id);
            isSuccess = prepStmt.executeUpdate() > 0;
        } catch (SQLException /*| NumberFormatException*/ e) { // todo NFE remove
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    @Override
    public boolean updateUserBalance(String login, BigDecimal newBalance) {
        boolean isSuccess = false;
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.UPDATE_BALANCE_BY_LOGIN)) {
            prepStmt.setBigDecimal(1, newBalance);
            prepStmt.setString(2, login);
            isSuccess = prepStmt.executeUpdate() > 0;
        } catch (SQLException | NumberFormatException e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    @Override
    public BigDecimal getBalance(String login) {
        BigDecimal balance = null;
        ResultSet rs = null;
        try (Connection con = connection;
                PreparedStatement pstmt = con.prepareStatement(SqlConstants.GET_USER_BALANCE)){
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                balance = rs.getBigDecimal("balance");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(rs);
            close(connection);
        }
        return balance;
    }

    @Override
    public List<Receipt> getReceiptsByLogin(String login) {
        List<Receipt> receiptList = new ArrayList<>();
        ResultSet rs = null;
        try (Connection con = connection;
             PreparedStatement prepStmt = con.prepareStatement(SqlConstants.GET_USER_RECEIPTS_BY_LOGIN)) {
            prepStmt.setString(1, login);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                Receipt receipt = receiptMapper.extractFromResultSet(rs);
                receipt.setUserLogin(login);
                receipt.setMasterLogin(rs.getString("masters.login"));
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
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void close(AutoCloseable ac) { // todo duplicate close?
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                logger.debug("Can't close Autocloseable", e);
                e.printStackTrace();
            }
        }
    }
}
