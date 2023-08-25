package com.landrecord.admob_ads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    Button btnadd1,btnadd2,btnadd3;
    int clk = 0;

    int k=2;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnadd1 = findViewById(R.id.btnadd1);
        btnadd2 = findViewById(R.id.btnadd2);
        btnadd3 = findViewById(R.id.btnadd3);

        btnadd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clk = 1;


                if(count%k==0)
                {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(MainActivity.this);
                    } else {
                        startActivity(new Intent(MainActivity.this, SecondActivity.class));
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }
                }
                else
                {
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                }
                count++;
            }
        });

        btnadd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clk = 2;

                if(count%k==0)
                {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(MainActivity.this);
                    } else {
                        startActivity(new Intent(MainActivity.this, ThirdActivity.class));
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }

                }
                else
                {
                    startActivity(new Intent(MainActivity.this, ThirdActivity.class));
                }
                count++;
            }
        });

        btnadd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clk = 3;

                if(count%k==0)
                {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(MainActivity.this);
                    } else {
                        startActivity(new Intent(MainActivity.this, Button3Activity.class));
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }
                }
                else
                {
                    startActivity(new Intent(MainActivity.this, Button3Activity.class));
                }

                count++;
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();
                Toast.makeText(MainActivity.this, "Add loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                super.onAdClosed();

            }
        });


        loadInterstial();


    }

    private void loadInterstial() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i(TAG, "onAdLoaded");

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.d("TAG", "The ad was dismissed.");
                        loadInterstial();
                        if(clk==1)
                        {
                            startActivity(new Intent(MainActivity.this, SecondActivity.class));
                        }
                        else if(clk==2)
                        {
                            startActivity(new Intent(MainActivity.this, ThirdActivity.class));

                        }
                        else if(clk==3)
                        {
                            startActivity(new Intent(MainActivity.this, Button3Activity.class));

                        }

                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }

        });
    }
}