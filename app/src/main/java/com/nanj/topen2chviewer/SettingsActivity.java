package com.nanj.topen2chviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

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
            // MainActivityに飛ぶ
            startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            break;
          case R.id.settings:
            // 何もしない
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
