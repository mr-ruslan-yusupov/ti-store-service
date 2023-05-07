package com.telmaneng.tistore.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "ti_part_request_tbl")
public class TiPartRequest {

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String customerEmail;
    @Column(nullable = false)
    private String customerPhoneNumber;
    @Column(nullable = false)
    private String tiPartNumber;
    @Column(nullable = false)
    private boolean isInStock;
    @Column(nullable = false)
    private boolean isNotified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getTiPartNumber() {
        return tiPartNumber;
    }

    public void setTiPartNumber(String tiPartNumber) {
        this.tiPartNumber = tiPartNumber;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean isInStock) {
        this.isInStock = isInStock;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setIsNotified(boolean isNotified) {
        this.isNotified = isNotified;
    }
}
