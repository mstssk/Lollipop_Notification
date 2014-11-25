package jp.mstssk.sample.lollipop_notification;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MediaStyleFragment extends Fragment {

    NotificationManagerCompat notificationManager;

    public static MediaStyleFragment newInstance() {
        MediaStyleFragment fragment = new MediaStyleFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.media_style, container, false);
        ButterKnife.inject(this, rootView);
        notificationManager = NotificationManagerCompat.from(getActivity());
        return rootView;
    }

    @OnClick(R.id.button_show_media_style)
    void showMediaStyleNotification() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            String msg = "Android 5.0 Lollipopより前のバージョンではMediaStyleは使えません。";
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        } else {
            notificationManager.notify(0, createMediaStyleNotification());
        }
    }

    /**
     * for Lollipop.
     *
     * @return MediaStyle notification.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Notification createMediaStyleNotification() {
        // 本来はMediaSessionを使いメディア再生をコントロールするが、サンプルなので割愛
        // MediaSession dummyMediaSession = new MediaSession(getActivity(), "dummyTag");
        Notification.MediaStyle mediaStyle = new Notification.MediaStyle()
                // .setMediaSession(dummyMediaSession.getSessionToken())
                .setShowActionsInCompactView(0, 2, 4);

        // 前へ,一時停止,次へ、などのActionを設定する。サンプルなのでPendingIntentは割愛
        Notification.Builder builder = new Notification.Builder(getActivity())
                .setContentTitle("Music Title")
                .setContentText("Artist Name, Album Tittle, Genre, etc.")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setShowWhen(false)
                .addAction(android.R.drawable.ic_media_previous, "Previous", null)
                .addAction(android.R.drawable.ic_media_rew, "Rewind", null)
                .addAction(android.R.drawable.ic_media_pause, "Pause", null)
                .addAction(android.R.drawable.ic_media_ff, "Fast Forward", null)
                .addAction(android.R.drawable.ic_media_next, "Next", null)
                .setStyle(mediaStyle);

        // ジャケット画像などはLargeIconで
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getAssets().open("jacket.jpg"));
            if (bitmap != null) {
                builder.setLargeIcon(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.build();
    }


    @OnClick(R.id.button_hide_media_style)
    void hideMediaStyleNotification() {
        notificationManager.cancel(0);
    }

    /**
     * For System use.
     */
    @Deprecated
    public MediaStyleFragment() {
    }
}
