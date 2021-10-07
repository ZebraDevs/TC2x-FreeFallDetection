package nl.rkeb.freefalldetection.utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import nl.rkeb.freefalldetection.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

public class SettingsManager {

    // Consts
    private static final String ConfigFileName = "config.json";

    @NonNull
    public static Config LoadConfigFile(Context cx) throws IOException {
        // Create File Instance
        File configFile = new File(cx.getExternalFilesDir(null), ConfigFileName);

        // Create Default File
        if (!configFile.exists()) {
            InputStream is = cx.getResources().getAssets().open(ConfigFileName);
            OutputStream os = new FileOutputStream(configFile);
            byte[] data = new byte[is.available()];
            is.read(data);
            os.write(data);
            is.close();
            os.close();
        }

        // Read JSON File
        return new Gson().fromJson(new BufferedReader(new FileReader(configFile)), Config.class);
    }
}
