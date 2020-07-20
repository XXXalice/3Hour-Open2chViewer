package com.nanj.topen2chviewer;

import android.content.Intent;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceClickListener;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.preferences, rootKey);

    // テーマ選択スイッチのListener
    Preference themePreference = findPreference("darkorlight");
    themePreference.setOnPreferenceChangeListener(new OnPreferenceClickListener() {
      public boolean onPreferenceClick(Preference preference) {
        // アクティビティを再起動する
        finish();
        startActivity(new Intent(getActivity().getApplicationContext(), getActivity().getApplicationContext()));
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
  }
}