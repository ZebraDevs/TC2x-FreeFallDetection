package nl.rkeb.freefalldetection;

import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import android.content.res.AssetManager;
import static android.content.ContentValues.TAG;

public class Config {

    @SerializedName("IntentTextToSpeak")
    @Expose
    private List<IntentTextToSpeak> intentTextToSpeak = null;

    public List<IntentTextToSpeak> getIntentTextToSpeaks() {
        return intentTextToSpeak;
    }

    public void setIntentTextToSpeak(List<IntentTextToSpeak> intentTextToSpeak) {
        this.intentTextToSpeak = intentTextToSpeak;
    }

    public class IntentTextToSpeak {

        @SerializedName("intent")
        @Expose
        private String intent = "";
        @SerializedName("intentExtraString")
        @Expose
        private String intentExtraString = "";
        @SerializedName("TextToSpeak")
        @Expose
        private String textToSPeak = "";
        @SerializedName("seperateCharExtraString")
        @Expose
        private Boolean seperateCharExtraString = false;
        @SerializedName("TTS_enabled")
        @Expose
        private Boolean tTSEnabled = false;

        public String getIntent() {
            return intent;
        }

        public void setIntent(String intent) {
            this.intent = intent;
        }

        public String getTextToSPeak() {
            return textToSPeak;
        }

        public void setTextToSpeak(String textToSPeak) {
            this.textToSPeak = textToSPeak;
        }

        public String getIntentExtraString() {
            return intentExtraString;
        }
        
        public void setIntentExtraString(String intentExtraString) {
            this.intentExtraString = intentExtraString;
        }

        public Boolean getSeperateCharExtraString() {
            return seperateCharExtraString;
        }

        public void setSeperateCharExtraString(Boolean seperateCharExtraString) {
            this.seperateCharExtraString = seperateCharExtraString;
        }

        public Boolean getTTSEnabled() {
            return tTSEnabled;
        }

        public void setTTSEnabled(Boolean tTSEnabled) {
            this.tTSEnabled = tTSEnabled;
        }


    }

}
