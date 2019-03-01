package com.weima.aishangyi.entity;

import java.util.List;

/**
 * 作者：cgy on 17/1/10 00:18
 * 邮箱：593960111@qq.com
 */
public class ClassBean {
    private ItemBean item;

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class ItemBean{
        private long id;
        private String name;
        private String icon;
        private List<LessonBean> lesson;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<LessonBean> getLesson() {
            return lesson;
        }

        public void setLesson(List<LessonBean> lesson) {
            this.lesson = lesson;
        }
    }
}
