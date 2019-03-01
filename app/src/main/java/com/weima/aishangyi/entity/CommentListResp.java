package com.weima.aishangyi.entity;

/**
 * Created by Administrator on 2017/2/7 0007.
 */
public class CommentListResp extends CommonEntity{
    private TalentDetailResp.DataBean.Comment data;

    public TalentDetailResp.DataBean.Comment getData() {
        return data;
    }

    public void setData(TalentDetailResp.DataBean.Comment data) {
        this.data = data;
    }
}
