package com.amazon.sample.iap.subscription;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.amazon.device.iap.PurchasingService;
import com.amazon.device.iap.model.RequestId;

import java.util.HashSet;
import java.util.Set;

/**
 * Main activity for IAP subscriptions Sample Code
 * 
 * This is the main activity for this project.
 */
public class MainActivity extends Activity implements View.OnClickListener {
	private static final int GOLD_MONTH = 0;
	private static final int GOLD_YEAR = 1;
	private static final int PLATINUM_MONTH = 2;
	private static final int PLATINUM_YEAR = 3;
	private static final int DIAMOND_MONTH = 4;
	private static final int DIAMOND_YEAR = 5;

	private SampleIapManager sampleIapManager;

    /**
     * Setup IAP SDK and other UI related objects specific to this sample
     * application.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupApplicationSpecificOnCreate();
        setupIAPOnCreate();
    }

    /**
     * Setup for IAP SDK called from onCreate. Sets up {@link SampleIapManager}
     * to handle InAppPurchasing logic and {@link SamplePurchasingListener} for
     * listening to IAP API callbacks
     */
    private void setupIAPOnCreate() {
        sampleIapManager = new SampleIapManager(this);
        final SamplePurchasingListener purchasingListener = new SamplePurchasingListener(sampleIapManager);
        Log.d(TAG, "onCreate: registering PurchasingListener");
        PurchasingService.registerListener(this.getApplicationContext(), purchasingListener);

    }

    /**
     * Call {@link PurchasingService#getProductData(Set)} to get the product
     * availability
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: call getProductData for skus: " + MySku.values());
        final Set<String> productSkus = new HashSet<String>();
        for (final MySku mySku : MySku.values()) {
            productSkus.add(mySku.getSku());
        }
        PurchasingService.getProductData(productSkus);
    }

    /**
     * Calls {@link PurchasingService#getUserData()} to get current Amazon
     * user's data and {@link PurchasingService#getPurchaseUpdates(boolean)} to
     * get recent purchase updates
	 * reset - Set to true to get a list of all purchases. Set to false to get a list of all purchases made since the last invocation.
     */
    @Override
    protected void onResume() {
        super.onResume();
        sampleIapManager.activate();
        Log.d(TAG, "onResume: call getUserData");
        PurchasingService.getUserData();
        Log.d(TAG, "onResume: getPurchaseUpdates");
        PurchasingService.getPurchaseUpdates(false); // reset = false

    }

    /**
     * Deactivate Sample IAP manager on main activity's Pause event
     */
    @Override
    protected void onPause() {
        super.onPause();
        sampleIapManager.deactivate();
    }




    public void onBuyMagazineClick(final View view) {

    }

