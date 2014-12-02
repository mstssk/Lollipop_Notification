package jp.mstssk.sample.lollipop_notification;


import android.app.AlarmManager;
import android.app.Fragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
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

public class HeadsUpFragment extends Fragment {

    NotificationManagerCompat notificationManager;

    public static HeadsUpFragment newInstance() {
        HeadsUpFragment fragment = new HeadsUpFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.heads_up, container, false);
        ButterKnife.inject(this, rootView);
        notificationManager = NotificationManagerCompat.from(getActivity());
        return rootView;
    }

    @OnClick(R.id.button_hun_fullscreenintent)
    void showWithFullScreenIntent() {
        Intent intent = new Intent(getActivity(), SampleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("Heads-up Notification")
                .setContentText("これはFullScreenIntentによるHeads-up Notificationです。")
                .setSmallIcon(R.drawable.ic_launcher)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setFullScreenIntent(pendingIntent, true);
        notificationManager.notify(0, builder.build());
    }

    @OnClick(R.id.button_hun_sound)
    void showWithSound() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("Heads-up Notification")
                .setContentText("優先度 HIGH および 通音によるHeads-up Notificationです。")
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        notificationManager.notify(0, builder.build());
    }

    @OnClick(R.id.button_hun_vibe)
    void showWithVibe() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("Heads-up Notification")
                .setContentText("優先度 HIGH および バイブレーションによるHeads-up Notificationです。")
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{50, 100, 50, 100, 50, 100});
        notificationManager.notify(0, builder.build());
    }

    @OnClick(R.id.button_hun_after_few_sec)
    void showWithFullScreenIntentAfterFewSecond() {
        Notification notification;
        {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                    .setContentTitle("Heads-up Notification")
                    .setContentText("優先度 HIGH および 通音によるHeads-up Notificationです。")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            notification = builder.build();
        }
        Intent intent = new Intent(getActivity(), NotifierReceiver.class);
        intent.putExtra(NotifierReceiver.EXTRA_NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        long time = System.currentTimeMillis() + (1000 * 5); // 5秒後
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    /**
     * for System uses.
     */
    @Deprecated
    public HeadsUpFragment() {
    }
}
