package com.repair.agency.model.dao.maper;

import com.repair.agency.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User>{
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setBalance(rs.getBigDecimal("balance"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}
