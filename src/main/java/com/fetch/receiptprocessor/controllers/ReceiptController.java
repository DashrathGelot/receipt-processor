package com.fetch.receiptprocessor.controllers;

import com.fetch.receiptprocessor.models.Points;
import com.fetch.receiptprocessor.models.Receipt;
import com.fetch.receiptprocessor.models.ReceiptID;
import com.fetch.receiptprocessor.services.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    private final Logger logger = LoggerFactory.getLogger(ReceiptController.class);

    @GetMapping("/{id}/points")
    public Points getPoints(@PathVariable String id) {
        try {
            long points = receiptService.getPoints(id);
            return new Points(String.valueOf(points).split("\\.")[0]);
        } catch (Exception e) {
            logger.error("Getting points make error for id : {} , message: {}", id, e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/process")
    public ReceiptID processReceipts(@RequestBody Receipt receipt) {
        try {
            String uniqueId = receiptService.processReceipt(receipt);
            return new ReceiptID(uniqueId);
        } catch (Exception e) {
            logger.error("Failed to process receipts, message: {}", e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("The receipt is invalid, Message: %s", e.getMessage()),
                    e
            );
        }
    }

}

