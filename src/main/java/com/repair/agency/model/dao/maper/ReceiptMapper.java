package com.repair.agency.model.dao.maper;

import com.repair.agency.model.entity.Receipt;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReceiptMapper implements ObjectMapper<Receipt>{
    @Override
    public Receipt extractFromResultSet(ResultSet rs) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setId(rs.getInt("id"));
        receipt.setItem(rs.getString("item"));
        receipt.setDescription(rs.getString("description"));
        receipt.setPrice(rs.getBigDecimal("price"));
        receipt.setFeedback(rs.getString("feedback"));
        receipt.setUser_id(rs.getInt("user_id"));  // todo rename userId
        receipt.setMaster_id(rs.getInt("master_id")); // todo rename masterId
        receipt.setStatus(rs.getString("status"));
        receipt.setCreateDate(rs.getString("create_time"));
        return receipt;
    }
}
