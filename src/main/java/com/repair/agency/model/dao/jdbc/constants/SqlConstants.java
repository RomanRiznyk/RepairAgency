package com.repair.agency.model.dao.jdbc.constants;

public interface SqlConstants { //todo what type of class it should be?
    // --------------- ADMIN SQL STATEMENTS --------------//
    String SET_USERS_WALLET = "UPDATE user SET balance=? WHERE id=?";
    String UPDATE_INVOICE_ENGINEER = "UPDATE receipt SET master_id=? WHERE id=?";
    String UPDATE_INVOICE_PRICE = "UPDATE receipt SET price=? WHERE id=?";
    String UPDATE_RECEIPT_STATUS = "UPDATE receipt SET status=? WHERE id=?";
    String SELECT_RECEIPTS_BY_STATUS = "SELECT * FROM receipt WHERE status=?";
    String SELECT_RECEIPTS_BY_STATUS_WITH_USER_MASTER_LOGINS = "SELECT receipt.id, item, description, price, feedback, user_id, customer.login, master_id, masters.login, status, receipt.create_time, update_time\n" +
            "FROM receipt LEFT JOIN user AS customer\n" +
            "ON receipt.user_id = customer.id \n" +
            "LEFT JOIN user AS masters\n" +
            "ON receipt.master_id = masters.id\n" +
            "WHERE receipt.status = ?;";

    /*String FINED_INVOICE_BY_ID = "SELECT * FROM user inner JOIN receipt " +
            "ON receipt.id = ? and user.id = receipt.user_id or receipt.id = ? and user.id = receipt.master_id;";*/
    String FINED_INVOICE_BY_ID = "SELECT * FROM receipt WHERE receipt.id = ?;";
    String SELECT_ALL_RECEIPTS_WITH_MASTER_USER_LOGINS = "SELECT receipt.id, item, description, price, feedback, user_id, customer.login, master_id, masters.login, status, receipt.create_time, update_time\n" +
            "FROM receipt LEFT JOIN user AS customer\n" +
            "ON receipt.user_id = customer.id \n" +
            "LEFT JOIN user AS masters\n" +
            "ON receipt.master_id = masters.id;";
    String SELECT_ALL_USERS = "SELECT * FROM user WHERE role='USER'";
    // --------------- ENGINEER SQL STATEMENTS --------------//
    String GET_ENGINEERS = "SELECT * FROM user WHERE role = 'MASTER'";
    String GET_MASTER_INVOICES_BY_EMAIL = "SELECT * FROM receipt WHERE master_id IN(SELECT id FROM user WHERE email=?)";
    // --------------- USER SQL STATEMENTS --------------//
    String SELECT_INVOICES_BY_EMAIL = "SELECT * FROM receipt WHERE user_id IN (SELECT id FROM user WHERE email=?)"; //todo login instead email
    String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";
    String GET_ENGINEER_BY_ID = "SELECT login FROM user WHERE id=?";
    String INSERT_NEW_RECEIPT = "INSERT INTO receipt (user_id, item, description)\n" +
                                "VALUES ((SELECT id FROM user WHERE login=?), ?, ?)";
    String INSERT_NEW_RECEIPT_WITH_MASTER = "INSERT INTO receipt (user_id, item, description, master_id)\n" +
            "VALUES ((SELECT id FROM user WHERE login=?), ?, ?, (SELECT id FROM user WHERE login=?))";

    String INSERT_FEEDBACK = "UPDATE receipt SET feedback=? WHERE id=?";
    String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?"; // todo get or find is better to name?
    String UPDATE_BALANCE_BY_LOGIN = "UPDATE user SET balance=? WHERE login=?";
    String GET_USER = "SELECT * FROM user WHERE email = ? and password = ?";
    String INSERT_USER = "INSERT INTO user (login, password) VALUES (?, ?)";  //todo login password only and balance
    String INSERT_USER_WITH_MAIL = "INSERT INTO user (login, password, email) VALUES (?, ?, ?)";  //todo login password only and balance

    String FIND_RECEIPT_BY_ID_WITH_USER_MASTER_LOGINS = "SELECT receipt.id, item, description, price, feedback, user_id, customer.login, master_id, masters.login, status, receipt.create_time, update_time\n" +
            "FROM receipt LEFT JOIN user AS customer\n" +
            "ON receipt.user_id = customer.id \n" +
            "LEFT JOIN user AS masters\n" +
            "ON receipt.master_id = masters.id\n" +
            "WHERE receipt.id = ?;";
    String GET_MASTER_RECEIPTS_BY_LOGIN = "SELECT receipt.id, item, description, price, feedback, user_id, customer.login, master_id, masters.login, status, receipt.create_time, update_time\n" +
            "FROM receipt LEFT JOIN user AS customer\n" +
            "ON receipt.user_id = customer.id \n" +
            "LEFT JOIN user AS masters\n" +
            "ON receipt.master_id = masters.id\n" +
            "WHERE masters.login =?"; // todo understand this query IN...todo User and Master logins instead ids
    String GET_USER_BALANCE_BY_LOGIN = "SELECT balance FROM user WHERE login=?";
    String GET_USER_BALANCE = "SELECT balance FROM user WHERE login=?";
    String GET_USER_RECEIPTS_BY_LOGIN = "SELECT receipt.id, item, description, price, feedback, user_id, customer.login, master_id, masters.login, status, receipt.create_time, update_time\n" +
            "FROM receipt LEFT JOIN user AS customer\n" +
            "ON receipt.user_id = customer.id \n" +
            "LEFT JOIN user AS masters\n" +
            "ON receipt.master_id = masters.id\n" +
            "WHERE customer.login =?";
    String GET_USER_LOGIN_BY_RECEIPT_ID = "SELECT login FROM user WHERE user.id IN(SELECT user_id FROM receipt WHERE id = ?);";
    String GET_RECEIPT_PRICE_BY_ID = "SELECT price FROM receipt WHERE id = ?";
    String GET_FEEDBACK_BY_ID = "SELECT feedback FROM receipt WHERE id=?";
    String SELECT_ALL_RECEIPTS_WITH_MASTER_USER_LOGINS_BY_PRICE = "SELECT receipt.id, item, description, price, feedback, user_id, customer.login, master_id, masters.login, status, receipt.create_time, update_time\n" +
            "FROM receipt LEFT JOIN user AS customer\n" +
            "ON receipt.user_id = customer.id \n" +
            "LEFT JOIN user AS masters\n" +
            "ON receipt.master_id = masters.id\n" +
            "ORDER BY price ";
    String SELECT_ALL_RECEIPTS_WITH_MASTER_USER_LOGINS_BY_DATE = "SELECT receipt.id, item, description, price, feedback, user_id, customer.login, master_id, masters.login, status, receipt.create_time, update_time\n" +
            "FROM receipt LEFT JOIN user AS customer\n" +
            "ON receipt.user_id = customer.id \n" +
            "LEFT JOIN user AS masters\n" +
            "ON receipt.master_id = masters.id\n" +
            "ORDER BY receipt.create_time ";
    String SELECT_ALL_RECEIPTS_WITH_MASTER_USER_LOGINS_BY_STATUS = "SELECT receipt.id, item, description, price, feedback, user_id, customer.login, master_id, masters.login, status, receipt.create_time, update_time\n" +
            "FROM receipt LEFT JOIN user AS customer\n" +
            "ON receipt.user_id = customer.id \n" +
            "LEFT JOIN user AS masters\n" +
            "ON receipt.master_id = masters.id\n" +
            "ORDER BY status ";
}
