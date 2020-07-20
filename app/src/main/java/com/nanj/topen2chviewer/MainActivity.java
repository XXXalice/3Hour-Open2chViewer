package com.nanj.topen2chviewer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // アップデートを確認
    new AppUpdater(this)
        .setDisplay(Display.DIALOG)
        .setUpdateFrom(UpdateFrom.GITHUB)
        .setGitHubUserAndRepo("NanJ-Dev", "3Hour-Open2chViewer")
        .setTitleOnUpdateAvailable("アップデートがあります")
        .setContentOnUpdateAvailable("「アップデートする」を押すとブラウザでGitHubを開きます。")
        .setButtonUpdate("アップデートする")
        .setButtonDismiss("無視する")
        .setButtonDoNotShowAgain("二度と表示しない")
        .start();

    // WebViewにページを表示させる
    WebView webView = findViewById(R.id.webview);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(new WebViewClient());
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    webView.loadUrl(sharedPreferences.getString("homepage", "https://open2ch.net/sp/"));

    // TopAppBarのナビゲーションアイコンのListener
    MaterialToolbar materialToolBar = findViewById(R.id.materialtoolbar);

    // ナビゲーションアイコンをタップするとドロワーを開く
    materialToolBar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openCloseDrawer(true);
      }
    });

    // ナビゲーションドロワーのListener
    NavigationView navigationView = findViewById(R.id.navigationview);

    // ドロワー内の項目をタップすると処理を実行
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(MenuItem menuItem) {
        // ドロワーを閉じる
        openCloseDrawer(false);
        switch (menuItem.getItemId()) {
          case R.id.home:
            // 何もしない
            break;
          case R.id.settings:
            // SettingActivityに飛ぶ
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            break;
        }
        return true;
      }
    });
  }

  // 戻るキーを押すとドロワーが閉じる
  @Override
  public void onBackPressed() {
    DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      openCloseDrawer(false);
    } else {
      super.onBackPressed();
    }
  }

  // ドロワーを開けたり閉じたりする
  public void openCloseDrawer(boolean openClose) {
    DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
    if (openClose) {
      drawerLayout.openDrawer(GravityCompat.START);
    } else {
      drawerLayout.closeDrawer(Gravity.LEFT);
    }
  }
}
