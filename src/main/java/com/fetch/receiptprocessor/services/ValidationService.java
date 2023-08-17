package com.fetch.receiptprocessor.services;

import com.fetch.receiptprocessor.Util.Rules;
import com.fetch.receiptprocessor.models.Item;
import com.fetch.receiptprocessor.models.Receipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ValidationService {
    private final Logger logger = LoggerFactory.getLogger(ValidationService.class);
    private boolean validatePurchaseDate(String date) {
        try {
            LocalDate.parse(date, Rules.DATE_FORMATTER);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    private boolean validateNumber(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validatePurchaseTime(String time) {
        if (time.isBlank() || time.isEmpty()) return false;
        return Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]").matcher(time).matches();
    }

    public List<String> validateReceipt(Receipt receipt) {
        logger.info("Validating Receipt");

        List<String> errors = new ArrayList<>();

        if (receipt.getRetailer() == null) {
            errors.add("Invalid retailer name");
        }

        if (!validatePurchaseDate(receipt.getPurchaseDate())) {
            errors.add("Invalid Date! it should be yyyy-mm-dd format");
        }

        if (!validatePurchaseTime(receipt.getPurchaseTime())) {
            errors.add("Invalid Time it should be hh:mm");
        }

        if (receipt.getItems() == null || receipt.getItems().isEmpty()) {
            errors.add("Invalid Items");
        } else {
            for (Item item : receipt.getItems()) {
                if (!validateNumber(item.price())) {
                    errors.add("Invalid Number! item price should be valid number");
                    break;
                }
            }
        }

        if (receipt.getTotal() == null || !validateNumber(receipt.getTotal())) {
            errors.add("Invalid Number! total should be valid number");
        }

        return errors;
    }
}
