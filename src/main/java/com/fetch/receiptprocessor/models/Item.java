package com.fetch.receiptprocessor.models;

public record Item(String shortDescription, String price) {
    @Override
    public String toString() {
        return "Item{" +
                "shortDescription='" + shortDescription + '\'' +
                ", price=" + price +
                '}';
    }
}

