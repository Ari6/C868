package com.ntlts.C868.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Inventory {
    private int itemId;
    private int departmentId;
    private int amount;
    //private LocalDate expire;
    //private LocalDate lastPurchase;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    /*
    public LocalDate getExpire() {
        return expire;
    }

    public void setExpire(LocalDate expire) {
        this.expire = expire;
    }

    public LocalDate getLastPurchase() {
        return lastPurchase;
    }

    public void setLastPurchase(LocalDate lastPurchase) {
        this.lastPurchase = lastPurchase;
    }
    */
}
