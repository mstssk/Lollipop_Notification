package jp.mstssk.sample.lollipop_notification;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.os.Bundle;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Map;


public class SampleActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        Map<String, Fragment> fragments = new LinkedHashMap<String, Fragment>();
        fragments.put("Material Design", MaterialDesignFragment.newInstance());
        fragments.put("Lock Screen", LockScreenFragment.newInstance());
        fragments.put("Heads-up", HeadsUpFragment.newInstance());
        fragments.put("Media Style", MediaStyleFragment.newInstance());
        for (Map.Entry<String, Fragment> entry : fragments.entrySet()) {
            actionBar.addTab(actionBar.newTab().setText(entry.getKey()).setTabListener(new FragmentTabListener(entry.getValue())));
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (getIntent().getCategories().contains(Notification.INTENT_CATEGORY_NOTIFICATION_PREFERENCES)) {
            Toast.makeText(this, "Starts from the notification gear icon.", Toast.LENGTH_SHORT).show();
        }
    }

    private static class FragmentTabListener implements ActionBar.TabListener {
        public Fragment fragment;

        public FragmentTabListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.replace(R.id.container, fragment);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.remove(fragment);
        }
    }
}
