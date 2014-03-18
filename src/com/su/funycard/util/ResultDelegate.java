package com.su.funycard.util;

import java.io.Serializable;

import android.util.Log;

import com.paypal.android.MEP.PayPalResultDelegate;

public class ResultDelegate implements PayPalResultDelegate, Serializable {

	private String paymentStatus;
	private String paykey = "";

	private static final long serialVersionUID = 10001L;

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaykey() {
		return paykey;
	}

	public void setPaykey(String paykey) {
		this.paykey = paykey;
	}

	/**
	 * Notification that the payment has failed.
	 * 
	 * @param paymentStatus
	 *            the status of the transaction
	 * @param correlationID
	 *            the correlationID for the transaction failure
	 * @param payKey
	 *            the pay key for the payment
	 * @param errorID
	 *            the ID of the error that occurred
	 * @param errorMessage
	 *            the error message for the error that occurred
	 */

	public void onPaymentSucceeded(String payKey, String paymentStatus) {
		this.paymentStatus = paymentStatus;
		this.paykey = payKey;

	}

	public void onPaymentFailed(String paymentStatus, String correlationID,
			String payKey, String errorID, String errorMessage) {
		this.paymentStatus = paymentStatus;
		this.paykey = payKey;

	}

	/**
	 * Notification that the payment was canceled.
	 * 
	 * @param paymentStatus
	 *            the status of the transaction
	 */
	public void onPaymentCanceled(String paymentStatus) {
		this.paymentStatus = paymentStatus;

	}
}
