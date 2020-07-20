package com.nanj.topen2chviewer;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {

    // 設定で選ばれたテーマに切り替える
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    if (sharedPreferences.getBoolean("darkorlight", true)) {
      getApplicationContext().setTheme(R.style.LightTheme);
    } else {
      getApplicationContext().setTheme(R.style.DarkTheme);
    }

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    // 設定画面を表示
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.settingscontainer, new SettingsFragment())
        .commit();
  }
}