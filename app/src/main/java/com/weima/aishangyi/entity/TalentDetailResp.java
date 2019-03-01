package com.weima.aishangyi.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class TalentDetailResp extends CommonEntity{
    private DataBean data;

    public static class DataBean{
        private long id;
        private String content;
        private int thumb_up;
        private long user_id;
        private int type;
        private int status;
        private String created_at;
        private String updated_at;
        private int comment_count;
        private User user;
        private Comment comment;
        private List<String> images;
        private ThumbBean is_thumb;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getThumb_up() {
            return thumb_up;
        }

        public void setThumb_up(int thumb_up) {
            this.thumb_up = thumb_up;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public Comment getComment() {
            return comment;
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public ThumbBean getIs_thumb() {
            return is_thumb;
        }

        public void setIs_thumb(ThumbBean is_thumb) {
            this.is_thumb = is_thumb;
        }

        public static class Comment extends CommonListData{
            private List<CommentBean> data;

            public List<CommentBean> getData() {
                return data;
            }

            public void setData(List<CommentBean> data) {
                this.data = data;
            }
        }

        public static class User implements Serializable {
            private long id;
            private String nickname;
            private String username;
            private String icon;
            private String phone;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
