package demo.mianshiti.com.mianshiti.bean;

/**
 * author xmn
 * on 2017/12/9 14:01
 */

public class RecordBean {
    //记录点击次数
    private int checknum;
    //记录录入的时间
    private int  record;

    public RecordBean(int checknum , int record){
        this.checknum=checknum;
        this.record=record;
    }

    public int getChecknum() {
        return checknum;
    }

    public void setChecknum(int checknum) {
        this.checknum = checknum;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }
}
