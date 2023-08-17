package com.fetch.receiptprocessor.models;

import java.util.List;

public class Receipt {
    private final String retailer;
    private final String purchaseDate;
    private final String purchaseTime;
    private final String total;
    private final List<Item> items;

    private long points;

    public Receipt(String retailer, String purchaseDate, String purchaseTime, String total, List<Item> items) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.total = total;
        this.items = items;
    }

    public String getRetailer() {
        return retailer;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public String getTotal() {
        return total;
    }

    public List<Item> getItems() {
        return items;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "retailer='" + retailer + '\'' +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", purchaseTime='" + purchaseTime + '\'' +
                ", total='" + total + '\'' +
                ", items=" + items +
                ", points=" + points +
                '}';
    }
}

