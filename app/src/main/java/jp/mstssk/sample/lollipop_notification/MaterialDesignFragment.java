package jp.mstssk.sample.lollipop_notification;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MaterialDesignFragment extends Fragment {

    private static final int REQUEST_CODE_PICK_IMG = 1;

    NotificationManagerCompat notificationManager;

    @InjectView(R.id.spiner_color)
    Spinner colorSpiner;

    @InjectView(R.id.button_choose_img)
    Button chooseImg;

    @InjectView(R.id.image_preview)
    ImageView preview;

    @InjectView(R.id.button_show_notification)
    Button showNotification;

    private Uri mImageUri;
    private int mNotifyId = 0;

    public static MaterialDesignFragment newInstance() {
        MaterialDesignFragment fragment = new MaterialDesignFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.material_design, container, false);
        ButterKnife.inject(this, rootView);
        notificationManager = NotificationManagerCompat.from(getActivity());

        ArrayAdapter<Color> colorArrayAdapter = new ArrayAdapter<Color>(getActivity(), android.R.layout.simple_spinner_item, Color.values());
        colorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpiner.setAdapter(colorArrayAdapter);

        return rootView;
    }

    @OnClick(R.id.button_choose_img)
    void chooseImg() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PICK_IMG:
                if (resultCode == Activity.RESULT_OK) {
                    mImageUri = data.getData();
                    preview.setImageURI(data.getData());
                } else {
                    mImageUri = null;
                    preview.setImageResource(android.R.color.transparent);
                }
        }
    }

    @OnClick(R.id.button_show_notification)
    void showNotification() {
        Integer color = ((Color) colorSpiner.getSelectedItem()).color;
        Notification notification = createNotification(color, mImageUri);
        notificationManager.notify(mNotifyId++, notification);
    }

    private Notification createNotification(Integer color, Uri img) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle("Content Title")
                .setContentText("Content text")
                .setSmallIcon(R.drawable.ic_launcher);
        if (color != null) {
            builder.setColor(color);
        }
        if (img != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), img);
                builder.setLargeIcon(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.build();
    }

    private enum Color {
        DEFAULT("Default", null),
        RED("Red", 0xF44336),
        GREEN("Green", 0x4CAF50),
        BLUE("Blue", 0x2196F3);

        String text;
        Integer color;

        Color(String text, Integer color) {
            this.text = text;
            this.color = color;
        }
    }

    /**
     * for System use.
     */
    @Deprecated
    public MaterialDesignFragment() {
    }
}