	/**
	 * Click handler invoked when user clicks button to buy magazine
	 * subscription. This method calls
	 * {@link PurchasingService#purchase(String)} with the SKU to initialize the
	 * purchase from Amazon Appstore
	 */
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.goldMonthBtn) {
			final RequestId requestId = PurchasingService.purchase(MySku.GOLD_MONTH_SUBS.getSku());
			Log.d(TAG, "onBuyMagazineClick: requestId (" + requestId + ")");
		} else if (id == R.id.goldYearBtn) {
			final RequestId requestId = PurchasingService.purchase(MySku.GOLD_YEAR_SUBS.getSku());
			Log.d(TAG, "onBuyMagazineClick: requestId (" + requestId + ")");
		} else if (id == R.id.platinumYearBtn) {
			final RequestId requestId = PurchasingService.purchase(MySku.PLATINUM_YEAR_SUBS.getSku());
			Log.d(TAG, "onBuyMagazineClick: requestId (" + requestId + ")");
		} else if (id == R.id.platinumMonthBtn) {
			final RequestId requestId = PurchasingService.purchase(MySku.PLATINUM_MONTH_SUBS.getSku());
			Log.d(TAG, "onBuyMagazineClick: requestId (" + requestId + ")");
		} else if (id == R.id.diamondYearBtn) {
			final RequestId requestId = PurchasingService.purchase(MySku.DIAMOND_MONTH_SUBS.getSku());
			Log.d(TAG, "onBuyMagazineClick: requestId (" + requestId + ")");
		} else if (id == R.id.diamondMonthBtn) {
			final RequestId requestId = PurchasingService.purchase(MySku.DIAMOND_YEAR_SUBS.getSku());
			Log.d(TAG, "onBuyMagazineClick: requestId (" + requestId + ")");
		}

	}

    /**
     * Callback on failed purchase updates response
     *
     * @param requestId
     */
    public void onPurchaseUpdatesResponseFailed(final String requestId) {
        Log.d(TAG, "onPurchaseUpdatesResponseFailed: for requestId (" + requestId + ")");
    }

    // ///////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////// Application specific code below
    // ////////////////////////////
    // ///////////////////////////////////////////////////////////////////////////////////////

    private static final String TAG = "SampleIAPSubscriptionsApp";

    private Handler guiThreadHandler;

    // Button to subscribe magazine
    private Button goldMonthBtn;
    private Button goldYearBtn;
    private Button platinumMonthBtn;
    private Button platinumYearBtn;
    private Button diamondMonthBtn;
    private Button diamondYearBtn;

    // TextView shows whether user is subscribed to magazine
    private TextView goldMonthTxt;
    private TextView goldYearTxt;
    private TextView platinumMonthTxt;
    private TextView platinumYearTxt;
    private TextView diamondMonthTxt;
    private TextView diamondYearTxt;

    /**
     * Setup application specific things, called from onCreate()
     */
    private void setupApplicationSpecificOnCreate() {
        setContentView(R.layout.activity_main);

		goldMonthBtn = (Button) findViewById(R.id.goldMonthBtn);
		goldYearBtn = (Button) findViewById(R.id.goldYearBtn);
		platinumMonthBtn = (Button) findViewById(R.id.platinumMonthBtn);
		platinumYearBtn = (Button) findViewById(R.id.platinumYearBtn);
		diamondMonthBtn = (Button) findViewById(R.id.diamondMonthBtn);
		diamondYearBtn = (Button) findViewById(R.id.diamondYearBtn);

		goldMonthBtn.setOnClickListener(this);
		goldYearBtn.setOnClickListener(this);
		platinumMonthBtn.setOnClickListener(this);
		platinumYearBtn.setOnClickListener(this);
		diamondMonthBtn.setOnClickListener(this);
		diamondYearBtn.setOnClickListener(this);


		goldMonthTxt = (TextView) findViewById(R.id.goldMonthTxt);
		goldYearTxt = (TextView) findViewById(R.id.goldYearTxt);
		platinumMonthTxt = (TextView) findViewById(R.id.platinumMonthTxt);
		platinumYearTxt = (TextView) findViewById(R.id.platinumYearTxt);
		diamondMonthTxt = (TextView) findViewById(R.id.diamondMonthTxt);
		diamondYearTxt = (TextView) findViewById(R.id.diamondYearTxt);

		resetApplication(goldMonthTxt);
		resetApplication(goldYearTxt);
		resetApplication(platinumMonthTxt);
		resetApplication(platinumYearTxt);
		resetApplication(diamondMonthTxt);
		resetApplication(diamondYearTxt);

        guiThreadHandler = new Handler();
    }

    /**
     * Show "Subscription Disabled" text in gray color to indicate user is not
     * subscribed to this magazine initially
     */
    private void resetApplication(TextView planLabelTxt) {
		planLabelTxt.setText(R.string.subscription_disabled);
		planLabelTxt.setTextColor(Color.GRAY);
		planLabelTxt.setBackgroundColor(Color.WHITE);
    }

    /**
     * Disable "Buy Magazine Subscription" button
     */
    private void disableBuyMagazineButton(int code, boolean enable) {
		switch (code) {
			case GOLD_MONTH:
				goldMonthBtn.setEnabled(enable);
				break;
			case GOLD_YEAR:
				goldYearBtn.setEnabled(enable);
				break;
			case PLATINUM_MONTH:
				platinumMonthBtn.setEnabled(enable);
				break;
			case PLATINUM_YEAR:
				platinumYearBtn.setEnabled(enable);
				break;
			case DIAMOND_MONTH:
				diamondMonthBtn.setEnabled(enable);
				break;
			case DIAMOND_YEAR:
				diamondYearBtn.setEnabled(enable);
				break;
		}
    }

    /**
     * Show Subscription as enabled in view
     */
    private void enableMagazineSubscriptionInView(int code,final boolean enable) {
		TextView isSubscriptionEnabled = null;
		switch (code) {
			case GOLD_MONTH:
				isSubscriptionEnabled = goldMonthTxt;
				break;
			case GOLD_YEAR:
				isSubscriptionEnabled = goldYearTxt;
				break;
			case PLATINUM_MONTH:
				isSubscriptionEnabled = platinumMonthTxt;
				break;
			case PLATINUM_YEAR:
				isSubscriptionEnabled = platinumYearTxt;
				break;
			case DIAMOND_MONTH:
				isSubscriptionEnabled = diamondMonthTxt;
				break;
			case DIAMOND_YEAR:
				isSubscriptionEnabled = diamondYearTxt;
				break;
		}

        Log.i(TAG, "enableMagazineSubscriptionInView: enabling magazine subscription, show by setting text color to blue and highlighting");
		final TextView finalIsSubscriptionEnabled = isSubscriptionEnabled;
		guiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
				if (enable) {
					finalIsSubscriptionEnabled.setText(R.string.subscription_enabled);
					finalIsSubscriptionEnabled.setTextColor(Color.BLUE);
					finalIsSubscriptionEnabled.setBackgroundColor(Color.YELLOW);
				} else {
					finalIsSubscriptionEnabled.setText(R.string.subscription_disabled);
					finalIsSubscriptionEnabled.setTextColor(Color.GRAY);
					finalIsSubscriptionEnabled.setBackgroundColor(Color.WHITE);
				}



            }
        });
    }

    /**
     * Show message on UI
     * 
     * @param message
     */
    public void showMessage(final String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Set the magazine subscription button status on UI
     * 
     * @param productAvailable
     * @param userCanSubscribe
     */
    public void setMagazineSubsAvail(final boolean productAvailable, final boolean userCanSubscribe) {
        if (productAvailable) {

			enableMagazineSubscriptionInView(GOLD_MONTH, userCanSubscribe);
			disableBuyMagazineButton(GOLD_MONTH, !userCanSubscribe);
		} else {
			enableMagazineSubscriptionInView(GOLD_MONTH, !userCanSubscribe);
            disableBuyMagazineButton(GOLD_MONTH, userCanSubscribe);
        }

    }


}
