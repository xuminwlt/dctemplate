package me.j360.batchwithboot;

/**
 * Package: me.j360.batchwithboot
 * User: min_xu
 * Date: 16/6/13 下午5:55
 * 说明：
 */

public class BlackListDO {

    private String uuid;
    private int deleteFlag;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
