package com.litingzhe.justandroid.ui.keyBoard.model;

public class Car {
    /**
     * CarNumber : 苏A138J9
     * BoundStatus : 0
     * ID : 0ef020d03d7540659c42d0b0575fe8fc
     */

    private String CarNumber;
    private int BoundStatus;
    private String ID;

    // 0 是还未申诉  1审核成功   9正在审核
    private Integer carStatus;

    public Integer getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarNumber() {
        return CarNumber;
    }

    public void setCarNumber(String CarNumber) {
        this.CarNumber = CarNumber;
    }

    public int getBoundStatus() {
        return BoundStatus;
    }

    public void setBoundStatus(int BoundStatus) {
        this.BoundStatus = BoundStatus;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


}
