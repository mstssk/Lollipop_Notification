package jp.mstssk.sample.lollipop_notification;


import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PriorityModeFragment extends Fragment {

    private static final int REQUEST_CODE_PICK_CONTACT = 1;

    NotificationManagerCompat notificationManager;

    @InjectView(R.id.image_preview)
    ImageView contactImg;

    @InjectView(R.id.contact_name)
    TextView contactName;

    @InjectView(R.id.edit_text_person)
    EditText editTextPerson;

    private Uri mContactUri = null;

    public static PriorityModeFragment newInstance() {
        PriorityModeFragment fragment = new PriorityModeFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.priority_mode, container, false);
        ButterKnife.inject(this, rootView);
        notificationManager = NotificationManagerCompat.from(getActivity());
        return rootView;
    }

    @OnClick(R.id.button_open_setting)
    void openSettings() {
        // 割り込み設定を開きます。
        Intent intent = new Intent("android.settings.ZEN_MODE_SETTINGS");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // 5.0未満 or 5.0の後に割り込み設定のactionが変わっていたら例外
            String msg = String.format("'%s%n' is not found.", intent.getAction());
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.button_notify_alerm)
    void showAlerm() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("アラーム")
                .setContentText("常に割り込みます。")
                .setSmallIcon(R.drawable.ic_launcher)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[]{50, 100, 50, 100, 50, 100});
        notificationManager.notify(0, builder.build());
    }

    @OnClick(R.id.button_notify_event)
    void showEvent() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("予定とリマインダ")
                .setContentText("「予定とリマインダ」の割り込みがONなら割り込みます。")
                .setSmallIcon(R.drawable.ic_launcher)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[]{50, 100, 50, 100, 50, 100});
        notificationManager.notify(1, builder.build());
    }

    @OnClick(R.id.button_notify_calls)
    void showCalls() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("通話")
                .setContentText("「通話」の割り込みがONなら割り込みます。")
                .setSmallIcon(R.drawable.ic_launcher)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[]{50, 100, 50, 100, 50, 100});
        if (mContactUri != null) {
            builder.addPerson(mContactUri.toString());
        }
        if (!TextUtils.isEmpty(editTextPerson.getText())) {
            builder.addPerson(editTextPerson.getText().toString());
        }
        notificationManager.notify(2, builder.build());
    }

    @OnClick(R.id.button_notify_msg)
    void showMsg() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("メッセージ")
                .setContentText("「メッセージ」の割り込みがONなら割り込みます。")
                .setSmallIcon(R.drawable.ic_launcher)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[]{50, 100, 50, 100, 50, 100});
        if (mContactUri != null) {
            builder.addPerson(mContactUri.toString());
        }
        if (!TextUtils.isEmpty(editTextPerson.getText())) {
            builder.addPerson(editTextPerson.getText().toString());
        }
        notificationManager.notify(3, builder.build());
    }

    @OnClick(R.id.button_choose_contact)
    void chooseContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_CONTACT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PICK_CONTACT:
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactUri = data.getData();
                    mContactUri = contactUri;
                    contactName.setText(getContactName(contactUri));
                    contactImg.setImageURI(getContactImageUri(contactUri));
                } else {
                    mContactUri = null;
                    contactName.setText(null);
                    contactImg.setImageResource(android.R.color.transparent);
                }
                break;
        }
    }

    private String getContactName(Uri contactUri) {
        if (contactUri == null) {
            return null;
        }
        Cursor cursor = getActivity().getContentResolver().query(contactUri, null, null, null,
                null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        int idx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        return cursor.getString(idx);
    }

    private Uri getContactImageUri(Uri contactUri) {
        if (contactUri == null) {
            return null;
        }
        Cursor cursor = getActivity().getContentResolver().query(contactUri, null, null, null,
                null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        int idx = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID);
        String hasPhoto = cursor.getString(idx);
        return Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo
                .CONTENT_DIRECTORY);
    }

    /**
     * for System uses.
     */
    @Deprecated
    public PriorityModeFragment() {
    }
}
