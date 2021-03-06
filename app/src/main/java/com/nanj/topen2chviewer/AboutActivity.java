package com.nanj.topen2chviewer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import mehdi.sakout.aboutpage.AboutPage;

public class AboutActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Aboutページを作成
    boolean darkMode;
    int drawable;
    int darkModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    if (darkModeFlags == Configuration.UI_MODE_NIGHT_YES) {
      darkMode = true;
      drawable = R.drawable.darkaa;
    } else {
      darkMode = false;
      drawable = R.drawable.aa;
    }
    View aboutPage = new AboutPage(this)
        .enableDarkMode(darkMode)
        .setImage(drawable)
        .setDescription(getString(R.string.app_name) + "\n" + getString(R.string.app_version))
        .addEmail("nanjdev334@gmail.com")
        .addGitHub("NanJ-Dev")
        .create();

    // 作成したAboutページを表示
    setContentView(aboutPage);
  }
}