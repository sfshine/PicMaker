package com.su.funycardpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalPayment;
import com.su.funycard.net.UserService;
import com.su.funycard.util.AsynTaskVoid;
import com.su.funycard.util.HttpUtil.MLog;
import com.su.funycard.util.ResultDelegate;
import com.su.funycardpro.R;

public class MyPayPalActivity extends Activity {
	protected static final int INITIALIZE_SUCCESS = 0;
	protected static final int INITIALIZE_FAILURE = 1;
	private static final int server = PayPal.ENV_LIVE;
	private static final String appID = "APP-80W284485P519543T";
	private PayPalPayment palPayment;
	private int what, elecount, designercount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_mypaypal);

		palPayment = (PayPalPayment) this.getIntent().getSerializableExtra(
				"payment");
		what = this.getIntent().getIntExtra("WHAT", 0);
		if (what == 0) {
			elecount = this.getIntent().getIntExtra("elecount", 0);
		} else if (what == 1) {
			designercount = this.getIntent().getIntExtra("designercount", 0);
		}
		Thread libraryInitializationThread = new Thread() {
			public void run() {
				initLibrary();
				if (PayPal.getInstance().isLibraryInitialized()) {
					hRefresh.sendEmptyMessage(INITIALIZE_SUCCESS);
				} else {
					hRefresh.sendEmptyMessage(INITIALIZE_FAILURE);
				}
			}
		};
		libraryInitializationThread.start();
	}

	// private PayPalPayment exampleSimplePayment() {
	//
	// PayPalPayment payment = new PayPalPayment();
	//
	// payment.setCurrencyType("USD");
	// payment.setRecipient("sfshine@paypal.com");
	//
	// payment.setSubtotal(new BigDecimal("8.25"));
	//
	// payment.setPaymentType(PayPal.PAY_TYPE_SIMPLE);
	//
	// payment.setMerchantName("XtickerX");
	// payment.setCustomID("8873482296");
	//
	// payment.setMemo("buy a xtk gift");
	//
	// return payment;
	// }

	public Handler hRefresh = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case INITIALIZE_SUCCESS:

				Intent checkoutIntent = PayPal.getInstance().checkout(
						palPayment, MyPayPalActivity.this, resultDelegate);
				startActivityForResult(checkoutIntent, 1234);
				break;
			case INITIALIZE_FAILURE:

				break;
			}
		}
	};

	ResultDelegate resultDelegate = new ResultDelegate();

	private void initLibrary() {
		PayPal pp = PayPal.getInstance();
		if (pp == null) {
			pp = PayPal.initWithAppID(this, appID, server);
			pp.setLanguage("zh_CN");
			pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);
			pp.setShippingEnabled(false);
			pp.setDynamicAmountCalculationEnabled(false);

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode != 1234) {
			return;
		}

		switch (resultCode) {
		case Activity.RESULT_OK:

			MLog.e(data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY));
			new AsynTaskVoid() {
				protected String doInBackground(Void... params) {
					if (elecount == 0 && designercount == 0) {
						return "";
					}
					if (what == 0) {

						new UserService()
								.addDesignspace(50, designercount + "");

					} else {

						new UserService().addElespace(50, elecount + "");

					}

					return null;
				};

				@Override
				protected void onPostExecute(String result) {
					finish();
					super.onPostExecute(result);
				}
			}.execute();

			// resultTitle = "SUCCESS";
			// resultInfo = "You have successfully completed this "
			// + (isPreapproval ? "preapproval." : "payment.");
			// resultExtra = "Transaction ID: "
			// + data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
			break;
		case Activity.RESULT_CANCELED:
			// resultTitle = "CANCELED";
			// resultInfo = "The transaction has been cancelled.";
			// resultExtra = "";
			Toast.makeText(MyPayPalActivity.this,
					"You have Cancled the payment", 10).show();
			finish();
			break;
		case PayPalActivity.RESULT_FAILURE:
			// resultTitle = "FAILURE";
			// resultInfo = data
			// .getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
			// resultExtra = "Error ID: "
			// + data.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
			Toast.makeText(MyPayPalActivity.this,
					"Oh Something Wrong happened, please contrace us", 10)
					.show();
			finish();
		}

		// finish();
	}
}
