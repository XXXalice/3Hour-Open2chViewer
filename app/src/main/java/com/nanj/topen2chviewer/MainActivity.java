package com.nanj.topen2chviewer;

import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;

public class MainActivity extends AppCompatActivity {
  int lastTheme;
  String lastURL;
  AgentWeb agentWeb;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // テーマを変更する
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    if (sharedPreferences.getBoolean("darkorlight", true)) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    } else {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
    lastTheme = AppCompatDelegate.getDefaultNightMode();

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

    // AgentWebを表示させる
    LinearLayout linearLayout = findViewById(R.id.agentwebcontainer);
    String goURL;
    if (lastURL == null || lastURL == "") {
      goURL = sharedPreferences.getString("homepage", "https://open2ch.net/sp/");
    } else {
      goURL = lastURL;
    }
    agentWeb = AgentWeb.with(this)
        .setAgentWebParent(linearLayout, new LinearLayout.LayoutParams(-1, -1))                
        .useDefaultIndicator()
        .setWebChromeClient(webChromeClient)
        .createAgentWeb()
        .ready()
        .go(goURL);

    // TopAppBarのナビゲーションアイコンのListener
    MaterialToolbar materialToolBar = findViewById(R.id.materialtoolbar);

    // ナビゲーションアイコンをタップするとドロワーを開く
    materialToolBar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openCloseDrawer(true);
      }
    });

    materialToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
          case R.id.forward:
            // WebViewの履歴を前に進める
            WebView webView = agentWeb.getWebCreator().getWebView();
            if (webView.canGoForward()) {
              webView.goForward();
            }
            break;
          case R.id.copyurl:
            // ページのURLをコピーする
            String url = agentWeb.getWebCreator().getWebView().getUrl();
            copyToClipboard(url);
            Toast.makeText(this, "URLをコピーしました。", Toast.LENGTH_LONG).show();
            break;
        }
        return true;
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

  @Override
  protected void onPause() {
    agentWeb.getWebLifeCycle().onPause(); 
    super.onPause();
  }

  @Override
  protected void onResume() {
    agentWeb.getWebLifeCycle().onResume();
    super.onResume();
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    if (lastTheme != AppCompatDelegate.getDefaultNightMode()) {
      lastURL = agentWeb.getWebCreator().getWebView().getUrl();
      recreate();
    } else {
      lastURL = "";
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    agentWeb.getWebLifeCycle().onDestroy();
  }

  // 戻るキーを押すと処理を開始する
  @Override
  public void onBackPressed() {
    DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      openCloseDrawer(false);
      return;
    }
    WebView webView = agentWeb.getWebCreator().getWebView();
    if (webView.canGoBack()) {
      webView.goBack();
      return;
    }
    super.onBackPressed();
  }

  // AgentWebで使うWebChromeClient
  WebChromeClient webChromeClient = new WebChromeClient() {
    @Override
    public void onReceivedTitle(WebView webView, String title) {
      super.onReceivedTitle(webView, title);
      TextView textView = findViewById(R.id.materialtoolbartitle);
      textView.setText(title);
    }
  };

  // ドロワーを開けたり閉じたりする
  public void openCloseDrawer(boolean openClose) {
    DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
    if (openClose) {
      drawerLayout.openDrawer(GravityCompat.START);
    } else {
      drawerLayout.closeDrawer(Gravity.LEFT);
    }
  }

  // クリップボードにコピーする
  public void copyToClipboard(String copyText) {
    ClipboardManager clipboardManager = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
    if (clipboardManager == null) {
      return;
    }
    clipboardManager.setPrimaryClip(ClipData.newPlainText("", copyText));
  }
}