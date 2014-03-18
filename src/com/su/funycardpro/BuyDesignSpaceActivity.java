package com.su.funycardpro;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalPayment;
import com.su.funycardpro.R;

public class BuyDesignSpaceActivity extends Activity {
	private final int WHAT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_buyelespace);
		super.onCreate(savedInstanceState);
	};

	private PayPalPayment getPayment() {
		PayPalPayment payment = new PayPalPayment();
		payment.setCurrencyType("USD");
		payment.setRecipient("sfshine@paypal.com");
		payment.setPaymentType(PayPal.PAY_TYPE_SIMPLE);
		// payment.setSubtotal(new BigDecimal(price));
		payment.setMerchantName("XtickerX");
		payment.setCustomID("8873482296");
		// payment.setMemo("buy a xtk gift");
		return payment;
	}

	public void btn_buy_50(View view) {
		// Intent intent = new Intent(BuyDesignSpaceActivity.this,
		// MyPayPalActivity.class);
		// PayPalPayment palPayment = getPayment();
		// palPayment.setSubtotal(new BigDecimal("0.99"));
		// palPayment.setMemo("Buy 50 uploads");
		// intent.putExtra("payment", palPayment);
		// intent.putExtra("WHAT", WHAT);
		// intent.putExtra("designercount", 50);
		//
		// startActivityForResult(intent, 132);
		String url = "https://www.paypal.com/hk/cgi-bin/webscr?cmd=_flow&SESSION=_GQjl18oXFSPAFh6-hj2bYJMCLa6PFjIlc5D-EgmZmedMObU5b6BK1wHyeu&dispatch=50a222a57771920b6a3d7b606239e4d529b525e0b7e69bf0224adecfb0124e9b61f737ba21b08198ccf805f1b90b660c53f96f39c1a34e95";
		openBrowser(url);

	}

	public void btn_buy_100(View view) {
		// Intent intent = new Intent(BuyDesignSpaceActivity.this,
		// MyPayPalActivity.class);
		// PayPalPayment palPayment = getPayment();
		// palPayment.setSubtotal(new BigDecimal("1.49"));
		// palPayment.setMemo("Buy 100 uploads");
		// intent.putExtra("payment", palPayment);
		// intent.putExtra("WHAT", WHAT);
		// intent.putExtra("designercount", 100);
		// startActivityForResult(intent, 132);
		String url = "https://www.paypal.com/hk/cgi-bin/webscr?cmd=_flow&SESSION=5vWWitQgYHtO3Tfjn5ZusbXwNoSI5JpxHdzspzrg9qH2ax9mposncdtma48&dispatch=50a222a57771920b6a3d7b606239e4d529b525e0b7e69bf0224adecfb0124e9b61f737ba21b08198ccf805f1b90b660c53f96f39c1a34e95";
		openBrowser(url);

	}

	public void btn_buy_200(View view) {
		 Intent intent = new Intent(BuyDesignSpaceActivity.this,
		 MyPayPalActivity.class);
		 PayPalPayment palPayment = getPayment();
		 palPayment.setSubtotal(new BigDecimal("1.99"));
		 palPayment.setMemo("Buy 200 uploads");
		 intent.putExtra("payment", palPayment);
		 intent.putExtra("WHAT", WHAT);
		 intent.putExtra("designercount", 200);
		 startActivityForResult(intent, 132);
		// String url =
		// "https://www.paypal.com/hk/cgi-bin/webscr?cmd=_flow&SESSION=5vWWitQgYHtO3Tfjn5ZusbXwNoSI5JpxHdzspzrg9qH2ax9mposncdtma48&dispatch=50a222a57771920b6a3d7b606239e4d529b525e0b7e69bf0224adecfb0124e9b61f737ba21b08198ccf805f1b90b660c53f96f39c1a34e95";
		// openBrowser(url);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		finish();
	}

	public void openBrowser(String url) {
		Intent viewIntent = new Intent("android.intent.action.VIEW",
				Uri.parse(url));
		startActivity(viewIntent);

	}

}
