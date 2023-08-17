package com.fetch.receiptprocessor.services;

import com.fetch.receiptprocessor.Util.Rules;
import com.fetch.receiptprocessor.models.Receipt;
import com.fetch.receiptprocessor.repository.ReceiptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReceiptService {
    private final Logger logger = LoggerFactory.getLogger(ReceiptService.class);

    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private ValidationService validationService;

    public ReceiptService(ReceiptRepository receiptRepository, ValidationService validationService) {
        this.receiptRepository = receiptRepository;
        this.validationService = validationService;
    }

    private String generateUniqueId() {
        String uniqueId = UUID.randomUUID().toString();
        logger.info("Generated Unique ID: {}", uniqueId);
        return uniqueId;
    }
    public String processReceipt(Receipt receipt) throws Exception {
        // validate receipt all values
        List<String> errors = validationService.validateReceipt(receipt);
        if (!errors.isEmpty()) {
            String errorMessages = String.join(", ", errors);
            throw new Exception(errorMessages);
        }

        String uniqueId = generateUniqueId();
        long points = 0;
        Rules rules = Rules.getInstance();

        //Rule 1
        points += rules.alphaNumeric(receipt.getRetailer());

        //Rule 2
        if (rules.isTotalRound(Double.parseDouble(receipt.getTotal()))) {
            points += 50;
        }

        //Rule 3
        if (rules.isTotalMultiple(Double.parseDouble(receipt.getTotal()))) {
            points += 25;
        }

        //Rule 4
        points += rules.rule4Items(receipt.getItems());

        //Rule 5
        points += rules.rule5TrimDesc(receipt.getItems());

        //Rule 6
        if (rules.isPurchaseDateOdd(receipt.getPurchaseDate())) {
            points += 6;
        }

        //Rule 7
        if (rules.isTime2to4(receipt.getPurchaseTime())) {
            points += 10;
        }

        receipt.setPoints(points);
        receiptRepository.save(uniqueId, receipt);

        return uniqueId;
    }

    public long getPoints(String id) {
        Receipt receipt = receiptRepository.getReceiptById(id);
        if (receipt == null) {
            throw new RuntimeException("No receipt found for that id");
        }
        long points = receipt.getPoints();
        logger.info("Calculated points for id: {} is: {}", id, points);
        return points;
    }

}

