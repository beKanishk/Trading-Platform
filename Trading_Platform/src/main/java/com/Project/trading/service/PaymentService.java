package com.Project.trading.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Project.trading.domain.PaymentMethod;
import com.Project.trading.domain.PaymentOrderStatus;
import com.Project.trading.model.PaymentOrder;
import com.Project.trading.model.User;
import com.Project.trading.repository.PaymentOrderRepository;
import com.Project.trading.response.PaymentResponse;
import com.Project.trading.serviceInterface.PaymentServiceInterface;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class PaymentService implements PaymentServiceInterface{
	@Autowired
	private PaymentOrderRepository paymentOrderRepository;
	
	@Value("${stripe.api.key}")
	private String stripeSecretKey;
	
	@Value("${razorpay.api.key}")
	private String razorpaySecretKey;
	
	@Value("${razorpay.api.secret}")
	private String apiSecretKey;
	
	@Override
	public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) {
		PaymentOrder paymentOrder = new PaymentOrder();
		
		paymentOrder.setAmount(amount);
		paymentOrder.setUser(user);
		paymentOrder.setPaymentMethod(paymentMethod);
		paymentOrder.setStatus(PaymentOrderStatus.PENDING);
		
		return paymentOrderRepository.save(paymentOrder);
	}

	@Override
	public PaymentOrder getPaymentOrderById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return paymentOrderRepository.findById(id).orElseThrow(() -> new Exception("payment order not found"));
	}

	@Override
	public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
		if(paymentOrder.getStatus() == null) {
			paymentOrder.setStatus(PaymentOrderStatus.PENDING);
		}
		
		if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {
			if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)) {
				RazorpayClient razorpay = new RazorpayClient(razorpaySecretKey, apiSecretKey);
				Payment payment = razorpay.payments.fetch(paymentId);
				
				Integer amount = payment.get("amount");
				String status = payment.get("status");
				
				if(status.equals("captured")) {
					paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
					return true;
				}
				paymentOrder.setStatus(PaymentOrderStatus.FAILED);
				paymentOrderRepository.save(paymentOrder);
				return false;
			}
			paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
			paymentOrderRepository.save(paymentOrder);
			return true;
		}
		return false;
	}

	@Override
	public PaymentResponse createRazorpayPaymentLink(User user, Long amount, Long orderId) throws RazorpayException {
		Long Amount = amount * 100;
		
		try {
			RazorpayClient razorpay = new RazorpayClient(razorpaySecretKey, apiSecretKey);
			
			JSONObject paymentLinkRequest = new JSONObject();
			paymentLinkRequest.put("amount", amount);
			paymentLinkRequest.put("currency", "INR");
			
			JSONObject customer = new JSONObject();
			customer.put("email", user.getEmail());
			paymentLinkRequest.put("customer", customer);
			
			JSONObject notify = new JSONObject();
			notify.put("email", true);
			paymentLinkRequest.put("notify", notify);
			
			paymentLinkRequest.put("reminder_enable", true);
			
			paymentLinkRequest.put("callback_url", "http://localhost:5173/wallet?order_id=" + orderId);
			paymentLinkRequest.put("callback_method", "get");
			
			PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
			
			String paymentLinkId = payment.get("id");
			String paymentLinkUrl = payment.get("short_url");
			
			PaymentResponse response = new PaymentResponse();
			response.setPayment_url(paymentLinkUrl);
			
			return response;
			
		} catch (Exception e) {
			System.out.println("Error generating link: " + e.getMessage());
			throw new RazorpayException(e.getMessage());
		}
	}

	@Override
	public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws Exception {
		try {
            Stripe.apiKey = stripeSecretKey;
            SessionCreateParams params = SessionCreateParams.builder()
            	.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://localhost:5173/wallet?order_id="+orderId)
                .setCancelUrl("https://localhost:5173/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("usd")
                        .setUnitAmount(amount * 100)
                        .setProductData(SessionCreateParams
                        		.LineItem
                        		.PriceData
                        		.ProductData
                        		.builder()
                            .setName("Top up wallet")
                            .build())
                        .build())
                    .build())
                .build();
            
            Session session = Session.create(params);
            
            System.out.println("session_____" + session);
            
            PaymentResponse response = new PaymentResponse();
            response.setPayment_url(session.getUrl());
         
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to create Stripe payment link.");
        }
	}

}
