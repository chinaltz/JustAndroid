package com.litingzhe.justandroid.ui.mapView.model;

import com.amap.api.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

public  class ParkInfo implements Serializable {
    /**
     * _id : 154
     * _location : 118.8017380839,31.928489837
     * _name : 亚都天元大厦
     * _address : 南京市江宁区天元西路158号
     * parkingCode : DHETC320115001
     * chargeStandard : 5元/小时，最高40元
     * _createtime : 2017-01-04 11:08:32
     * _updatetime : 2017-01-04 11:08:32
     * _province : 江苏省
     * _city : 南京市
     * _district : 江宁区
     * _distance : 884
     * _image : []
     * freeSlotNum : 260
     * parkingSpaces : 260
     */
    private String _id;
    private String _location;
    private String _name;
    private String _address;
    private String parkingCode;
    private String chargeStandard;
    private String _createtime;
    private String _updatetime;
    private String _province;
    private String _city;
    private String _district;
    private String _distance;
    private int freeSlotNum;
    private int parkingSpaces;
    private int  IsPayOnline;

    public int getIsPayOnline() {
        return IsPayOnline;
    }

    public void setIsPayOnline(int isPayOnline) {
        IsPayOnline = isPayOnline;
    }

    private List<String> _image;



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String getParkingCode() {
        return parkingCode;
    }

    public void setParkingCode(String parkingCode) {
        this.parkingCode = parkingCode;
    }

    public String getChargeStandard() {
        return chargeStandard;
    }

    public void setChargeStandard(String chargeStandard) {
        this.chargeStandard = chargeStandard;
    }

    public String get_createtime() {
        return _createtime;
    }

    public void set_createtime(String _createtime) {
        this._createtime = _createtime;
    }

    public String get_updatetime() {
        return _updatetime;
    }

    public void set_updatetime(String _updatetime) {
        this._updatetime = _updatetime;
    }

    public String get_province() {
        return _province;
    }

    public void set_province(String _province) {
        this._province = _province;
    }

    public String get_city() {
        return _city;
    }

    public void set_city(String _city) {
        this._city = _city;
    }

    public String get_district() {
        return _district;
    }

    public void set_district(String _district) {
        this._district = _district;
    }

    public String get_distance() {
        return _distance;
    }

    public void set_distance(String _distance) {
        this._distance = _distance;
    }

    public int getFreeSlotNum() {
        return freeSlotNum;
    }

    public void setFreeSlotNum(int freeSlotNum) {
        this.freeSlotNum = freeSlotNum;
    }

    public int getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(int parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public List<String> get_image() {
        return _image;
    }

    public void set_image(List<String> _image) {
        this._image = _image;
    }
    }