package com.monke.monkeybook.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

import com.monke.monkeybook.BuildConfig;

/**
 * Created by GKF on 2018/1/6.
 * 监听耳机键
 */

public class MediaButtonIntentReceiver extends BroadcastReceiver {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    public static final String TAG = MediaButtonIntentReceiver.class.getSimpleName();

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (handleIntent(context, intent) && isOrderedBroadcast()) {
            abortBroadcast();
        }
    }

    public static boolean handleIntent(final Context context, final Intent intent) {
        if (DEBUG) Log.v(TAG, "Received intent: " + intent);
        final String intentAction = intent.getAction();
        if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
            final KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (event == null) {
                return false;
            }

            final int keycode = event.getKeyCode();

            String command = null;
            switch (keycode) {
                case KeyEvent.KEYCODE_HEADSETHOOK:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    command = ReadAloudService.mediaButtonAction;
                    break;
                default:
                    break;
            }
            if (command != null) {
                startService(context, command);
                return true;
                }
            }
        return false;
    }

    private static void startService(Context context, String command) {
        final Intent intent = new Intent(context, ReadAloudService.class);
        intent.setAction(command);
        context.startService(intent);
    }

}
