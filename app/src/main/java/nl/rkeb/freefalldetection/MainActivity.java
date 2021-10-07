package nl.rkeb.freefalldetection;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import nl.rkeb.freefalldetection.utilities.PermissionsHelper;
import nl.rkeb.freefalldetection.utilities.SettingsManager;

public class MainActivity extends AppCompatActivity implements PermissionsHelper.OnPermissionsResultListener {

    // Utility Class
    private PermissionsHelper mPermissionsHelper = null;

    // TTs
    private TextToSpeech mTextToSpeech;
    private boolean mTextToSpeechReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request Permissions
        mPermissionsHelper = new PermissionsHelper(this, this);

        mTextToSpeech = new TextToSpeech(this, status -> mTextToSpeechReady = true);
        mTextToSpeech.setLanguage(Locale.getDefault());

        // Load Config
        try {
            App.mConfig = SettingsManager.LoadConfigFile(this);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Could not load config file", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        IntentFilter freeFallDetectionIntentFilter = new IntentFilter();

        for (Config.IntentTextToSpeak intentTextToSpeak : App.mConfig.getIntentTextToSpeaks()) {
            freeFallDetectionIntentFilter.addAction(String.valueOf(intentTextToSpeak.getIntent()));
        }

        registerReceiver(freeFallDetectionBR, freeFallDetectionIntentFilter);
    }

    BroadcastReceiver freeFallDetectionBR = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String mIntent = intent.getAction();

            for (Config.IntentTextToSpeak intentTextToSpeak : App.mConfig.getIntentTextToSpeaks()) {
                if (mIntent.equalsIgnoreCase(String.valueOf(intentTextToSpeak.getIntent()))) {
                    if (intentTextToSpeak.getTTSEnabled()) {
                        String textToSpeak = String.valueOf(intentTextToSpeak.getTextToSPeak());
                        String extraTextToSpeak = String.valueOf(intentTextToSpeak.getIntentExtraString());

                        Log.i(TAG, String.valueOf(intentTextToSpeak.getIntent()) +  " - " +  textToSpeak + " : " + extraTextToSpeak);
                        playTtsFreeFallDetection(textToSpeak);
                    }
                }
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionsHelper.onActivityResult();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionsHelper.onRequestPermissionsResult();
    }

    @Override
    public void onPermissionsGranted() {

        Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
    }

    public void onClosed() {

        unregisterReceiver(freeFallDetectionBR);
    }

    public void playTtsFreeFallDetection(String textToSpeak) {
        if (mTextToSpeechReady) {
            mTextToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_ADD,
                    null, UUID.randomUUID().toString());
        } else {
            new Handler().postDelayed(() -> playTtsFreeFallDetection(textToSpeak), 1000);
        }
    }
}