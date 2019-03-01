package com.weima.aishangyi.entity;

/**
 * 作者：cgy on 17/3/15 00:48
 * 邮箱：593960111@qq.com
 */
public class AnswerDetailResp extends CommonEntity{
    private QuestionBean data;


    public QuestionBean getData() {
        return data;
    }

    public void setData(QuestionBean data) {
        this.data = data;
    }
}
