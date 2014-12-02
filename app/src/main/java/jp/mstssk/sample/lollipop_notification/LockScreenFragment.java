package jp.mstssk.sample.lollipop_notification;


import android.app.Fragment;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LockScreenFragment extends Fragment {

    NotificationManagerCompat notificationManager;

    public static LockScreenFragment newInstance() {
        LockScreenFragment fragment = new LockScreenFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lock_screen, container, false);
        ButterKnife.inject(this, rootView);
        notificationManager = NotificationManagerCompat.from(getActivity());
        return rootView;
    }

    @OnClick(R.id.button_open_setting)
    void openSettings() {
        Intent intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.button_notify_public)
    void showPublic() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("Public notification.")
                .setContentText("ロックスクリーンに表示されます。")
                .setSmallIcon(R.drawable.ic_launcher)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notificationManager.notify(0, builder.build());
    }

    @OnClick(R.id.button_notify_private)
    void showPrivate() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("Private notification.")
                .setContentText("ロックスクリーンに表示されますが、詳細は隠され、アプリ名のみ表示されます。")
                .setSmallIcon(R.drawable.ic_launcher)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
        notificationManager.notify(1, builder.build());
    }

    @OnClick(R.id.button_notify_private_with_public)
    void showPrivateWithPublicVersion() {
        Notification publicVer = new NotificationCompat.Builder(getActivity())
                .setContentTitle("Alternative notification.")
                .setContentText("代わりに表示されるNotificationです。")
                .setSmallIcon(R.drawable.ic_launcher)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("Private notification.")
                .setContentText("ロックスクリーンに表示されますが、詳細は隠され、アプリ名のみ表示されます。")
                .setSmallIcon(R.drawable.ic_launcher)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setPublicVersion(publicVer);
        notificationManager.notify(2, builder.build());
    }

    @OnClick(R.id.button_notify_secret)
    void showSecret() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("Secret notification.")
                .setContentText("ロックスクリーンに全く表示されません。")
                .setSmallIcon(R.drawable.ic_launcher)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET);
        notificationManager.notify(3, builder.build());
    }

    /**
     * for System use.
     */
    public LockScreenFragment() {
    }
}
