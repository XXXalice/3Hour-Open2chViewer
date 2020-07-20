package com.nanj.topen2chviewer;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import mehdi.sakout.aboutpage.AboutPage;

public class AboutActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Aboutページを作成
    View aboutPage = new AboutPage(this);
    if (AppCompatDelegate.getDefaultNightMode() = AppCompatDelegate.MODE_NIGHT_YES) {
      aboutPage.enableDarkMode(true);
    }
    aboutPage.setImage(R.drawable.aa)
        .setDescription(getString(R.string.app_name) + "\n" + getString(R.string.app_version))
        .addEmail("nanjdev334@gmail.com")
        .addGitHub("NanJ-Dev")
        .create();

    // 作成したAboutページを表示
    setContentView(aboutPage);
  }
}
