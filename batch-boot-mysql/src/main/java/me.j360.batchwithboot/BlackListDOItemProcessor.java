package me.j360.batchwithboot;

import org.springframework.batch.item.ItemProcessor;

import java.util.UUID;

/**
 * Package: me.j360.batchwithboot
 * User: min_xu
 * Date: 16/6/13 下午3:44
 * 说明：
 */
public class BlackListDOItemProcessor implements ItemProcessor<BlackListDO, BlackListDO> {

    public String inputFile;

    public BlackListDOItemProcessor() {
    }

    public BlackListDOItemProcessor(String inputFile) {
        this.inputFile = inputFile;
    }
    // 数据处理
    public BlackListDO process(BlackListDO blackListDO) throws Exception {
        blackListDO.setDeleteFlag(0);
        blackListDO.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
        return blackListDO;
    }

}
