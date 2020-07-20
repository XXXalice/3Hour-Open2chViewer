package com.nanj.topen2chviewer;

import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    // 設定画面を表示
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.settingscontainer, new SettingsFragment())
        .commit();
  }
  // 設定で選ばれたテーマに切り替える
  @Override
  public Resources.Theme getTheme() {
    Resources.Theme theme = super.getTheme();
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    int setStyle;
    if (sharedPreferences.getBoolean("darkorlight", true)) {
      setStyle = R.style.LightThemeNoActionBar;
    } else {
      setStyle = R.style.DarkThemeNoActionBar;
    }
    theme.applyStyle(setStyle, true);
    return theme;
  }
}