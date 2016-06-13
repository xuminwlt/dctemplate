package me.j360.batchwithboot;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Package: me.j360.batchwithboot
 * User: min_xu
 * Date: 16/6/13 下午6:26
 * 说明：
*/
public class BeanDtoMapper implements RowMapper<BlackListDO> {

    public BeanDtoMapper() {
        super();
    }

    @Override
    public BlackListDO mapRow(ResultSet resultSet, int i) throws SQLException {
        BlackListDO dto = new BlackListDO();
        return dto;
    }
}
