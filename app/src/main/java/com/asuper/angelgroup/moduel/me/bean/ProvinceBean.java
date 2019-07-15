package com.asuper.angelgroup.moduel.me.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by shubei on 2017/11/27.
 */

public class ProvinceBean  implements IPickerViewData{


    /**
     * hasChild : 9
     * id : 8
     * provinceName : 吉林省
     */

    private int hasChild;
    private int id;
    private String provinceName;

    public ProvinceBean() {
    }

    public ProvinceBean(int id, String provinceName) {
        this.id = id;
        this.provinceName = provinceName;
    }

    public int getHasChild() {
        return hasChild;
    }

    public void setHasChild(int hasChild) {
        this.hasChild = hasChild;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String getPickerViewText() {
        return provinceName;
    }
}
