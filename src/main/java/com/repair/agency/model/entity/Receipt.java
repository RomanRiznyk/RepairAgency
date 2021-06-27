package com.repair.agency.model.entity;

import java.math.BigDecimal;

public class Receipt {
    private int id;
    private String item;
    private String description;
    private BigDecimal price;
    private String feedback;
    private String userLogin;
    private String masterLogin;
    private String status; //todo Enum
    private String createDate;
    private String lastUpdate;

    private int user_id; // todo userId
    private int master_id; // todo masterId

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    /*public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMaster_id() {
        return master_id;
    }

    public void setMaster_id(int master_id) {
        this.master_id = master_id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getMasterLogin() {
        return masterLogin;
    }

    public void setMasterLogin(String masterLogin) {
        this.masterLogin = masterLogin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", feedback='" + feedback + '\'' +
                ", userLogin='" + userLogin + '\'' +
                ", masterLogin='" + masterLogin + '\'' +
                ", status='" + status + '\'' +
                ", user_id=" + user_id +
                ", master_id=" + master_id +
                ", createDate=" + createDate +
                '}';
    }

}
