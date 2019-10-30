package com.ntlts.C868.Models;

public class Item {
    private int itemId;
    private String itemName;
    private int cateogryId;

    public int getCateogryId() {
        return cateogryId;
    }

    public void setCateogryId(int cateogryId) {
        this.cateogryId = cateogryId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
