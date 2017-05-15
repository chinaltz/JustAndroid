package com.litingzhe.justandroid.netdb.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by litingzhe on 2017/4/7.
 *
 * 地块 实体
 */

@Entity
public class Land {

    @Id(autoincrement = true)
    private Long landId=null;
    private String LandCode;
    private String landName;
    @Generated(hash = 1791735910)
    public Land(Long landId, String LandCode, String landName) {
        this.landId = landId;
        this.LandCode = LandCode;
        this.landName = landName;
    }
    @Generated(hash = 1574330841)
    public Land() {
    }
    public Long getLandId() {
        return this.landId;
    }
    public void setLandId(Long landId) {
        this.landId = landId;
    }
    public String getLandCode() {
        return this.LandCode;
    }
    public void setLandCode(String LandCode) {
        this.LandCode = LandCode;
    }
    public String getLandName() {
        return this.landName;
    }
    public void setLandName(String landName) {
        this.landName = landName;
    }



}
