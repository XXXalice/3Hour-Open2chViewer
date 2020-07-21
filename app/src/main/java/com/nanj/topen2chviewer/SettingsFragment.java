package com.nanj.topen2chviewer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.Preference.OnPreferenceClickListener;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.preferences, rootKey);

    // テーマ選択スイッチのListener
    Preference themePreference = findPreference("darkorlight");
    themePreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
      @Override
      public boolean onPreferenceChange(Preference preference, Object newValue) {
        // トーストを出す
        Toast.makeText(getActivity().getApplicationContext(), "次回起動時から有効となります。", Toast.LENGTH_LONG).show();
        return true;
      }
    });

    // 「About」のListener
    Preference aboutPreference = findPreference("about");
    aboutPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
      public boolean onPreferenceClick(Preference preference) {
        // AboutActivityに飛ぶ
        startActivity(new Intent(getActivity().getApplicationContext(), AboutActivity.class));
        return true;
      }
    });

    // 「デフォルトの設定に戻す」のListener
    Preference restoreDefaultPreference = findPreference("restoredefault");
    restoreDefaultPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
      public boolean onPreferenceClick(Preference preference) {
        // デフォルトの設定に戻す
        EditTextPreference homePage = findPreference("homepage");
        homePage.setText("https://open2ch.net/sp/");
        Toast.makeText(getActivity().getApplicationContext(), "デフォルトの設定に戻されました。", Toast.LENGTH_LONG).show();
        return true;
      }
    });
  }
}