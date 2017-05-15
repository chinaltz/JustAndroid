package com.litingzhe.justandroid.netdb.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by litingzhe on 2017/4/7.
 *
 * 箱码实体
 */

@Entity
public class BoxCode {

    @Id(autoincrement = true)
    private Long boxId=null;
    private String boxCode;
    //0 未上传 -1 失败 1 成功
    private  int  isUpload;
    private String   landId;

    private Date createDate;

    @Generated(hash = 810352835)
    public BoxCode(Long boxId, String boxCode, int isUpload, String landId,
            Date createDate) {
        this.boxId = boxId;
        this.boxCode = boxCode;
        this.isUpload = isUpload;
        this.landId = landId;
        this.createDate = createDate;
    }

    @Generated(hash = 1577721088)
    public BoxCode() {
    }

    public Long getBoxId() {
        return this.boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public String getBoxCode() {
        return this.boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public int getIsUpload() {
        return this.isUpload;
    }

    public void setIsUpload(int isUpload) {
        this.isUpload = isUpload;
    }

    public String getLandId() {
        return this.landId;
    }

    public void setLandId(String landId) {
        this.landId = landId;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }



}
