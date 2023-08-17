package com.fetch.receiptprocessor.repository;

import com.fetch.receiptprocessor.models.Receipt;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ReceiptRepository {
    private final Map<String, Receipt> receiptStorage;

    public ReceiptRepository() {
        receiptStorage = new HashMap<>();
    }

    public Map<String, Receipt> getReceiptStorage() {
        return receiptStorage;
    }

    public void save(String id, Receipt receipt) {
        receiptStorage.put(id, receipt);
    }

    public Receipt getReceiptById(String id) {
        return receiptStorage.get(id);
    }

}
