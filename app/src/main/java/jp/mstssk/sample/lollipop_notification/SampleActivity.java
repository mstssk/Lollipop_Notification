package jp.mstssk.sample.lollipop_notification;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class SampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab1 = actionBar.newTab().setText("Material Design").setTabListener(new FragmentTabListener(MaterialDesignFragment.newInstance()));
        ActionBar.Tab tab2 = actionBar.newTab().setText("Lock Screen").setTabListener(new FragmentTabListener(LockScreenFragment.newInstance()));
        ActionBar.Tab tab3 = actionBar.newTab().setText("Heads-up").setTabListener(new FragmentTabListener(HeadsUpFragment.newInstance()));
        ActionBar.Tab tab4 = actionBar.newTab().setText("Media Style").setTabListener(new FragmentTabListener(MediaStyleFragment.newInstance()));
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
        actionBar.addTab(tab4);
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
