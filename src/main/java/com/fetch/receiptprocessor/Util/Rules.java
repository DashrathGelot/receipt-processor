package com.fetch.receiptprocessor.Util;

import com.fetch.receiptprocessor.models.Item;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class Rules {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static Rules instance;
    private Rules() {}

    public static Rules getInstance() {
        if (instance == null) {
            instance = new Rules();
        }
        return instance;
    }

    //Rule 1: One point for every alphanumeric in retailer name
    public long alphaNumeric(String retailerName) {
        return retailerName.replaceAll("[^a-zA-Z0-9]", "").length();
    }

    //Rule 2: 50 Points if total is round
    public boolean isTotalRound(double total) {
        return Math.round(total) == total;
    }

    //Rule 3: 25 Points if total is multiple of 0.25
    public boolean isTotalMultiple(double total) {
        return total % 0.25 == 0;
    }

    //Rule 4: 5 Points for every two items on the receipt
    public long rule4Items(List<Item> items) {
        return (items.size()/2L) * 5;
    }

    //Rule 5: If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
    public long rule5TrimDesc(List<Item> items) {
        long points = 0;
        for (Item item : items) {
            long descLen = item.shortDescription().trim().length();
            if (descLen % 3 == 0) {
                points += (long) Math.ceil(Double.parseDouble(item.price()) * 0.2);
            }
        }
        return points;
    }

    //Rule 6: 6 points if the day in the purchase date is odd.
    public boolean isPurchaseDateOdd(String purchaseDate) {
        return LocalDate.parse(purchaseDate, Rules.DATE_FORMATTER).getDayOfMonth() % 2 != 0;
    }

    //Rule 7: 10 points if the time of purchase is after 2:00pm and before 4:00pm.
    public boolean isTime2to4(String purchaseTime) {
        int hour = Integer.parseInt(purchaseTime.split(":")[0]);
        return hour >= 14 && hour <= 16;
    }
}
