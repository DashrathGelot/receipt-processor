package com.fetch.receiptprocessor;

import com.fetch.receiptprocessor.models.Item;
import com.fetch.receiptprocessor.models.Receipt;
import com.fetch.receiptprocessor.repository.ReceiptRepository;
import com.fetch.receiptprocessor.services.ReceiptService;
import com.fetch.receiptprocessor.services.ValidationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReceiptprocessorApplicationTests {

	private static ReceiptService receiptService;
	private final Logger logger = LoggerFactory.getLogger(ReceiptService.class);

	private String processReceipt(Receipt receipt) {
		try {
			return receiptService.processReceipt(receipt);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	private Receipt getReceipt() {
		List<Item> items = new ArrayList<>(
				List.of(new Item("Mountain Dew 12PK", "6.49"),
						new Item("Emils Cheese Pizza", "12.25"),
						new Item("Knorr Creamy Chicken", "1.26"),
						new Item("Doritos Nacho Cheese", "3.35"),
						new Item("   Klarbrunn 12-PK 12 FL OZ  ", "12.00"))
		);

		return new Receipt("Target", "2022-01-01", "13:01", "35.35", items);
	}

	static ReceiptRepository receiptRepository = new ReceiptRepository();

	static ValidationService validationService = new ValidationService();

	@BeforeAll
	static void setRepo() {
		receiptService = new ReceiptService(receiptRepository, validationService);
	}

	@Test
	@DisplayName("Process given receipt and generate unique ID")
	void processReceipt() {
		String uID = processReceipt(getReceipt());
		assertNotNull(uID);
	}

	@Test
	@DisplayName("Calculate and get points by given unique id")
	void getPoints() {
		String uID = processReceipt(getReceipt());
		assertEquals(28L, receiptService.getPoints(uID));
	}

}
