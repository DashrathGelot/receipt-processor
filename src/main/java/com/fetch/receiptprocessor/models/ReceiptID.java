package com.fetch.receiptprocessor.models;

public record ReceiptID(String id) {
    @Override
    public String toString() {
        return "ReceiptID{" +
                "id='" + id + '\'' +
                '}';
    }
}
