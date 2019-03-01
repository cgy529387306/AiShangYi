package com.weima.aishangyi.entity;

/**
 * 作者：cgy on 17/3/5 17:01
 * 邮箱：593960111@qq.com
 */
public class UpdateResp extends CommonEntity{
    private UpdateBean data;

    public UpdateBean getData() {
        return data;
    }

    public void setData(UpdateBean data) {
        this.data = data;
    }
}
