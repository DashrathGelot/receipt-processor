package com.fetch.receiptprocessor.models;

public record Points(String points) {
    @Override
    public String toString() {
        return "Points{" +
                "points=" + points +
                '}';
    }
}
