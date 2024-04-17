package com.bankit.pg.razorpay.controller;

import java.math.BigInteger;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/pg")
public class RazorpayController {
	/*
	 * Step 4.) Implement Payment Flow: Implement the payment flow in your
	 * application. This typically involves creating an order, collecting payment
	 * details, and handling the payment response.
	 */

	/*
	 * Step 5.) Handle Webhook Events (Optional): If you need to handle
	 * payment-related events such as successful payments or refunds asynchronously,
	 * you can set up webhook endpoints in your Spring Boot application to receive
	 * these events from Razorpay.
	 */

	private RazorpayClient client;
	// Define your Razorpay API keys
	private static final String SECRET_ID1 = "SECRET_ID1";
	private static final String SECRET_KEY1 = "SECRET_KEY1";
	private static final String SECRET_ID2 = "SECRET_ID2";
	private static final String SECRET_KEY2 = "SECRET_KEY2";

	// Endpoint for creating an order
	@PostMapping(path = "/createOrder")
	public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
		OrderResponse response = new OrderResponse();
		try {
			// Determine which set of Razorpay credentials to use based on the order amount
			if (orderRequest.getAmount().intValue() > 1000) {
				client = new RazorpayClient(SECRET_ID1, SECRET_KEY1);
			} else {
				client = new RazorpayClient(SECRET_ID2, SECRET_KEY2);
			}

			// Create a Razorpay order
			Order order = createRazorPayOrder(orderRequest.getAmount());
			System.out.println("---------------------------");
			String orderId = (String) order.get("id");
			System.out.println("Order ID: " + orderId);
			System.out.println("---------------------------");
			// Set response attributes
			response.setRazorpayOrderId(orderId);
			response.setApplicationFee("" + orderRequest.getAmount());
			if (orderRequest.getAmount().intValue() > 1000) {
				response.setSecretKey(SECRET_KEY1);
				response.setSecretId(SECRET_ID1);
				response.setPgName("razor1");
			} else {
				response.setSecretKey(SECRET_KEY2);
				response.setSecretId(SECRET_ID2);
				response.setPgName("razor2");
			}

			return response;
		} catch (RazorpayException e) {
			// Handle Razorpay exceptions
			e.printStackTrace();
		}

		return response;
	}

	// Method to create a Razorpay order
	private Order createRazorPayOrder(BigInteger amount) throws RazorpayException {

		JSONObject options = new JSONObject();
		options.put("amount", amount.multiply(new BigInteger("100"))); // Razorpay expects amount in paisa
		options.put("currency", "INR");
		options.put("receipt", "txn_123456"); // Unique receipt ID for the transaction
		options.put("payment_capture", 1); // Enable auto-capture of payments
		return client.orders.create(options);
	}
}
