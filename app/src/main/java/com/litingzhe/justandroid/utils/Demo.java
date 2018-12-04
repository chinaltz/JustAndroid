package com.litingzhe.justandroid.utils;

import java.util.List;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/6/29 上午11:19.
 * 类描述：
 */


public class Demo {


    /**
     * formmodel : {"packagename":"p1","plength":"12","pwidth":"12","pheight":"12","grossweight":"12","newweight":"12"}
     * gridData : [{"itemid":"8bf39ca894fb4032971d3116fa91c24e","itemname":"Valve","qty":"1"},{"itemid":"df7cf379c4b346d3b1f0a22d824f0638","itemname":"Valve seat","qty":"3"}]
     */

    private FormmodelBean formmodel;
    private List<GridDataBean> gridData;

    public FormmodelBean getFormmodel() {
        return formmodel;
    }

    public void setFormmodel(FormmodelBean formmodel) {
        this.formmodel = formmodel;
    }

    public List<GridDataBean> getGridData() {
        return gridData;
    }

    public void setGridData(List<GridDataBean> gridData) {
        this.gridData = gridData;
    }

    public static class FormmodelBean {
        /**
         * packagename : p1
         * plength : 12
         * pwidth : 12
         * pheight : 12
         * grossweight : 12
         * newweight : 12
         */

        private String packagename;
        private String plength;
        private String pwidth;
        private String pheight;
        private String grossweight;
        private String newweight;

        public String getPackagename() {
            return packagename;
        }

        public void setPackagename(String packagename) {
            this.packagename = packagename;
        }

        public String getPlength() {
            return plength;
        }

        public void setPlength(String plength) {
            this.plength = plength;
        }

        public String getPwidth() {
            return pwidth;
        }

        public void setPwidth(String pwidth) {
            this.pwidth = pwidth;
        }

        public String getPheight() {
            return pheight;
        }

        public void setPheight(String pheight) {
            this.pheight = pheight;
        }

        public String getGrossweight() {
            return grossweight;
        }

        public void setGrossweight(String grossweight) {
            this.grossweight = grossweight;
        }

        public String getNewweight() {
            return newweight;
        }

        public void setNewweight(String newweight) {
            this.newweight = newweight;
        }
    }

    public static class GridDataBean {
        /**
         * itemid : 8bf39ca894fb4032971d3116fa91c24e
         * itemname : Valve
         * qty : 1
         */

        private String itemid;
        private String itemname;
        private String qty;

        public String getItemid() {
            return itemid;
        }

        public void setItemid(String itemid) {
            this.itemid = itemid;
        }

        public String getItemname() {
            return itemname;
        }

        public void setItemname(String itemname) {
            this.itemname = itemname;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }
    }
}
