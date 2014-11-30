package jp.mstssk.sample.lollipop_notification;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

public class NotifierReceiver extends BroadcastReceiver {

    public static final String EXTRA_NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = intent.getParcelableExtra(EXTRA_NOTIFICATION);
        notificationManager.notify(0, notification);
    }
}
