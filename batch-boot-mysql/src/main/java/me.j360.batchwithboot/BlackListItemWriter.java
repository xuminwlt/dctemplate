package me.j360.batchwithboot;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Package: me.j360.batchwithboot
 * User: min_xu
 * Date: 16/6/13 下午3:44
 * 说明：
 */
public class BlackListItemWriter implements ItemWriter<BlackListDO> {

    @Override
    public void write(List<? extends BlackListDO> blackList) throws Exception {
        // 插入数据库操作
    }

}
