package me.j360.bootnative.mapper;

import me.j360.bootnative.dto.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Package: me.j360.bootnative.mapper
 * User: min_xu
 * Date: 16/6/16 下午8:16
 * 说明：
 */

public class CustomerCreditRowMapper implements RowMapper {

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt(ID_COLUMN));
        user.setName(rs.getString(NAME_COLUMN));

        return user;
    }
}
