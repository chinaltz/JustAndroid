package com.litingzhe.justandroid.pay.model;

/**
 * Created by litingzhe on 2017/1/3.
 */

public class ChargeModel {


    private  int price;

    private  boolean isSelect;

    //增送金额
    private  int presentation;


    public ChargeModel(int price, boolean isSelect, int presentation) {


        this.price = price;
        this.isSelect = isSelect;
        this.presentation = presentation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getPresentation() {
        return presentation;
    }

    public void setPresentation(int presentation) {
        this.presentation = presentation;
    }
}
