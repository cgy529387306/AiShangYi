package com.weima.aishangyi.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：cgy on 17/1/3 17:28
 * 邮箱：593960111@qq.com
 */
public class ClassTypeResp extends CommonEntity{

    /**
     * id : 1
     * name : 琴类
     * parent_id : 0
     * is_cover : 1
     * status : 0
     * icon : http://image.cnpp.cn/upload/images/20150521/15191086706_800x758.jpg
     * created_at : 1234567808
     * updated_at : 1234567808
     * children : [{"id":3,"name":"钢琴","parent_id":1,"is_cover":0,"status":0,"icon":"http://image.cnpp.cn/upload/images/20150521/15191086706_800x758.jpg","created_at":"1234567890","updated_at":"1234567890"}]
     */

    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private long id;
        private String name;
        private long parent_id;
        private int is_cover;
        private int status;
        private String icon;
        private String created_at;
        private String updated_at;
        /**
         * id : 3
         * name : 钢琴
         * parent_id : 1
         * is_cover : 0
         * status : 0
         * icon : http://image.cnpp.cn/upload/images/20150521/15191086706_800x758.jpg
         * created_at : 1234567890
         * updated_at : 1234567890
         */

        private List<ChildrenBean> children;

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

        public long getParent_id() {
            return parent_id;
        }

        public void setParent_id(long parent_id) {
            this.parent_id = parent_id;
        }

        public int getIs_cover() {
            return is_cover;
        }

        public void setIs_cover(int is_cover) {
            this.is_cover = is_cover;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean implements Serializable{
            private long id;
            private String name;
            private long parent_id;
            private int is_cover;
            private int status;
            private String icon;
            private String created_at;
            private String updated_at;

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

            public long getParent_id() {
                return parent_id;
            }

            public void setParent_id(long parent_id) {
                this.parent_id = parent_id;
            }

            public int getIs_cover() {
                return is_cover;
            }

            public void setIs_cover(int is_cover) {
                this.is_cover = is_cover;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }
        }
    }

}
