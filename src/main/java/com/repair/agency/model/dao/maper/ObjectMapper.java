package com.repair.agency.model.dao.maper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectMapper<T>{
     T extractFromResultSet(ResultSet rs) throws SQLException;
}
