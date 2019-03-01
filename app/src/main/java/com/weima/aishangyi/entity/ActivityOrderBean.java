package com.weima.aishangyi.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
public class ActivityOrderBean implements Serializable{
    private long id;
    private long user_id;
    private int number;
    private String order_no;
    private String price;
    private String amount;
    private String discount;
    private int item;
    private long subject_id;
    private long origin_id;
    private int device;
    private int type;
    private int status;
    private long pay_time;
    private String cancel_time;
    private String teacher_id;
    private int teach_del;
    private int soft_del;
    private String coupon_id;
    private String created_at;
    private String updated_at;
    private ActiveOrderBean active_order;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public long getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(long subject_id) {
        this.subject_id = subject_id;
    }

    public long getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(long origin_id) {
        this.origin_id = origin_id;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
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

    public long getPay_time() {
        return pay_time;
    }

    public void setPay_time(long pay_time) {
        this.pay_time = pay_time;
    }

    public String getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(String cancel_time) {
        this.cancel_time = cancel_time;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getTeach_del() {
        return teach_del;
    }

    public void setTeach_del(int teach_del) {
        this.teach_del = teach_del;
    }

    public int getSoft_del() {
        return soft_del;
    }

    public void setSoft_del(int soft_del) {
        this.soft_del = soft_del;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
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

    public ActiveOrderBean getActive_order() {
        return active_order;
    }

    public void setActive_order(ActiveOrderBean active_order) {
        this.active_order = active_order;
    }

    public static class ActiveOrderBean implements Serializable{

        private long id;
        private long active_id;
        private int number;
        private String order_id;
        private long user_id;
        private String phone;
        private String contacts;
        private String quote;
        private int device;
        private String created_at;
        private String updated_at;
        private ActiveBean active;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getActive_id() {
            return active_id;
        }

        public void setActive_id(long active_id) {
            this.active_id = active_id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }

        public int getDevice() {
            return device;
        }

        public void setDevice(int device) {
            this.device = device;
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

        public ActiveBean getActive() {
            return active;
        }

        public void setActive(ActiveBean active) {
            this.active = active;
        }

        public static class ActiveBean implements Serializable{
            private long id;
            private String title;
            private int number;
            private String address;
            private String price;
            private String url;
            private long start_time;
            private long end_time;
            private long close_time;
            private int status;
            private String content;
            private String created_at;
            private String updated_at;
            private int type;
            private String time_status;
            private String end_status;
            private String has_number;
            private int is_collect;
            private List<String> images;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public long getStart_time() {
                return start_time;
            }

            public void setStart_time(long start_time) {
                this.start_time = start_time;
            }

            public long getEnd_time() {
                return end_time;
            }

            public void setEnd_time(long end_time) {
                this.end_time = end_time;
            }

            public long getClose_time() {
                return close_time;
            }

            public void setClose_time(long close_time) {
                this.close_time = close_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTime_status() {
                return time_status;
            }

            public void setTime_status(String time_status) {
                this.time_status = time_status;
            }

            public String getEnd_status() {
                return end_status;
            }

            public void setEnd_status(String end_status) {
                this.end_status = end_status;
            }

            public String getHas_number() {
                return has_number;
            }

            public void setHas_number(String has_number) {
                this.has_number = has_number;
            }

            public int getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(int is_collect) {
                this.is_collect = is_collect;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }
        }
    }
}
